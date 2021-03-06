package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.Signature;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.monet.bbc.R;
import com.monet.bbc.connection.ApiInterface;
import com.monet.bbc.connection.BaseUrl;
import com.monet.bbc.model.loginResponse.LoginPojo;
import com.monet.bbc.model.loginResponse.LoginPost;
import com.monet.bbc.utils.AppPreference;
import com.monet.bbc.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.monet.bbc.utils.AppConstant.EMAIL_PATTERN;
import static com.monet.bbc.utils.AppUtils.checkConnection;
import static com.monet.bbc.utils.AppUtils.shortToast;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_login_email, edt_login_password;
    private String email, password;
    private LoginButton fbLogin;
    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private AccessToken mAccessToken;
    private String socialId, socialEmail, socialName, socialImage;
    private static int RC_SIGN_IN = 1;
    boolean doubleBackToExitPressedOnce = false;
    private ApiInterface apiInterface;
    private static final String TAG = "LoginScreen";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        edt_login_email = findViewById(R.id.edt_login_email);
        edt_login_password = findViewById(R.id.edt_login_password);
        fbLogin = findViewById(R.id.fbLogin);

        findViewById(R.id.tv_login_forgot).setOnClickListener(this);
        findViewById(R.id.tv_Login_SignUp_Link).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_fb).setOnClickListener(this);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCanceledOnTouchOutside(false);
//        getKeyHash();
        apiInterface = BaseUrl.getRetrofit().create(ApiInterface.class);
        fbLogin.setOnClickListener(this);
        fbLogin.setReadPermissions(Arrays.asList(EMAIL));
        fbLogin.setLoginBehavior(LoginBehavior.WEB_ONLY);
        callbackManager = CallbackManager.Factory.create();
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forgot:
                startActivity(new Intent(this, ForgotPasswordScreen.class));
                break;

            case R.id.tv_Login_SignUp_Link:
                startActivity(new Intent(this, RegisterScreen.class));
                break;

            case R.id.btn_login:
                validate();
                break;

            case R.id.btn_fb:
                //Toast.makeText(this, "btn clicked", Toast.LENGTH_SHORT).show();
                fbLogin.performClick();
                break;
            case R.id.fbLogin:
                fbLogin();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fbLogin() {
        pd.show();
        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                pd.dismiss();
                mAccessToken = loginResult.getAccessToken();
                getUserProfile(mAccessToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginScreen.this, "Login Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginScreen.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserProfile(AccessToken currentAccessToken) {

        GraphRequest request = GraphRequest.newMeRequest(

                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            socialId = object.getString("id");
                            socialName = object.getString("name");
                            socialImage = "https://graph.facebook.com/" + socialId + "/picture?type=large";
                            socialEmail = object.getString("email");
                            socialLogin(socialId, socialName, socialImage, socialEmail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                             AppUtils.shortToast(LoginScreen.this, "Your Email ID is not registered with Facebook");
                        }
                        LoginManager.getInstance().logOut();

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void socialLogin(String socialId, String socialName, String socialImage, final String socialEmail) {
        findViewById(R.id.btn_login).setClickable(false);
        pd.show();
        LoginPost loginPost = new LoginPost(socialEmail, socialName, socialId);
        Call<LoginPojo> pojoCall = apiInterface.socialLoginUser(loginPost);
        pojoCall.enqueue(new Callback<LoginPojo>() {

            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                findViewById(R.id.btn_login).setClickable(true);
                try {
                    if (pd.isShowing()) {
                        pd.dismiss();
                        if (response.body().getCode().equals("200")) {
                            AppPreference.setUserLoggedOut(LoginScreen.this, false);
                            AppPreference.setImageURL(LoginScreen.this, response.body().getResponse().getImage());
                            AppPreference.setEmail(LoginScreen.this, response.body().getResponse().getEmail());
                            AppPreference.setUserName(LoginScreen.this, response.body().getResponse().getFull_Name());
                            AppPreference.setApiToken(LoginScreen.this, response.body().getResponse().getApi_token());
                            AppPreference.setId(LoginScreen.this, response.body().getResponse().get_id());
                            startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                            finish();
                        } else {
                            shortToast(LoginScreen.this, response.body().getMessage());
                        }
                    }
                } catch (Exception e) {
                    shortToast(LoginScreen.this, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {
                findViewById(R.id.btn_login).setClickable(true);
                pd.dismiss();
                shortToast(LoginScreen.this, "Oops! Something went wrong.");
            }
        });
    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.monet_android",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", "KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void validate() {

        email = edt_login_email.getText().toString();
        password = edt_login_password.getText().toString();

        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Please enter email or password", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (email.matches(EMAIL_PATTERN)) {
            loginUser();
        } else {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("NewApi")
    private void loginUser() {
        findViewById(R.id.btn_login).setClickable(false);
        pd.show();
        LoginPost loginPost = new LoginPost(email, password);
        Call<LoginPojo> pojoCall = apiInterface.loginUser(loginPost);
        pojoCall.enqueue(new Callback<LoginPojo>() {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                findViewById(R.id.btn_login).setClickable(true);
                try {
                    if (pd.isShowing()) {
                        pd.dismiss();
                        if (response.body().getCode().equals("200")) {
                            AppPreference.setUserLoggedOut(LoginScreen.this, false);
                            AppPreference.setImageURL(LoginScreen.this, response.body().getResponse().getImage());
                            AppPreference.setEmail(LoginScreen.this, response.body().getResponse().get_id());
                            AppPreference.setUserName(LoginScreen.this, response.body().getResponse().getFull_Name());
                            AppPreference.setApiToken(LoginScreen.this, response.body().getResponse().getApi_token());
                            AppPreference.setId(LoginScreen.this, response.body().getResponse().get_id());
                            startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                            finish();
                        } else {
                            shortToast(LoginScreen.this, response.body().getMessage());
                        }
                    } else {
                        shortToast(LoginScreen.this, "pd not visible");
                    }

                } catch (Exception e) {
                    pd.dismiss();
                    shortToast(LoginScreen.this, "Oops! Something went wrong.");
                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {
                findViewById(R.id.btn_login).setClickable(true);
                pd.dismiss();
                Toast.makeText(LoginScreen.this, "Oops! Something went wrong.", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        checkConnection(this);
        super.onResume();
    }
}

