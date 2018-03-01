package com.herman.hw3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by herman on 4.05.15.
 */
public class GameOverActivity extends Activity {
    //do stuff here
    private RelativeLayout rLayout;
    private Button retry;
    private Button share;
    private Button mainMenu;

    private TextView scoreText;
    private TextView livesText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gameover);

        rLayout = (RelativeLayout) findViewById(R.id.rLayout);

        retry = (Button) findViewById(R.id.retryButton);
        share = (Button) findViewById(R.id.shareButton);
        mainMenu = (Button) findViewById(R.id.mainMenuButton);

        scoreText = (TextView) findViewById(R.id.scoreText);
        livesText = (TextView) findViewById(R.id.livesLeft);

        final Bundle extras = getIntent().getExtras();

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start the game
                Intent i = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(i);
                finish();
            }});
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start the game
                //TODO: make it actually share something
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "I got "+extras.getInt("score")+" points on Bubblr!");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }});
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: dont make a new intent, just close all others and goto main menu
                //start the game
                finish();
            }});


        if (extras != null) {
            scoreText.setText("Score: "+extras.getInt("score"));
            livesText.setText("Lives left: "+extras.getInt("lives"));

        }


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
}
