package com.example.android.baccalculator;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    private EditText Weight;
    private EditText Time;
    private EditText Drinks;

    private int GenderValue = 0;
    private SeekBar seek_Bar1;
    private TextView Seekbar1_display;

    private CheckBox standard_drink_checkB;

    private int DrinkTypeValue = 1;
    private SeekBar seek_Bar2;
    private TextView Seekbar2_display;

    private int ABVValue = 15;
    private SeekBar seek_Bar3;
    private TextView Seekbar3_display;

    private TextView BAC_display;

    private AlertDialog DDriver;
    private AlertDialog GDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Weight = (EditText) findViewById(R.id.Weight_input);
        Time = (EditText) findViewById(R.id.Time_input);
        Drinks = (EditText) findViewById(R.id.Drink_input);

        seek_Bar1 = (SeekBar) findViewById(R.id.seekbar1);
        Seekbar1_display = (TextView) findViewById(R.id.SeekbarDisplay);
        seek_Bar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                GenderValue = progress;

                if (GenderValue == 0) {
                    Seekbar1_display.setText("Male");
                }
                else {
                    Seekbar1_display.setText("Female");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        standard_drink_checkB = (CheckBox) findViewById(R.id.SD_checkbox);

        seek_Bar2 = (SeekBar) findViewById(R.id.seekbar2);
        Seekbar2_display = (TextView) findViewById(R.id.SeekbarDisplay2);
        seek_Bar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int pro, boolean b) {
                DrinkTypeValue = pro;

                if (DrinkTypeValue == 0) {
                    Seekbar2_display.setText("Beer");
                }
                else if (DrinkTypeValue == 1) {
                    Seekbar2_display.setText("Wine");
                }
                else {
                    Seekbar2_display.setText("Liquor");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek_Bar3 = (SeekBar) findViewById(R.id.seekbar3);
        Seekbar3_display = (TextView) findViewById(R.id.SeekbarDisplay3);
        seek_Bar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ABVValue = i;
                Seekbar3_display.setText("" + i + "% ABV");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        BAC_display = (TextView) findViewById(R.id.BMI_output);

        Build_the_Alert_Dialoag();
        Build_the_Good_Dialoag();

        if (savedInstanceState != null)  {
            BAC_display.setText(savedInstanceState.getString("BAC"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)  {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("BAC", BAC_display.getText().toString());

    }

    public void Build_the_Alert_Dialoag() {
        AlertDialog.Builder SafetyNotice = new AlertDialog.Builder(MainActivity.this);
        SafetyNotice.setTitle("STOP!!");
        SafetyNotice.setMessage("Driving over the legal limit of 0.08 BAC is illegal, and unsafe." +
                "  Please wait until your alcohol level has decreased");
        SafetyNotice.setPositiveButton("EXIT SCREEN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        DDriver = SafetyNotice.create();
    }

    public void Build_the_Good_Dialoag() {
        AlertDialog.Builder SafetyNotice = new AlertDialog.Builder(MainActivity.this);
        SafetyNotice.setTitle("Have a good night!");
        SafetyNotice.setMessage("You're in a legal level of BAC to drive.  " +
                "Still, be careful and stay alert on the road!");
        SafetyNotice.setPositiveButton("EXIT SCREEN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        GDriver = SafetyNotice.create();
    }


    public void onClickBtn(View v) {
        if (Weight.getText().toString().equals("") || Weight.getText().toString().isEmpty())  {
            Toast.makeText(this, "All Input fields must be filled", Toast.LENGTH_LONG).show();
            return;
        }
        double PersonWeightInput_LBS = Double.parseDouble(Weight.getText().toString());
        if (Time.getText().toString().equals("") || Time.getText().toString().isEmpty())  {
            Toast.makeText(this, "All Input fields must be filled", Toast.LENGTH_LONG).show();
            return;
        }
        double TimeElapsed_HRS = Double.parseDouble(Time.getText().toString());
        if (Drinks.getText().toString().equals("") || Drinks.getText().toString().isEmpty())  {
            Toast.makeText(this, "All Input fields must be filled", Toast.LENGTH_LONG).show();
            return;
        }
        double Number_of_Drinks = Double.parseDouble(Drinks.getText().toString());

        double PersonWeight_GRAMS = PersonWeightInput_LBS * 454;
        double GenderConstant;

        if (GenderValue == 0) {
            GenderConstant = 0.68;
        }
        else {
            GenderConstant = 0.55;
        }

        double grams_alcohol_perdrink;
        double VOL;
        double ABV = ABVValue/100;
        double FULLBAC;
        double TimeAdjustedBAC;

        if (standard_drink_checkB.isChecked()) {
            FULLBAC = ((14*Number_of_Drinks)/(PersonWeight_GRAMS*GenderConstant))*100;
            TimeAdjustedBAC = (FULLBAC - (TimeElapsed_HRS * 0.015));
        }
        else {
            if (DrinkTypeValue == 0) {
                VOL = 350;
            }
            else if (DrinkTypeValue == 1) {
                VOL = 150;
            }
            else {
                VOL = 44;
            }

            grams_alcohol_perdrink = ((ABV)*(VOL)*(0.789));
            FULLBAC = ((grams_alcohol_perdrink*Number_of_Drinks)/(PersonWeight_GRAMS*GenderConstant))*100;
            TimeAdjustedBAC = (FULLBAC - (TimeElapsed_HRS * 0.015));
        }

        BAC_display.setText(String.format("%.2f", TimeAdjustedBAC));

        if (TimeAdjustedBAC <= 0.08) {
            GDriver.show();
        }
        else {
            DDriver.show();
        }


    }

}
