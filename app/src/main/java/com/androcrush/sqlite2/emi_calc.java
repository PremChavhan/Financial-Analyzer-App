package com.androcrush.sqlite2;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class emi_calc extends Activity {

    Spinner sp1;
    Button button1, bthlp;
    Float f1, f2, f3, f4, f5, f6;
    EditText etLoanAmount, etInterest, etAns, e1;
    TextView tvans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_calc);
        sp1 = (Spinner) findViewById(R.id.sp1);
        button1 = (Button) findViewById(R.id.button1);
        etLoanAmount = (EditText) findViewById(R.id.etLoanAmount);
        etInterest = (EditText) findViewById(R.id.etInterest);
        e1 = (EditText) findViewById(R.id.e1);

        tvans = (TextView) findViewById(R.id.etAns);

        List<String> list = new ArrayList<String>();
        list.add("Select a type");
        list.add("Monthly time Period");
        list.add("Installment price");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spineritem, list);
        sp1.setAdapter(dataAdapter);

        sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long id) {
                // TODO Auto-generated method stub

                String s = parent.getItemAtPosition(pos).toString();

                if (s.equals("Monthly time Period")) {
                    button1.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub


                            String a = etLoanAmount.getText().toString();
                            String m = etInterest.getText().toString();
                            String d = e1.getText().toString();

                            if (a.length() <= 0 || m.length() <= 0 || d.length() <= 0) {
                                Toast.makeText(getApplicationContext(), "Empty Text Field Enter Data ", Toast.LENGTH_LONG).show();
                            } else {
                                f1 = Float.parseFloat(etLoanAmount.getText()
                                        .toString());

                                try {

                                    f2 = Float.parseFloat(etInterest.getText()
                                            .toString());
                                } catch (NumberFormatException e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }

                                f5 = Float.parseFloat(e1.getText().toString());

                                f3 = (f1 * f2 / 100);
                                f4 = f3 + f1;
                                f6 = f4 / f5;
                                String s2 = String.valueOf(f6);
                                tvans.setText(s2 + " Rs Installment");


                            }
                        }
                    });
                }
                if (s.equals("Installment price")) {
                    button1.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub


                            String a = etLoanAmount.getText().toString();
                            String m = etInterest.getText().toString();
                            String d = e1.getText().toString();

                            if (a.length() <= 0 || m.length() <= 0 || d.length() <= 0) {
                                Toast.makeText(getApplicationContext(), "Empty Text Field Enter Data ", Toast.LENGTH_LONG).show();
                            } else {
                                f1 = Float.parseFloat(etLoanAmount.getText()
                                        .toString());
                                try {
                                    f2 = Float.valueOf((etInterest.getText()
                                            .toString()));

                                } catch (NumberFormatException e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }

                                f5 = Float.parseFloat(e1.getText().toString());

                                f3 = (f1 * f2 / 100);
                                f4 = f3 + f1;
                                int f6 = (int) (f4 / f5);
                                int f7 = (int) (f4 % f5);
                                String s2 = String.valueOf(f6);
                                tvans.setText(s2 + "    Month      " + f7
                                        + " Rs " + "Remaing Money");

                            }
                        }
                    });
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }



        });
    }
}

