package com.androcrush.sqlite2;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.Math;
import androidx.appcompat.app.AppCompatActivity;

public class Sip_calc extends AppCompatActivity {

    EditText investAmount, investDuration, investInterest;
    Button calc;
    double futureValue;
    TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sip_calc);
        investAmount=findViewById(R.id.investamt);
        investDuration=findViewById(R.id.years);
        investInterest=findViewById(R.id.annrate);
        calc=findViewById(R.id.button1);
        answer=findViewById(R.id.ans);

        calc.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View arg0) {
                int investment = Integer.parseInt(investAmount.getText().toString());
                int months = (Integer.parseInt(investDuration.getText().toString())) * 12;
                double monthlyRate = Float.parseFloat(investDuration.getText().toString())/12/100;

                futureValue = investment * (Math.pow(1 + monthlyRate, months) - 1) /
                        monthlyRate;


                answer.setText(" "+(int)(futureValue));
            }
        }
        );





    }

}