package com.bluezooweb.eggtimer;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Developed by Lovish Pandey

    private static final String TAG = "Main";
    SeekBar seekbar;
    TextView textView;
    Button button;

    int time = 0;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.timer);
        seekbar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);

        seekbar.setMax(630);
        seekbar.setProgress(30);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekbar.setMin(30);
        }

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int minutes = progress / 60;
                int seconds = progress - minutes * 60;

                String secs = Integer.toString(seconds);
                if (secs.length() == 1) {
                    secs = "0" + Integer.toString(seconds);
                }

                textView.setText(String.valueOf(minutes) + ":" + secs);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String operation = button.getText().toString();

                int progress = seekbar.getProgress();
                if (progress < 30) {
                    seekbar.setProgress(30);
                }

                switch (operation) {
                    case "Start":
                        //start the countdown;
                        Log.i(TAG, "onClick: Progress: " + progress);
                        startCountDown(progress);
                        break;
                    case "Stop":
                        //stop the countdown and reset;
                        reset();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Invalid Operation", Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        });


    }

    private void reset() {
        countDownTimer.cancel();

        seekbar.setVisibility(View.VISIBLE);

        button.setText("Start");
        textView.setText("00:30");
        seekbar.setProgress(30);

    }

    private void startCountDown(int progress) {
        seekbar.setVisibility(View.INVISIBLE);
        button.setText("Stop");

        countDownTimer = new CountDownTimer(progress * 1000, 1000) {

            @Override
            public void onTick(long l) {
                long minutes = l / 60000;
                long seconds = (l / 1000) - minutes * 60;

                String secs = Long.toString(seconds);

                if (secs.length() == 1) {
                    secs = "0" + Long.toString(seconds);
                }

                textView.setText(String.valueOf(minutes) + ":" + secs);
            }

            @Override
            public void onFinish() {
                reset();
            }

        }.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Credit");
            builder.setMessage(" Icon made by Roundicons Freebies from www.flaticon.com ").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
