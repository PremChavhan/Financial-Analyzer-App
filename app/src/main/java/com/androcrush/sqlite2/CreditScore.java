package com.androcrush.sqlite2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CreditScore extends AppCompatActivity {

    public TextView CredSco;
    public RadioButton Paid_30,
            Paid_60,Paid_90,Paid_great90,LatePaid_30,
            LatePaid_60,LatePaid_90,LatePaid_great90,
            AccOpen_30,AccOpen_60,AccOpen_90,AccOpen_great90,
            AccUsed_30,AccUsed_60,AccUsed_90,AccUsed_great90;

    public CheckBox Record_miss_payment,Crecard,StoCard,VehFinanace,PerLoan,Paydayloan,RetailLoan,Mort,App_CreditCard;
    public RadioGroup Late_Payments_group,Recent_Paid_Late_group;
    public EditText Late_Payments_Count,Owned_Money,Money_spent_Month,Limit_CreditCard;
    public Button Cred;

    public int CreditScore=0;
    public boolean flag1=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_score);
        Cred=findViewById(R.id.GetCreditScore);

        CredSco=findViewById(R.id.CredScore);
        //Record Miss payments
        Record_miss_payment= findViewById(R.id.record_miss_payment);
        //Late payments group
        Late_Payments_group = findViewById(R.id.Late_Payments_group);
        Paid_30 = findViewById(R.id.R_paid_30);
        Paid_60 = findViewById(R.id.R_paid_60);
        Paid_90 = findViewById(R.id.R_paid_90);
        Paid_great90 = findViewById(R.id.R_paid_great_90);

        //recent paid late
        Recent_Paid_Late_group = findViewById(R.id.Recent_paid_late);
        LatePaid_30 = findViewById(R.id.R_late_30);
        LatePaid_60 = findViewById(R.id.R_late_60);
        LatePaid_90 = findViewById(R.id.R_late_90);
        LatePaid_great90 = findViewById(R.id.R_late_great_90);

        //no of late payments
        Late_Payments_Count = findViewById(R.id.No_late_payments);

        //How much you owe
        Owned_Money = findViewById(R.id.Money_owe);

        //Money spent in a month
        Limit_CreditCard=findViewById(R.id.Limit_for_month);
        Money_spent_Month = findViewById(R.id.Spent_for_month);

        //How long your account been open
        AccOpen_30 = findViewById(R.id.Acc_open_30);
        AccOpen_60 = findViewById(R.id.Acc_open_60);
        AccOpen_90 = findViewById(R.id.Acc_open_90);
        AccOpen_great90 = findViewById(R.id.Acc_open_great_90);

        //How long it’s been since those accounts were used?
        AccUsed_30 = findViewById(R.id.Acc_used_30);
        AccUsed_60 = findViewById(R.id.Acc_used_60);
        AccUsed_90 = findViewById(R.id.Acc_used_90);
        AccUsed_great90 = findViewById(R.id.Acc_used_great_90);

        //Credits you have
        Crecard = findViewById(R.id.check_CreCard);
        StoCard = findViewById(R.id.check_StoCard);
        VehFinanace = findViewById(R.id.check_VehFinance);
        PerLoan = findViewById(R.id.check_PerLoan);
        Paydayloan = findViewById(R.id.check_PaydayLoan);
        RetailLoan = findViewById(R.id.check_RetailLoan);
        Mort = findViewById(R.id.check_Mort);

        //Have youn applied for credit card;
        App_CreditCard = findViewById(R.id.check_App_CreditCard);

        Record_miss_payment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    Late_Payments_group.setEnabled(true);
                    Recent_Paid_Late_group.setEnabled(true);
                    Late_Payments_Count.setEnabled(true);
                    Owned_Money.setEnabled(true);
                    Paid_30.setEnabled(true);
                    Paid_60.setEnabled(true);
                    Paid_90.setEnabled(true);
                    Paid_great90.setEnabled(true);
                    LatePaid_30.setEnabled(true);
                    LatePaid_60.setEnabled(true);
                    LatePaid_90.setEnabled(true);
                    LatePaid_great90.setEnabled(true);
                    flag1=true;
                } else {
                    Late_Payments_group.setEnabled(false);
                    Recent_Paid_Late_group.setEnabled(false);
                    Late_Payments_Count.setEnabled(false);
                    Owned_Money.setEnabled(false);
                    Paid_30.setEnabled(false);
                    Paid_60.setEnabled(false);
                    Paid_90.setEnabled(false);
                    Paid_great90.setEnabled(false);
                    LatePaid_30.setEnabled(false);
                    LatePaid_60.setEnabled(false);
                    LatePaid_90.setEnabled(false);
                    LatePaid_great90.setEnabled(false);
                    flag1=false;
                }
            }
        });

        Cred.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            public void onClick(View v) {
                int pay;
                if(flag1=false)
                {
                    pay=350;
                }
                else
                    pay=pay_hist();

                CreditScore=pay+CredUtil()+CreditHist()+Credits()+AppCreditCard();
                Toast.makeText(CreditScore.this,"CreditScore is:"+CreditScore,Toast.LENGTH_LONG);
                //CredSco.setText("" +CreditScore);
                Intent intent = new Intent(CreditScore.this, DisplayCredit.class);
                intent.putExtra("cred",CreditScore);
                startActivity(intent);
            }
        });
    }

    public int pay_hist()
    {
        int count =0;

        if(Paid_30.isChecked())
        {
            count=count+80;
        }
        else  if(Paid_60.isChecked())
        {
            count=count+60;
        }
        else  if(Paid_90.isChecked())
        {
            count=count+40;
        }
        else  if(Paid_great90.isChecked())
        {
            count=count+20;
        }

        if(LatePaid_30.isChecked())
        {
            count=count+80;
        }
        else  if(LatePaid_60.isChecked())
        {
            count=count+60;
        }
        else  if(LatePaid_90.isChecked())
        {
            count=count+40;
        }
        else  if(LatePaid_great90.isChecked())
        {
            count=count+20;
        }

        int late_pay=Integer.parseInt(Late_Payments_Count.getText().toString());
        if(late_pay<5)
        {
            count=count+65;
        }
        else  if(late_pay>5 && late_pay<10)
        {
            count=count+50;
        }
        else  if(late_pay>10 && late_pay<25)
        {
            count=count+35;
        }
        else  if(late_pay>25 && late_pay<50)
        {
            count=count+20;
        }

        int owed_mon=Integer.parseInt(Owned_Money.getText().toString());
        if(owed_mon<10000)
        {
            count=count+65;
        }
        else  if(owed_mon>10000 && owed_mon<30000)
        {
            count=count+50;
        }
        else  if(owed_mon>30000 && owed_mon<50000)
        {
            count=count+35;
        }
        else  if(owed_mon>50000)
        {
            count=count+15;
        }

        return count;
    }

    public int CredUtil()
    {
        int Count=0;
        int x=Integer.parseInt(Money_spent_Month.getText().toString());
        int y=Integer.parseInt(Limit_CreditCard.getText().toString());
        if(x<=0.3*y)
        {
            Count=Count+300;
        }
        else if(x<=0.6*y)
        {
            Count=Count+150;
        }
        else if(x<=0.9*y)
        {
            Count=Count+75;
        }
        else if(x>=y)
        {
            Count=Count+37;
        }
        return Count;
    }

    public int CreditHist()
    {
        int count=0;

        if(AccOpen_30.isChecked())
        {
            count=count+30;
        }
        else if(AccOpen_60.isChecked())
        {
            count=count+45;
        }
        else if(AccOpen_90.isChecked())
        {
            count=count+60;
        }
        else if(AccOpen_great90.isChecked())
        {
            count=count+75;
        }

        if(AccUsed_30.isChecked())
        {
            count=count+30;
        }
        else if(AccUsed_60.isChecked())
        {
            count=count+45;
        }
        else if(AccUsed_90.isChecked())
        {
            count=count+60;
        }
        else if(AccUsed_great90.isChecked())
        {
            count=count+75;
        }

        return count;
    }

    public int Credits()
    {
        int count=0;
        if(Crecard.isChecked())
        {
            count=count+15;
        }
        if(StoCard.isChecked())
        {
            count=count+14;
        }
        if(VehFinanace.isChecked())
        {
            count=count+14;
        }
        if(PerLoan.isChecked())
        {
            count=count+15;
        }
        if(Paydayloan.isChecked())
        {
            count=count+14;
        }
        if(RetailLoan.isChecked())
        {
            count=count+14;
        }
        if(Mort.isChecked())
        {
            count=count+14;
        }
        return count;
    }

    public int AppCreditCard()
    {
        int count=0;

        if(App_CreditCard.isChecked())
        {
            count=count+100;
        }
        return count;
    }

}



