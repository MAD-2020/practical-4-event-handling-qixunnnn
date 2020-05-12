package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */

    final String TAG = "Whack-A-Mole! 2.0";
    private TextView txtScore;
    private int advancedScore;
    private CountDownTimer start_cdt;
    private CountDownTimer newMole_cdt;

    private void readyTimer(){
        start_cdt = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                Toast.makeText(getApplicationContext(),"Get Ready In " + l/1000 + " seconds", Toast.LENGTH_SHORT);
                Log.v(TAG, "Ready CountDown!" + l/ 1000);
                buttonsControl(false);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"GO!", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Ready CountDown Complete!");
                start_cdt.cancel();

                buttonsControl(true);
                placeMoleTimer();
            }
        };
        start_cdt.start();
    }


    private void placeMoleTimer(){
        newMole_cdt = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG,"New Mole Location");
                setNewMole();
            }

            @Override
            public void onFinish() {
                newMole_cdt.start();
            }
        };
        newMole_cdt.start();
    }
    private static final int[] BUTTON_IDS = {
            R.id.T_btnLeft, R.id.T_btnCenter, R.id.T_btnRight,
            R.id.M_btnLeft, R.id.M_btnCenter, R.id.M_btnRight,
            R.id.B_btnLeft, R.id.B_btnCenter, R.id.B_btnRight

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        advancedScore = intent.getIntExtra("score",0);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtScore.setText(Integer.toString(advancedScore));
        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));


        for(final int id : BUTTON_IDS){
            findViewById(id).setOnClickListener(this);
        }
        readyTimer();
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    private void doCheck(Button checkButton)
    {
        if (checkButton.getText() == "*")
        {
            advancedScore++;
            Log.v(TAG,"Hit, score added!");
        }
        else {
            advancedScore--;
            Log.v(TAG,"Missed, score deducted");
        }
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);

        Button b;
        for(final int id : BUTTON_IDS){
           b = findViewById(id);
           b.setText("O");
        }

        Button selectedBut = findViewById(BUTTON_IDS[randomLocation]);
        selectedBut.setText("*");
    }

    @Override
    public void onClick(View v) {
        for(int i=0;i<BUTTON_IDS.length;i++)
        {
            if (v.getId() == BUTTON_IDS[i])
            {
                Button clickedBut = findViewById(BUTTON_IDS[i]);
                doCheck(clickedBut);
                break;
            }
        }
        txtScore.setText(Integer.toString(advancedScore));
        setNewMole();
    }
    public void buttonsControl(boolean butCon)
    {
        Button b;
        if (butCon){
            for(final int id : BUTTON_IDS){
                b = findViewById(id);
                b.setClickable(true);
            }
        }
        else{
            for(final int id : BUTTON_IDS) {
                b = findViewById(id);
                b.setClickable(false);
            }
        }
    }
}

