package com.example.android.yu_gi_ohcalculator;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private LinearLayout activity_main;
    int lifePoints1 = 8000;
    int lifePoints2 = 8000;
    int characterImageID1 = 2130837621;
    int characterImageID2 = 2130837601;
    int input = 0;
    boolean player = true;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("lp1", lifePoints1);
        outState.putInt("lp2", lifePoints2);
        outState.putInt("characterImage1", characterImageID1);
        outState.putInt("characterImage2", characterImageID2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main = (LinearLayout) findViewById(R.id.activity_main);


        /**Loads old LivePoints*/
        if (savedInstanceState != null) {
            lifePoints1 = savedInstanceState.getInt("lp1");
            lifePoints2 = savedInstanceState.getInt("lp2");
            characterImageID1 = savedInstanceState.getInt("characterImage1");
            characterImageID2 = savedInstanceState.getInt("characterImage2");
        }

        displayLifePoints(lifePoints1, player = true);
        displayLifePoints(lifePoints2, player = false);
        loadCharacter(characterImageID1, characterImageID2);

        /** Creates two MediaPlayers for the sound effects (needs to be in OneCreate)*/
        final MediaPlayer lifePointsReset = MediaPlayer.create(this, R.raw.life_points_reset);
        final MediaPlayer lifePointsDecrease = MediaPlayer.create(this, R.raw.life_points_decrease);


        /**Choose your Character1 method */
        boolean isInLandscapeMode = getResources().getBoolean(R.bool.isLargeLayout);
        if (isInLandscapeMode) {
            //Skips character Loading when Phone is in Landscreen Mode
        } else {
            ImageView character1 = (ImageView) findViewById(R.id.player1);
            character1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CharacterSelectActivity.class);
                    String image_titles[] = getResources().getStringArray(R.array.character_name);
                    intent.putExtra("strings", image_titles);
                    startActivityForResult(intent, 1);
                    player = true;
                }
            });


            /**Choose your Character2 method */
            ImageView character2 = (ImageView) findViewById(R.id.player2);
            character2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CharacterSelectActivity.class);
                    String image_titles[] = getResources().getStringArray(R.array.character_name);
                    intent.putExtra("strings", image_titles);
                    startActivityForResult(intent, 1);
                    player = false;
                }
            });
        }


        /** Resets everything expect character pictures */
        Button playLifePointsReset = (Button) this.findViewById(R.id.reset);

        playLifePointsReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lifePointsReset.start();
                input = 0000;
                lifePoints1 = 8000;
                lifePoints2 = 8000;
                displayInput(input);
                displayLifePoints(lifePoints1, player = true);
                displayLifePoints(lifePoints2, player = false);
                clear(v);
            }
        });
        /** + Player 1 Button function with Sound */
        Button playLifePoints1Increase = (Button) this.findViewById(R.id.player1add);
        playLifePoints1Increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input != 0) {
                    lifePointsDecrease.start();
                    int oldLifePoints1 = lifePoints1;
                    lifePoints1 = lifePoints1 + input;

                    /**Start animation for LifePoints and input*/
                    TextView scoreView = (TextView) findViewById(R.id.life_Points_Player_1);
                    TextView inputView = (TextView) findViewById(R.id.player_Input);
                    animateTextView(oldLifePoints1, lifePoints1, scoreView);
                    animateTextView(input, 0, inputView);
                    input = 0;
                }
            }
        });
        /** - Player 1 Button function with Sound */
        Button playLifePoints1Decrease = (Button) this.findViewById(R.id.player1subtract);

        playLifePoints1Decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input != 0) {
                    lifePointsDecrease.start();
                    int oldLifePoints1 = lifePoints1;
                    lifePoints1 = lifePoints1 - input;
                    if (lifePoints1 <= 0) {
                        lifePoints1 = 0;

                        winner(player = true);

                    }
                    /**Start animation for LifePoints and input*/
                    TextView scoreView = (TextView) findViewById(R.id.life_Points_Player_1);
                    TextView inputView = (TextView) findViewById(R.id.player_Input);
                    animateTextView(oldLifePoints1, lifePoints1, scoreView);
                    animateTextView(input, 0, inputView);
                    input = 0;
                }
            }
        });
        /** + Player 2 Button function with Sound */
        Button playLifePoints2Increase = (Button) this.findViewById(R.id.player2add);

        playLifePoints2Increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input != 0) {
                    lifePointsDecrease.start();
                    int oldLifePoints2 = lifePoints2;
                    lifePoints2 = lifePoints2 + input;

                    /**Start animation for LifePoints and input*/
                    TextView scoreView = (TextView) findViewById(R.id.life_Points_Player_2);
                    TextView inputView = (TextView) findViewById(R.id.player_Input);
                    animateTextView(oldLifePoints2, lifePoints2, scoreView);
                    animateTextView(input, 0, inputView);
                    input = 0;
                }
            }
        });
        /** - Player 2 Button function with Sound */
        Button playLifePoints2Decrease = (Button) this.findViewById(R.id.player2subtract);

        playLifePoints2Decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input != 0) {
                    lifePointsDecrease.start();

                    int oldLifePoints2 = lifePoints2;
                    lifePoints2 = lifePoints2 - input;
                    if (lifePoints2 <= 0) {
                        lifePoints2 = 0;

                        winner(player = false);
                    }

                    /**Start animation for LifePoints and input*/
                    TextView scoreView = (TextView) findViewById(R.id.life_Points_Player_2);
                    TextView inputView = (TextView) findViewById(R.id.player_Input);
                    animateTextView(oldLifePoints2, lifePoints2, scoreView);
                    animateTextView(input, 0, inputView);
                    input = 0;
                }
            }
        });

    }

    /**
     * Handles the data coming from the CharacterSelectActivity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                int image = data.getIntExtra("result", 1);
                Log.v("image", valueOf(image));
                changeCharacter(image);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Code if there is no result
            }
        }
    }

    /**
     * Changes the character every time the user selects a new one
     */
    public void changeCharacter(int image) {
        ImageView character1 = (ImageView) findViewById(R.id.player1);
        ImageView character2 = (ImageView) findViewById(R.id.player2);
        if (player) {
            characterImageID1 = image;
            character1.setImageResource(image);
        } else {
            characterImageID2 = image;
            character2.setImageResource(image);
        }

    }

    /**
     * Used only at start to load the old character
     */
    public void loadCharacter(int characterImageID1, int characterImageID2) {
        boolean isInLandscapeMode = getResources().getBoolean(R.bool.isLargeLayout);
        if (isInLandscapeMode) {
            //does nothing in landScreen Mode
        } else {
            ImageView character1 = (ImageView) findViewById(R.id.player1);
            ImageView character2 = (ImageView) findViewById(R.id.player2);
            character1.setImageResource(characterImageID1);
            character2.setImageResource(characterImageID2);
        }

    }

    /**
     * Displays LifePoints for Player 1 & 2
     */
    public void displayLifePoints(int lifePoints1, boolean player) {
        if (player) {
            TextView scoreView = (TextView) findViewById(R.id.life_Points_Player_1);
            scoreView.setText(valueOf(lifePoints1));
            scoreView.setMaxLines(1);
        } else {
            TextView scoreView = (TextView) findViewById(R.id.life_Points_Player_2);
            scoreView.setText(valueOf(lifePoints2));
            scoreView.setMaxLines(1);
        }
    }

    /**
     * Displays Calculator Input
     */
    public void displayInput(int input) {
        TextView scoreView = (TextView) findViewById(R.id.player_Input);
        scoreView.setText(valueOf(input));
    }

    /**
     * Adds the amount of the button to input, moves to next position
     */
    public void number(View v) {
        Button b = (Button) v;
        input = input * 10;
        input = input + Integer.parseInt(b.getText().toString());
        inputCheck(input);
    }

    /**
     * Clears the input/coin/dice screen
     */
    public void clear(View v) {
        input = 0;
        displayInput(input);

        TextView coinView = (TextView) findViewById(R.id.coin);
        coinView.setText(getResources().getString(R.string.coin));

        TextView diceView = (TextView) findViewById(R.id.dice);
        diceView.setText(getResources().getString(R.string.dice));
    }

    /**
     * Checks if user Input is to high
     */
    public void inputCheck(int input) {
        if (input >= 10000) {
            input = 9999;
            displayInput(input);
            Toast.makeText(this, getResources().getString(R.string.toHigh), Toast.LENGTH_SHORT).show();
        } else {
            displayInput(input);
        }
    }

    /**
     * Flips the coin
     */
    public void coin(View v) {
        Random r = new Random();
        int i1 = r.nextInt(3 - 1) + 1;
        TextView coinView = (TextView) findViewById(R.id.coin);
        if (i1 == 1) {
            coinView.setText(getResources().getString(R.string.heads));
        } else {
            coinView.setText(getResources().getString(R.string.tails));
        }
    }

    /**
     * Rolls the dice
     */
    public void dice(View v) {
        Random r = new Random();
        int i2 = r.nextInt(7 - 1) + 1;

        TextView coinView = (TextView) findViewById(R.id.dice);
        coinView.setText(valueOf(i2));
    }


    /**
     * Animation method for the life Points and Input
     */
    public void animateTextView(int initialValue, int finalValue, final TextView textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(1800);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                textview.setText(valueAnimator.getAnimatedValue().toString());

            }
        });
        valueAnimator.start();

    }

    private void winner(boolean player) {
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.test, null);
        popupWindow = new PopupWindow(container, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        popupWindow.showAtLocation(activity_main, Gravity.CENTER, 0, 0);
        TextView win = (TextView) popupWindow.getContentView().findViewById(R.id.winner);
        if (player)
            win.setText(getResources().getString(R.string.winner2));
        else
            win.setText(getResources().getString(R.string.winner1));
        /**Used to close the window via touch */
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}