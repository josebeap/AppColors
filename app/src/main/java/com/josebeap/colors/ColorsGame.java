package com.josebeap.colors;

import android.graphics.Color;

import java.util.Random;

public class ColorsGame {

    private int targetBackColor = 0;
    private int targetTextColor = 0;
    private int proposedbackColor = 0;
    private int proposedtextColor = 0;
    private int proposedColor = 0;
    private onChangeTargetColorListener onChangeTargetColorListener = null;
    private onChangeProposedColorListener onChangeProposedColorListener = null;


    public interface onChangeTargetColorListener {
        public abstract void onChange(int backColor, int textColor);
    }

    public interface onChangeProposedColorListener {
        public abstract void onChange(int backColor, int textColor);
    }

    public ColorsGame.onChangeTargetColorListener getOnChangeTargetColorListener() {
        return onChangeTargetColorListener;
    }

    public void setOnChangeTargetColorListener(ColorsGame.onChangeTargetColorListener onChangeTargetColorListener) {
        this.onChangeTargetColorListener = onChangeTargetColorListener;
    }


    public onChangeProposedColorListener getOnChangeProposedColorListener() {
        return onChangeProposedColorListener;
    }

    public void setOnChangeProposedColorListener(ColorsGame.onChangeProposedColorListener onChangeProposedColorListener) {
        this.onChangeProposedColorListener = onChangeProposedColorListener;
    }


    public static int randomColor() {

        Random rand = new Random();
        int redValue = rand.nextInt(256);
        int greenValue = rand.nextInt(256);
        int blueValue = rand.nextInt(256);
        int color = Color.rgb(redValue, greenValue, blueValue);


        return color;
    }


    public static int getSuggestedTextColor(int backcColor) {

        int redValue = Color.red(backcColor);
        int greenValue = Color.green(backcColor);
        int blueValue = Color.blue(backcColor);
        int grayValue = (int) ((redValue * 0.20 + greenValue * 0.75 + blueValue * 0.05));
        int textColor = Color.BLACK;

        if (grayValue < 128) {
            textColor = Color.WHITE;
        }

        return textColor;
    }

    public int getProposedColor() {
        return proposedbackColor;
    }

    public void setProposedColor(int proposedColor) {
        proposedbackColor = proposedColor;
        proposedtextColor = getSuggestedTextColor(proposedbackColor);


        if (getOnChangeProposedColorListener() != null)
            getOnChangeProposedColorListener().onChange(proposedColor, proposedtextColor);


    }


    public int getProposedTextColor() {
        return proposedtextColor;
    }


    public static float distance(int color1, int color2) {
        int redValue1 = Color.red(color1);
        int greenValue1 = Color.green(color1);
        int blueValue1 = Color.blue(color1);

        int redValue2 = Color.red(color2);
        int greenValue2 = Color.green(color2);
        int blueValue2 = Color.blue(color2);

        int resRedValue = (int) Math.pow(redValue1 - redValue2, 2);
        int resGreenValue = (int) Math.pow(greenValue1 - greenValue2, 2);
        int resBlueValue = (int) Math.pow(blueValue1 - blueValue2, 2);


        //calculate the distance between two points 3D

        float distance = (float) Math.sqrt(resRedValue + resGreenValue + resBlueValue);

        return distance;

    }

    public int getScore() {
        float distance = distance(targetBackColor, proposedbackColor);

        int score = (int) (100 - Math.min(distance, 100));

        return score;

    }

    public int getTargetBackColor() {
        return targetBackColor;
    }

    public void setTargetBackColor(int targetColor) {
        targetBackColor = targetColor;
        targetTextColor = getSuggestedTextColor(targetBackColor);

        do {
            proposedbackColor = randomColor();
        } while (getScore() > 0);

        proposedtextColor = getSuggestedTextColor(proposedbackColor);
        if (getOnChangeTargetColorListener() != null) {
            getOnChangeTargetColorListener().onChange(targetBackColor, targetTextColor);
        }

        if (getOnChangeProposedColorListener() != null) {
            getOnChangeProposedColorListener().onChange(proposedbackColor, proposedtextColor);
        }
    }

    public int getTargetTextColor() {
        return targetTextColor;
    }

    public int getProposedbackColor() {
        return proposedbackColor;
    }


    public void restartGame() {
        setTargetBackColor(randomColor());

    }

    public ColorsGame() {
        restartGame();
    }

}

