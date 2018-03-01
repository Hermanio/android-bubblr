package com.herman.hw3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {

    /**
     * Some global variables.
     */
    private static final String PREFERENCES = "mySettings";

    private RelativeLayout mainMenuLayout;
    private Button startButton;
    private Button optionsButton;
    private Button exitButton;

    private SharedPreferences preferences;


    /**
     * Sets up the main menu.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainMenuLayout = (RelativeLayout) findViewById(R.id.mainMenu);
        startButton = (Button) findViewById(R.id.startButton);
        optionsButton = (Button) findViewById(R.id.optionsButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start the game
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
            }});
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(i);
            }});
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //exit
                //probably confirmation would be nice.
                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);*/
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }});


        initPreferences();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


    }

    /**
     * Checks if the preferences are initialised, if not it gives them some default values.
     */
    public void initPreferences() {
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        if (preferences.contains("numberOfElements")) {
            //file exists, we can now chill
        } else {

            //create defaults
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("numberOfElements", 5);
            editor.putInt("sizeOfElements", 100);
            editor.putInt("squareSpeedOfChange", 1000);
            editor.putInt("circleSpeedOfChange", 800);
            editor.putInt("elementCreationSpeed", 50);
            editor.putInt("time", 10000);
            editor.putBoolean("debugMode", false);
            editor.putBoolean("animatedShapes", true);
            editor.putInt("animationSpeed", 50);

            editor.commit();
        }
    }


}
