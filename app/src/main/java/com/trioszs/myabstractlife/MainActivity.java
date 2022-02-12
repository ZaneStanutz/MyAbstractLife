
package com.trioszs.myabstractlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;
import java.lang.*;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'myabstractlife' library on application startup.
    static {
        System.loadLibrary("myabstractlife");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnCalculate = findViewById(R.id.calc_button);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double yearBorn = 0.0;
                double monthBorn = 0.0;
                double dayBorn = 0.0;


                //only parse et views if they are not an empty string
                EditText et1 = findViewById(R.id.editText_birthYear);
                if (et1.getText().toString().equals("")) {
                    // do nothing..
                } else {
                    yearBorn = Integer.parseInt(et1.getText().toString());
                }

                EditText et2 = findViewById(R.id.editText_birthMonth);
                if (et2.getText().toString().equals("")) {
                    //do nothing..
                } else {
                    monthBorn = Integer.parseInt(et2.getText().toString());
                }

                EditText et3 = findViewById(R.id.editText_birthDay);
                if (et3.getText().toString().equals("")) {
                    //do nothing
                } else {
                    dayBorn = Integer.parseInt(et3.getText().toString());
                }

                //only perform call to JNI if we have input values for all arguments
                if (yearBorn != 0.0 && monthBorn != 0.0 && dayBorn != 0.0) {
                    String result = resultFromJNI(yearBorn, monthBorn, dayBorn);

                    List<String> responseElements = Arrays.asList(result.split("\\."));

                    String seconds = responseElements.get(0);
                    String secondsCommify = commify(seconds);
                    TextView tv1 = findViewById(R.id.textViewSeconds);
                    tv1.setText(secondsCommify); // use even subscript to omit .0000 from rounded values

                    String minutes = responseElements.get(2);
                    String minutesCommify = commify(minutes);
                    TextView tv2 = findViewById(R.id.textViewMinutes);
                    tv2.setText(minutesCommify);

                    String hours = responseElements.get(4);
                    String hoursCommify = commify(hours);
                    TextView tv3 = findViewById(R.id.textViewHours);
                    tv3.setText(hoursCommify);

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter values for ALL three fields :)", Toast.LENGTH_LONG).show();
                }

            } // onClick

        }); // setOnClickListener()

    } // OnCreate

    protected String commify(@NonNull String number) {
        char[] splitNumber = number.toCharArray(); // convert String to array of char
        StringBuilder commafiedStringBuild = new StringBuilder(); // create string builder object
        String commafiedNumber;
        char digit;

        for (int j = 1 , i = splitNumber.length -1; i >= 0; i--,j++) {
            commafiedStringBuild.insert(0, splitNumber[i]);
            if(j%3 == 0 && i != 0) {
                commafiedStringBuild.insert(0, ",");
            }
            else{
                continue;
            }

            }
        commafiedNumber = commafiedStringBuild.toString();
        return commafiedNumber;
        }

    public native String resultFromJNI(double yearBorn,double monthBorn, double dayBorn);

} // Main