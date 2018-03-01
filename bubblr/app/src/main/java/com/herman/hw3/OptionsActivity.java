package com.herman.hw3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by herman on 4.05.15.
 */
public class OptionsActivity extends Activity {

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

    private static final int MIN_NUMBER_OF_ELEMENTS = 2;
    private static final int MIN_SIZE_OF_ELEMENTS = 20;
    private static final int MIN_SQUARE_SPEED_OF_CHANGE = 200;
    private static final int MIN_CIRCLE_SPEED_OF_CHANGE = 200;
    private static final int MIN_ELEMENT_CREATION_SPEED = 16;
    private static final int MIN_TIME_LIMIT = 10000;
    private static final int MIN_ANIMATION_SPEED = 10;


    private RelativeLayout rLayout;

    private SeekBar numberOfElementsSeekBar;
    private SeekBar sizeOfElementsSeekBar;
    private SeekBar squareSpeedOfChangeSeekBar;
    private SeekBar circleSpeedOfChangeSeekBar;
    private SeekBar elementCreationSpeedSeekBar;
    private SeekBar timeSeekBar;
    private SeekBar animationSpeedSeekBar;

    private TextView nrOfElementsTextBox;
    private TextView sizeOfElementsTextBox;
    private TextView squareSpeedOfChangeTextBox;
    private TextView circleSpeedOfChangeTextBox;
    private TextView elementCreationSpeedTextBox;
    private TextView timeTextBox;
    private TextView animationSpeedTextBox;

    private Button saveButton;

    private Switch debugSwitch;
    private Switch animatedShapesSwitch;


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private int numberOfElements;
    private int sizeOfElements;
    private int squareSpeedOfChange;
    private int circleSpeedOfChange;
    private int elementCreationSpeed;
    private int time;
    private boolean debugMode;
    private boolean animatedShapes;
    private int animationSpeed;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options2);

        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void init() {

        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        editor = preferences.edit();

        numberOfElements = preferences.getInt(NUMBER_OF_ELEMENTS, -1);
        sizeOfElements = preferences.getInt(SIZE_OF_ELEMENTS, -1);
        squareSpeedOfChange = preferences.getInt(SQUARE_SPEED_OF_CHANGE, -1);
        circleSpeedOfChange = preferences.getInt(CIRCLE_SPEED_OF_CHANGE, -1);
        elementCreationSpeed = preferences.getInt(ELEMENT_CREATION_SPEED, -1);
        time = preferences.getInt(TIME, -1);
        debugMode = preferences.getBoolean(DEBUG_MODE, false);
        animatedShapes = preferences.getBoolean(ANIMATED_SHAPES, false);
        animationSpeed = preferences.getInt(ANIMATION_SPEED, -1);


        rLayout = (RelativeLayout) findViewById(R.id.rLayout);

        nrOfElementsTextBox = (TextView) findViewById(R.id.nrOfElementsTextBox);
        sizeOfElementsTextBox = (TextView) findViewById(R.id.sizeOfElementsTextBox);
        squareSpeedOfChangeTextBox = (TextView) findViewById(R.id.squareSpeedOfChangeTextBox);
        circleSpeedOfChangeTextBox = (TextView) findViewById(R.id.circleSpeedOfChangeTextBox);
        elementCreationSpeedTextBox = (TextView) findViewById(R.id.elementCreationSpeedTextBox);
        timeTextBox = (TextView) findViewById(R.id.timeTextBox);
        animationSpeedTextBox = (TextView) findViewById(R.id.animationSpeedTextBox);

        nrOfElementsTextBox.setText("Number of elements: "+ numberOfElements);
        sizeOfElementsTextBox.setText("Element size: "+ sizeOfElements);
        squareSpeedOfChangeTextBox.setText("Square timer (ms): "+ squareSpeedOfChange);
        circleSpeedOfChangeTextBox.setText("Circle timer (ms): "+ circleSpeedOfChange);
        elementCreationSpeedTextBox.setText("Element creation speed (ms): "+ elementCreationSpeed);
        timeTextBox.setText("Game length (s): "+ time / 1000);
        animationSpeedTextBox.setText("Animation speed (ms): " + animationSpeed);

        numberOfElementsSeekBar = (SeekBar) findViewById(R.id.numberOfElements);
        numberOfElementsSeekBar.setProgress(numberOfElements - MIN_NUMBER_OF_ELEMENTS);

        sizeOfElementsSeekBar = (SeekBar) findViewById(R.id.sizeOfElements);
        sizeOfElementsSeekBar.setProgress(sizeOfElements - MIN_SIZE_OF_ELEMENTS);

        squareSpeedOfChangeSeekBar = (SeekBar) findViewById(R.id.squareSpeedOfChange);
        squareSpeedOfChangeSeekBar.setProgress(squareSpeedOfChange - MIN_SQUARE_SPEED_OF_CHANGE);

        circleSpeedOfChangeSeekBar = (SeekBar) findViewById(R.id.circleSpeedOfChange);
        circleSpeedOfChangeSeekBar.setProgress(circleSpeedOfChange - MIN_CIRCLE_SPEED_OF_CHANGE);

        elementCreationSpeedSeekBar = (SeekBar) findViewById(R.id.elementCreationSpeed);
        elementCreationSpeedSeekBar.setProgress(elementCreationSpeed - MIN_ELEMENT_CREATION_SPEED);

        timeSeekBar = (SeekBar) findViewById(R.id.time);
        timeSeekBar.setProgress(time - MIN_TIME_LIMIT);

        animationSpeedSeekBar = (SeekBar) findViewById(R.id.animationSpeed);
        animationSpeedSeekBar.setProgress(animationSpeed - MIN_ANIMATION_SPEED);

        saveButton = (Button) findViewById(R.id.saveButton);

        debugSwitch = (Switch) findViewById(R.id.debugSwitch);
        debugSwitch.setChecked(debugMode);

        debugSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                editor.putBoolean(DEBUG_MODE, isChecked);
            }
        });
        animatedShapesSwitch = (Switch) findViewById(R.id.animatedShapesSwitch);
        animatedShapesSwitch.setChecked(animatedShapes);

        animatedShapesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                editor.putBoolean(ANIMATED_SHAPES, isChecked);
            }
        });


        numberOfElementsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int result = i + MIN_NUMBER_OF_ELEMENTS;
                editor.putInt(NUMBER_OF_ELEMENTS, result);
                nrOfElementsTextBox.setText("Number of elements: "+ result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sizeOfElementsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int result = i + MIN_SIZE_OF_ELEMENTS;
                editor.putInt(SIZE_OF_ELEMENTS, result);
                sizeOfElementsTextBox.setText("Element size: "+ result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        squareSpeedOfChangeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int result = i + MIN_SQUARE_SPEED_OF_CHANGE;
                editor.putInt(SQUARE_SPEED_OF_CHANGE, result);
                squareSpeedOfChangeTextBox.setText("Square timer (ms): "+ result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        circleSpeedOfChangeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int result = i + MIN_CIRCLE_SPEED_OF_CHANGE;
                editor.putInt(CIRCLE_SPEED_OF_CHANGE, result);
                circleSpeedOfChangeTextBox.setText("Circle timer (ms): "+ result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        elementCreationSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int result = i + MIN_ELEMENT_CREATION_SPEED;
                editor.putInt(ELEMENT_CREATION_SPEED, result);
                elementCreationSpeedTextBox.setText("Element creation speed (ms): "+ result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int result = i + MIN_TIME_LIMIT;
                editor.putInt(TIME, result);
                timeTextBox.setText("Game length (s): "+ result / 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        animationSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int result = i + MIN_ANIMATION_SPEED;
                editor.putInt(ANIMATION_SPEED, result);
                animationSpeedTextBox.setText("Animation speed (ms): "+ result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: check for invalid values
                editor.commit();
                finish();
            }
        });
    }
}
