package com.monet.bbc.activity;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.monet.bbc.R;
import com.monet.bbc.utils.AppPreference;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveVideoPlay extends AppCompatActivity implements Animation.AnimationListener {

    private CircleImageView userImage;
    private ImageView img_liveTumb, img_liveVideo_back;
    private TextView tv_videoNameLive, secondsShow, tv_wait, tv_userLivePoints;
    private DonutProgress prog_LiveTimer;
    private ShowQuestion showQuestion;
    private ShowTimer showTimer;
    private ShowRightQuestionTimer rightQuestionTimer;
    private ClearSelection clearSelection;
    private int progreTime = 10;
    private int progTime = 0;
    private int quesProgreTime = 5;
    private int quesProgTime = 0;
    private int optionShowTime = 3;
    private int optionTime = 0;
    private String ansSelected = "", rigthAnswer = "3";
    private int userPoints = 0;
    private Button optionOne, optionTwo, optionThree, optionFour;
    private RelativeLayout questionLayout;
    private View view_coin;
    private Animation animZoomIn;
    private Animation animZoomOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getLayoutInflater().inflate(R.layout.activity_live_video_play, null);
        v.setKeepScreenOn(true);
        setContentView(v);

        initView();

    }

    private void initView() {
        userImage = findViewById(R.id.img_liveQuestionUser);
        tv_videoNameLive = findViewById(R.id.tv_videoNameLive);
        img_liveTumb = findViewById(R.id.img_liveTumb);
        secondsShow = findViewById(R.id.tv_liveTimeSeconds);
        prog_LiveTimer = findViewById(R.id.prog_LiveTimer);
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);
        questionLayout = findViewById(R.id.questionLayout);
        tv_wait = findViewById(R.id.tv_wait);
        img_liveVideo_back = findViewById(R.id.img_liveVideo_back);
        tv_userLivePoints = findViewById(R.id.tv_userLivePoints);
        view_coin = findViewById(R.id.view_coin);

        userPoints = AppPreference.getUserPoints(this);

        tv_userLivePoints.setText(""+userPoints);

        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        animZoomIn.setAnimationListener(this);

        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out);
        animZoomOut.setAnimationListener(this);

        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("image");
        String name = bundle.getString("name");

        if(AppPreference.getImageURL(this).isEmpty()){
            Glide.with(this).load("http://icons.iconarchive.com/icons/graphicloads/flat-finance/256/person-icon.png").into(userImage);
        }else{
            Glide.with(this).load(AppPreference.getImageURL(this)).into(userImage);
        }

        if (image != null) {
            Glide.with(this).load(image).into(img_liveTumb);
            tv_videoNameLive.setText(name);
        }

        prog_LiveTimer.setText("");
        prog_LiveTimer.setMax(quesProgreTime);
        showQuestion = new ShowQuestion(5000, 1000);
        showQuestion.start();

        optionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableSelection("1");
                ansSelected = "1";
            }
        });

        optionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableSelection("2");
                ansSelected = "2";
            }
        });

        optionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableSelection("3");
                ansSelected = "3";
            }
        });

        optionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableSelection("4");
                ansSelected = "4";
            }
        });

        img_liveVideo_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void disableSelection(String s) {
        optionOne.setClickable(false);
        optionTwo.setClickable(false);
        optionThree.setClickable(false);
        optionFour.setClickable(false);
        setButtonColor(s);
    }

    private void enableSelection() {
        optionOne.setClickable(true);
        optionTwo.setClickable(true);
        optionThree.setClickable(true);
        optionFour.setClickable(true);
        optionOne.setBackgroundResource(R.drawable.ans_capsule);
        optionTwo.setBackgroundResource(R.drawable.ans_capsule);
        optionThree.setBackgroundResource(R.drawable.ans_capsule);
        optionFour.setBackgroundResource(R.drawable.ans_capsule);
        ansSelected = "";
        progreTime = 10;
        progTime = 0;
        quesProgreTime = 5;
        quesProgTime = 0;
        optionShowTime = 3;
        optionTime = 0;
        tv_wait.setVisibility(View.VISIBLE);
        questionLayout.setVisibility(View.GONE);
        clearSelection.cancel();
        prog_LiveTimer.setMax(quesProgreTime);
        showQuestion = new ShowQuestion(5000, 1000);
        showQuestion.start();
    }

    private void showRightAnswer() {
        if (ansSelected.isEmpty()) {
            if (rigthAnswer.equals("1")) {
                optionOne.setBackgroundResource(R.drawable.right_answer);
            } else if (rigthAnswer.equals("2")) {
                optionTwo.setBackgroundResource(R.drawable.right_answer);
            } else if (rigthAnswer.equals("3")) {
                optionThree.setBackgroundResource(R.drawable.right_answer);
            } else if (rigthAnswer.equals("4")) {
                optionFour.setBackgroundResource(R.drawable.right_answer);
            }
        } else {
            if (ansSelected.equals("1")) {
                optionOne.setBackgroundResource(R.drawable.wrong_answer);
            } else if (ansSelected.equals("2")) {
                optionTwo.setBackgroundResource(R.drawable.wrong_answer);
            } else if (ansSelected.equals("3")) {
                optionThree.setBackgroundResource(R.drawable.wrong_answer);
            } else if (ansSelected.equals("4")) {
                optionFour.setBackgroundResource(R.drawable.wrong_answer);
            }

            if (rigthAnswer.equals("1")) {
                optionOne.setBackgroundResource(R.drawable.right_answer);
            } else if (rigthAnswer.equals("2")) {
                optionTwo.setBackgroundResource(R.drawable.right_answer);
            } else if (rigthAnswer.equals("3")) {
                optionThree.setBackgroundResource(R.drawable.right_answer);
            } else if (rigthAnswer.equals("4")) {
                optionFour.setBackgroundResource(R.drawable.right_answer);
            }
        }

        if(ansSelected.equals(rigthAnswer)){
            userPoints = userPoints+10;
            AppPreference.setUserPoints(this, userPoints);
            tv_userLivePoints.setText(""+userPoints);
            view_coin.startAnimation(animZoomIn);
        }

        showTimer.cancel();
        clearSelection = new ClearSelection(2000, 1000);
        clearSelection.start();

    }

    private void setButtonColor(String s) {
        ansSelected = s;
        if (s.equals("1")) {
            optionOne.setBackgroundResource(R.drawable.option_selected);
        } else if (s.equals("2")) {
            optionTwo.setBackgroundResource(R.drawable.option_selected);
        } else if (s.equals("3")) {
            optionThree.setBackgroundResource(R.drawable.option_selected);
        } else if (s.equals("4")) {
            optionFour.setBackgroundResource(R.drawable.option_selected);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        view_coin.startAnimation(animZoomOut);
        view_coin.postDelayed(new Runnable() {
            @Override
            public void run() {
                view_coin.setAnimation(null);
            }
        }, 500);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private class ShowTimer extends CountDownTimer {

        public ShowTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            progreTime = progreTime - 1;
            progTime = progTime + 1;
            secondsShow.setText("" + progreTime);
            prog_LiveTimer.setDonut_progress("" + progTime);
        }

        @Override
        public void onFinish() {
            secondsShow.setText("0");
            prog_LiveTimer.setDonut_progress("30");
            showTimer.cancel();
            prog_LiveTimer.setMax(optionShowTime);
            rightQuestionTimer = new ShowRightQuestionTimer(3000, 1000);
            rightQuestionTimer.start();
        }
    }

    private class ShowRightQuestionTimer extends CountDownTimer {

        public ShowRightQuestionTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            optionShowTime = optionShowTime - 1;
            optionTime = optionTime + 1;
            secondsShow.setText("" + optionTime);
            prog_LiveTimer.setDonut_progress("" + optionTime);
        }

        @Override
        public void onFinish() {
            secondsShow.setText("0");
            prog_LiveTimer.setDonut_progress("3");
            showRightAnswer();
//            enableSelection();
        }
    }

    private class ClearSelection extends CountDownTimer {

        public ClearSelection(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            enableSelection();
        }
    }

    private class ShowQuestion extends CountDownTimer {

        public ShowQuestion(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            quesProgreTime = quesProgreTime - 1;
            quesProgTime = quesProgTime + 1;
            secondsShow.setText("" + quesProgTime);
            prog_LiveTimer.setDonut_progress("" + quesProgTime);
        }

        @Override
        public void onFinish() {
            showQuestion.cancel();
            tv_wait.setVisibility(View.GONE);
            questionLayout.setVisibility(View.VISIBLE);
            prog_LiveTimer.setMax(progreTime);
            showTimer = new ShowTimer(10000, 1000);
            showTimer.start();
        }
    }
}