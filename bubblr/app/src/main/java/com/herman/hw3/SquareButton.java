package com.herman.hw3;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by herman on 30.04.15.
 */
public class SquareButton extends AdvancedButton {



    public SquareButton(Context context, AttributeSet attrs, long period) {
        super(context, attrs, period);

        setBackgroundResource(R.drawable.square_button);


    }

    @Override
    public boolean isToRemove() {
        return super.isToRemove();
    }

    @Override
    public boolean isToChangePosition() {
        return super.isToChangePosition();
    }

    @Override
    public void setToChangePosition(boolean toChangePosition) {
        super.setToChangePosition(toChangePosition);
    }

    @Override
    public void setToRemove(boolean toRemove) {
        super.setToRemove(toRemove);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(super.getSize(), super.getSize());


    }

    public int getSize() { return super.getSize();}

    public void setSize(int newSize) {
        super.setSize(newSize);
    }

    @Override
    public int getChangedPositionCount() {
        return super.getChangedPositionCount();
    }

    @Override
    public void setChangedPositionCount(int changedPosition) {
        super.setChangedPositionCount(changedPosition);
    }

    @Override
    public int getScoreBaseValue() {
        return super.getScoreBaseValue();
    }

    @Override
    public void setScoreBaseValue(int scoreBaseValue) {
        super.setScoreBaseValue(scoreBaseValue);
    }


}
