package com.herman.hw3;

/*
TODO: lives will be lost if moving element results in not finding a spot
maybe make it a feature???
TODO: set limits on options on the click of 'save' button
TODO: implement lives lost when element has to disappear
TODO: set temp button to null
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

/**
 * Created by herman on 3.05.15.
 */
public class GameActivity extends Activity {

    private RelativeLayout gameBoard;
    /**
     * Constants for use with preferences lookup.
     */
    private static final String PREFERENCES = "mySettings";

    private static final String NUMBER_OF_ELEMENTS = "numberOfElements";
    private static final String SIZE_OF_ELEMENTS = "sizeOfElements";
    private static final String SQUARE_SPEED_OF_CHANGE = "squareSpeedOfChange";
    private static final String CIRCLE_SPEED_OF_CHANGE = "circleSpeedOfChange";
    private static final String ELEMENT_CREATION_SPEED = "elementCreationSpeed";
    private static final String TIME = "time";
    private static final String DEBUG_MODE = "debugMode";
    private static final String ANIMATED_SHAPES = "animatedShapes";
    private static final String ANIMATION_SPEED = "animationSpeed";






    /**
     * Various global variables for initialization purposes.
     */

    /*BUG: Android does not get layout width-height right at the start
    so if someone were to make the element spawn time really small then
    Android just would say that width and height are 0 because it has to draw
    the elements to the screen and only then can it retrieve some data

    this is a stupid hack to fix this issue
    */
    private int width = 450;
    private int height = 450;

    private TextView scoreBox;
    private TextView timeBox;
    private TextView livesBox;

    private Thread t;
    private Thread tGUI;


    private CountDownTimer cTimer;

    private SharedPreferences preferences;

    private int numberOfElements;
    private int sizeOfElements;
    private int squareSpeedOfChange;
    private int circleSpeedOfChange;
    private int elementCreationSpeed;
    private int time;
    private boolean debugMode;
    private boolean animatedShapes;
    private int animationSpeed;


    /**
     * Variables for score and lives, shapeCount for debug purposes.
     */
    private int score = 0;
    private int lives = 5;
    private int shapeCount = 0;


    /**
     * Prepares the game and then starts the game.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);

        gameBoard = (RelativeLayout) findViewById(R.id.gameBoard);

        scoreBox = (TextView) findViewById(R.id.scoreBox);
        timeBox = (TextView) findViewById(R.id.timeBox);
        livesBox = (TextView) findViewById(R.id.livesBox);

        /*settings initialisation process*/
        init();
        startGame();
    }

    /**
     * Handling pause events, in this case it mercilessly stops everything.
     */
    @Override
    protected void onPause() {
        super.onPause();
        t.interrupt();
        tGUI.interrupt();
        cTimer.cancel();
        finish();
    }

    /**
     * Handles stop event.
     */
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    /**
     * Gives the variables width and height values for later use.
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        width = gameBoard.getWidth();
        height = gameBoard.getHeight();
    }

    /**
     * Settings initialization process.
     */
    public void init() {


        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        numberOfElements = preferences.getInt(NUMBER_OF_ELEMENTS, -1);
        sizeOfElements = preferences.getInt(SIZE_OF_ELEMENTS, -1);
        squareSpeedOfChange = preferences.getInt(SQUARE_SPEED_OF_CHANGE, -1);
        circleSpeedOfChange = preferences.getInt(CIRCLE_SPEED_OF_CHANGE, -1);
        elementCreationSpeed = preferences.getInt(ELEMENT_CREATION_SPEED, -1);
        time = preferences.getInt(TIME, -1);
        debugMode = preferences.getBoolean(DEBUG_MODE, false);
        animatedShapes = preferences.getBoolean(ANIMATED_SHAPES, false);
        animationSpeed = preferences.getInt(ANIMATION_SPEED, -1);


    }


    /**
     * Random int generator.
     * @param max- max value to be generated.
     * @return the random integer.
     */
    private int randomInt(int max) {
        return new Random().nextInt(max + 1);
    }


    /**
     * Creates a square.
     * @return SquareButton.
     */
    private SquareButton createSquare() {
        SquareButton button = new SquareButton(this, null, squareSpeedOfChange);
        button.setSize(sizeOfElements);
        button.setScoreBaseValue(sizeOfElements);
        return button;
    }

    /**
     * Creates a circle.
     * @return CircleButton.
     */
    private CircleButton createCircle() {
        CircleButton button = new CircleButton(this, null, circleSpeedOfChange);

        button.setSize(sizeOfElements);
        button.setScoreBaseValue(sizeOfElements);
        return button;
    }

    /**
     * Adds a shape to the layout.
     * @param layout the layout where the shape is put.
     * @param button the button to be added.
     */
    private void addShape(final RelativeLayout layout, final AdvancedButton button) {
        if (this.getShapeCount() < numberOfElements) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    score += button.getScoreBaseValue() / (button.getChangedPositionCount()  + 1);

                    layout.removeView(button);

                    GameActivity.this.setShapeCount(GameActivity.this.getShapeCount() - 1);


                }
            });

            setNewShapeLocation(layout, button, true);
        }

    }

    /**
     * Sets a new location for the shape.
     * @param layout the layout where the shape is
     * @param button the button to change position
     * @param newElement if newElement, we do not apply any transitions, else we do if
     *                   settings say so
     */
    private void setNewShapeLocation(final RelativeLayout layout, final AdvancedButton button, boolean newElement) {
        int buttonSize = button.getSize();

        int buttonOldX = (int) button.getX();
        int buttonOldY = (int) button.getY();

        int buttonNewX = randomInt(width - buttonSize);
        int buttonNewY = randomInt(height - buttonSize);




        button.setX(buttonNewX);
        button.setY(buttonNewY);

        int itr = 0;
        while(true) {


            if (checkIfShapeIntersects(layout, button)) {

                buttonNewX = randomInt(width - buttonSize);
                buttonNewY = randomInt(height - buttonSize);

                button.setX(buttonNewX);
                button.setY(buttonNewY);
                //System.out.println("INTERSECTS");


            } else {

                if (newElement) {

                    layout.addView(button);
                    this.setShapeCount(this.getShapeCount() + 1);
                } else {

                    if (animatedShapes) {
                        final AdvancedButton tempButton;
                        tempButton = new AdvancedButton(this, null, Integer.MAX_VALUE - 1000 );
                        tempButton.setVisibility(View.INVISIBLE);
                        tempButton.setSize(sizeOfElements);
                        tempButton.setClickable(false);
                        tempButton.setX(buttonNewX);
                        tempButton.setY(buttonNewY);
                        layout.addView(tempButton);

                        button.setX(buttonOldX);
                        button.setY(buttonOldY);

                        Animation translateAnimation;

                        translateAnimation = new TranslateAnimation(
                                0.0f, buttonNewX - buttonOldX, 0.0f, buttonNewY - buttonOldY
                        );

                        final int finalButtonNewX = buttonNewX;
                        final int finalButtonNewY = buttonNewY;
                        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                                animation.setDuration(1);
                                button.startAnimation(animation);
                                button.setX(finalButtonNewX);
                                button.setY(finalButtonNewY);
                                //System.out.println("TEMPBUTTON x y size "+tempButton.getX()+" "+tempButton.getY()+" "+tempButton.getSize());
                                layout.removeView(tempButton);

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });


                        translateAnimation.setDuration(animationSpeed);
                        button.startAnimation(translateAnimation);
                        translateAnimation = null;
                    } else {
                        button.setX(buttonNewX);
                        button.setY(buttonNewY);
                    }



                }

                break;
            }

            itr++;
            if (itr >= 10) {
                //System.out.println("Failed to find a free spot for a shape.");
                if (!newElement) {
                    layout.removeView(button);
                    removeLife();

                    this.setShapeCount(this.getShapeCount() - 1);
                }

                break;
            }
        }

    }


    /**
     * Updates the GUI.
     */
    private void updateGUI() {
        scoreBox.setText("Score: "+this.score);
        livesBox.setText("Lives left: "+this.lives);
    }

    /**
     * Removes a life from the player, then checks if the game should end.
     */
    private void removeLife() {
        this.lives--;
        this.score -= 1000;
        if (lives == 0) {
            endGame();
        }
    }

    /**
     * Updates all of the elements in the game according to each elements
     * parameters which are changed by each elements' timers.
     * @param layout the layout where the elements are.
     */
    private void updateElements(RelativeLayout layout) {
        View v;
        AdvancedButton advancedButton;
        for (int i = 0; i < layout.getChildCount(); i++) {
            v = layout.getChildAt(i);
            if (v instanceof AdvancedButton) {
                advancedButton = (AdvancedButton) v;
                if (advancedButton.isToRemove()) {
                    layout.removeView(advancedButton);
                    advancedButton = null;
                    this.setShapeCount(this.getShapeCount() - 1);
                    //because user was unable to remove element we take 1 life away

                    if (!debugMode) {
                        removeLife();
                    }

                } else {
                    if (advancedButton.isToChangePosition()) {
                        //change its location
                        //if cant change then remove
                        advancedButton.setToChangePosition(false);
                        setNewShapeLocation(layout, advancedButton, false);

                    }
                }
            }
        }

    }


    /**
     * DEPRECATED, HERE FOR LEGACY PURPOSES.
     * @param layout
     * @param x
     * @param y
     * @param size
     * @return
     */
    private boolean checkIfShapeIntersects(RelativeLayout layout, float x, float y, float size) {



        int childCount = layout.getChildCount();
        View v;

        int cx1 = (int) x;
        int cx2 = (int) (cx1 + size);
        int cy1 = (int) y;
        int cy2 = (int) (cy1 + size);

        int ox1, ox2, oy1, oy2;

        int childWidth;
        int childHeight;

        for (int i = 0; i < childCount; i++) {
            v = layout.getChildAt(i);

            ox1 = (int) v.getX();
            oy1 = (int) v.getY();


            if (v instanceof SquareButton) {
                SquareButton temp = (SquareButton) v;
                childWidth = temp.getSize();
                childHeight = temp.getSize();


            } else if (v instanceof CircleButton) {
                CircleButton temp = (CircleButton) v;
                childWidth = temp.getSize();
                childHeight = temp.getSize();

            } else {
                childWidth = v.getWidth();
                childHeight = v.getHeight();
            }
            ox2 = ox1 + childWidth;
            oy2 = oy1 + childHeight;


            if (!(cx1 >= ox2 || cx2 <= ox1 || cy1 >= oy2 || cy2 <= oy1)) {
                return true;
            }
        }
        return false;


    }

    /**
     * Checks if the button intersects with any of the shapes on the layout.
     * @param layout the layout where the elements are.
     * @param button the button to check against.
     * @return if intersects, true, else false
     */
    private boolean checkIfShapeIntersects(RelativeLayout layout, AdvancedButton button) {


        int childCount = layout.getChildCount();
        View v;

        int cx1 = (int) button.getX();
        int cx2 = cx1 + button.getSize();
        int cy1 = (int) button.getY();
        int cy2 = cy1 + button.getSize();

        int ox1, ox2, oy1, oy2;

        int childWidth;
        int childHeight;

        for (int i = 0; i < childCount; i++) {
            v = layout.getChildAt(i);

            ox1 = (int) v.getX();
            oy1 = (int) v.getY();


            if (v instanceof AdvancedButton) {
                AdvancedButton temp = (AdvancedButton) v;
                if (button == temp) {
                    //System.out.println("WE ARE NOT COMPARING IT TO ITSELF YO");
                    continue;
                }
                childWidth = temp.getSize();
                childHeight = temp.getSize();

            } else {
                childWidth = v.getWidth();
                childHeight = v.getHeight();
            }
            ox2 = ox1 + childWidth;
            oy2 = oy1 + childHeight;


            if (!(cx1 >= ox2 || cx2 <= ox1 || cy1 >= oy2 || cy2 <= oy1)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Ends the game.
     */
    private void endGame() {
        Intent i = new Intent(GameActivity.this, GameOverActivity.class);
        i.putExtra("score", score);
        i.putExtra("lives", lives);
        startActivity(i);
        finish();

    }


    /**
     * Starts the game.
     */
    private void startGame() {

        t = new Thread() {
            @Override
            public void run() {
                gameBoard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeLife();
                    }});
                try {
                    while (true) {



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ((new Random()).nextBoolean()) {
                                    addShape(gameBoard, createSquare());
                                    addShape(gameBoard, createCircle());
                                } else {
                                    addShape(gameBoard, createCircle());
                                    addShape(gameBoard, createSquare());
                                }



                            }
                        });
                        Thread.sleep(elementCreationSpeed);
                       // System.out.println(shapeCount);

                    }
                } catch (InterruptedException e) {}

            }
        };
        tGUI = new Thread() {
            @Override
            public void run() {

                try {

                    while (true) {
                        /*about 60fps*/
                        Thread.sleep(16);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                updateGUI();
                                updateElements(gameBoard);
                            }
                        });
                    }
                } catch (InterruptedException e) {}
            }
        };



        cTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                timeBox.setText("Time: " + millisUntilFinished / 1000);
                //while (elementCreationSpeed > )
            }

            public void onFinish() {
                timeBox.setText("Time's up!");
                endGame();

            }

        };
        t.start();
        tGUI.start();
        cTimer.start();

    }

    public int getShapeCount() {
        return shapeCount;
    }

    public void setShapeCount(int shapeCount) {
        this.shapeCount = shapeCount;
    }
}
