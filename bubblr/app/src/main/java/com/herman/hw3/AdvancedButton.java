package com.herman.hw3;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by herman on 12.05.15.
 */
public class AdvancedButton extends Button {

    private int size;
    private int changedPositionCount = 0;
    private boolean toChangePosition = false;
    private boolean toRemove = false;
    private Timer timer = new Timer();

    private int scoreBaseValue;

    /**
     * The constructor
     * @param context- context for the button
     * @param attrs- needed attributes
     * @param period- the time period for changing its location
     */
    public AdvancedButton(Context context, AttributeSet attrs, long period) {
        super(context, attrs);


        createPositionChangeTimer(period);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(size, size);
    }

    /**
     * Creates a new timer for the button.
     * @param period- the time period for changing its location
     */
    public void createPositionChangeTimer(long period) {


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                AdvancedButton.this.setToChangePosition(true);
                //System.out.println("relocating button");
                AdvancedButton.this.setChangedPositionCount(AdvancedButton.this.getChangedPositionCount() + 1);
                //System.out.println("timer run");
                if (AdvancedButton.this.getChangedPositionCount() > 3) {

                    AdvancedButton.this.setToRemove(true);
                    //System.out.println("removing button");
                    //System.out.println("timer stop");
                    timer.cancel();
                    timer.purge();
                    timer = null;



                }
            }
        };
        timer.schedule(timerTask, period, period);
    }



    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public int getChangedPositionCount() {
        return changedPositionCount;
    }

    public void setChangedPositionCount(int changedPositionCount) {
        this.changedPositionCount = changedPositionCount;
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }

    public boolean isToChangePosition() {
        return toChangePosition;
    }

    public void setToChangePosition(boolean toChangePosition) {
        this.toChangePosition = toChangePosition;
    }

    public int getScoreBaseValue() {
        return scoreBaseValue;
    }

    public void setScoreBaseValue(int scoreBaseValue) {
        this.scoreBaseValue = scoreBaseValue;
    }
}
