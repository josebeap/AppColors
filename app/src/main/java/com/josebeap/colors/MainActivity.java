package com.josebeap.colors;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView lblProposedColor = null;
    private TextView lblTargetColor = null;
    private SeekBar sbrRed = null;
    private SeekBar sbrGreen = null;
    private SeekBar sbrBlue = null;
    private TextView lblRedValue = null;
    private TextView lblGreenValue = null;
    private TextView lblBlueValue = null;
    private Button btnGetScore = null;
    private Button btnNewColor = null;

    private ColorsGame colorsGame = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorsGame = new ColorsGame();

        colorsGame.setOnChangeTargetColorListener((backColor, textColor) -> {
            lblTargetColor.setBackgroundColor(backColor);
            lblTargetColor.setTextColor(textColor);
        });

        colorsGame.setOnChangeProposedColorListener((backColor, textColor) -> {
            lblProposedColor.setBackgroundColor(backColor);
            lblProposedColor.setTextColor(textColor);


            sbrRed.setProgress(Color.red(backColor));
            lblRedValue.setText(String.valueOf(sbrRed.getProgress()));

            sbrGreen.setProgress(Color.green(backColor));
            lblGreenValue.setText(String.valueOf(sbrGreen.getProgress()));

            sbrBlue.setProgress(Color.blue(backColor));
            lblBlueValue.setText(String.valueOf(sbrBlue.getProgress()));
        });

        initViews();
        initEvents();
        colorsGame.restartGame();

    }

    public void initViews() {
        lblTargetColor = findViewById(R.id.lvlTargetColor);
        lblProposedColor = findViewById(R.id.lblProposedColor);

        sbrRed = findViewById(R.id.sbrRed);
        sbrGreen = findViewById(R.id.sbrGreen);
        sbrBlue = findViewById(R.id.sbrBlue);


        lblRedValue = findViewById(R.id.lblRedValue);
        lblGreenValue = findViewById(R.id.lblGreenValue);
        lblBlueValue = findViewById(R.id.lblBlueValue);


        btnGetScore = findViewById(R.id.btnGetScore);
        btnNewColor = findViewById(R.id.btnNewColor);

    }

    public void initEvents() {
        SeekBar[] seekBars = {sbrRed, sbrGreen, sbrBlue};



        for (SeekBar seekBar : seekBars) {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    int redValue = sbrRed.getProgress();
                    int greenValue = sbrGreen.getProgress();
                    int blueValue = sbrBlue.getProgress();
                    int newBackColor = Color.rgb(redValue, greenValue, blueValue);

                    colorsGame.setProposedColor(newBackColor);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }




        btnGetScore.setOnClickListener(v -> {
            showScore();
        });


        btnNewColor.setOnClickListener(v -> {
            colorsGame.restartGame();

        });
    }

    public void showScore () {

        final String RED =getString(R.string.Red);
        final String GREEN =getString(R.string.Green);
        final String BLUE =getString(R.string.Blue);
        final String VERYLOW =getString(R.string.Very_low);
        final String VERYHIGH =getString(R.string.Very_high);
        final String LOW =getString(R.string.Low);
        final String HIGH =getString(R.string.High);

        int targetColor= colorsGame.getTargetBackColor();
        int proposedColor= colorsGame.getProposedColor();
        int redDiff=Color.red(targetColor)-Color.red(proposedColor);
        int greenDiff =Color.green(targetColor)-Color.green(proposedColor);
        int blueDiff =Color.blue(targetColor)-Color.blue(proposedColor);

        int score = colorsGame.getScore();


        StringBuilder text = new StringBuilder();
        StringBuilder tips = new StringBuilder();

        text.append(getString(R.string.Your_score_is, String.valueOf(score)));

        if (redDiff>10){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,RED.toLowerCase(),VERYLOW.toLowerCase()));
        }else if(redDiff > 0){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,RED.toLowerCase(),LOW.toLowerCase()));
        }else if (redDiff < -10){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,RED.toLowerCase(),VERYHIGH.toLowerCase()));
        }else if(redDiff <  0){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,RED.toLowerCase(),HIGH.toLowerCase()));
        }


        if (greenDiff>10){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,GREEN.toLowerCase(),VERYLOW.toLowerCase()));
        }else if(greenDiff > 0){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,GREEN.toLowerCase(),LOW.toLowerCase()));
        }else if (greenDiff < -10){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,GREEN.toLowerCase(),VERYHIGH.toLowerCase()));
        }else if(greenDiff <  0){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,GREEN.toLowerCase(),HIGH.toLowerCase()));
        }

        if (blueDiff>10){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,BLUE.toLowerCase(),VERYLOW.toLowerCase()));
        }else if(blueDiff > 0){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,BLUE.toLowerCase(),LOW.toLowerCase()));
        }else if (blueDiff < -10){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,BLUE.toLowerCase(),VERYHIGH.toLowerCase()));
        }else if(blueDiff <  0){
            tips.append("\n");
            tips.append(getString(R.string.X_is_Y,BLUE.toLowerCase(),HIGH.toLowerCase()));
        }



        if (tips.length()>0){
            text.append("\n\n");
            text.append(getString(R.string.Tips));
            text.append(":");
            text.append(tips);
        }



        AlertDialog.Builder alert = new AlertDialog.Builder(this);




        alert.setMessage(text);
        alert.setPositiveButton(getString(R.string.Close),null);
        alert.show();


    }

}