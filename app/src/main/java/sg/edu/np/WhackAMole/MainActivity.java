package sg.edu.np.WhackAMole;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    final String TAG = "Whack-A-Mole 1.0!";
    private Button leftBtn;
    private Button midBtn;
    private Button rightBtn;
    private TextView txtScore;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Whack-A-Mole!");
        leftBtn = (Button) findViewById(R.id.btnLeft);
        midBtn = (Button) findViewById(R.id.btnCenter);
        rightBtn = (Button) findViewById(R.id.btnRight);

        leftBtn.setOnClickListener(this);
        midBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);

        txtScore = (TextView) findViewById(R.id.txtScore);

        Log.v(TAG, "Finished Pre-Initialisation!");

    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    int iScore = 0;
    int highestTens = 0;
    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (checkButton.getText() == "*")
        {
            iScore++;
            Log.v(TAG,"Hit, score added!");
        }
        else {
            iScore--;
            Log.v(TAG,"Missed, score deducted");
        }

        if (iScore % 10 == 0 && iScore > 0 && iScore > highestTens)
        {
            highestTens = iScore;
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here. */
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Warning! Insane Whack-A-Mole Incoming!");
        dialog.setMessage("Would you like to advance to advanced mode?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nextLevel();
                Log.v(TAG, "User accepts!");
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alertBox = dialog.create();
        alertBox.show();
        Log.v(TAG, "Advance option given to user!");
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent toMainAct2 = new Intent(MainActivity.this, Main2Activity.class);
        toMainAct2.putExtra("score",iScore);
        startActivity(toMainAct2);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);

        List<Button> btnList = new ArrayList<Button>();
        btnList.add(leftBtn);
        btnList.add(midBtn);
        btnList.add(rightBtn);

        //Reset
        for (int i = 0;i<btnList.size();i++){
            btnList.get(i).setText("O");
        }

        btnList.get(randomLocation).setText("*");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLeft:
                Log.v(TAG,"Button Left Clicked!");
                doCheck(leftBtn);
                break;

            case R.id.btnCenter:
                Log.v(TAG,"Button Middle Clicked!");
                doCheck(midBtn);
                break;

            case R.id.btnRight:
                Log.v(TAG,"Button Right Clicked!");
                doCheck(rightBtn);
                break;

        }
        txtScore.setText(Integer.toString(iScore));
        setNewMole();
    }
}