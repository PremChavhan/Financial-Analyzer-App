package com.androcrush.sqlite2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Smsreceiver extends BroadcastReceiver {

    //interface
    private static SmsListener mListener;
    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG="SmsBroadcastReceiver";
    String msg,PhoneNO="";
    private DBManager dbManager;
    Pattern regEx =
            Pattern.compile("[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}");
    Matcher m;
    private int intIndex=0;
    double debitedAmount=0.0;
    int i;

    public Smsreceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            dbManager = new DBManager(context);
            dbManager.open();
            Log.i(TAG, "Intent Received" + intent.getAction());
            if (intent.getAction() == SMS_RECEIVED) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] mypdu = (Object[]) bundle.get("pdus");
                    final SmsMessage[] message = new SmsMessage[mypdu.length];

                    for (int i = 0; i < mypdu.length; i++) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String format = bundle.getString("format");

                            message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], format);
                             /*m = regEx.matcher(message[i].getDisplayOriginatingAddress());
                            if (m.find()) {
                                try {*/
                            //  String phoneNumber = m.group(0);
                            PhoneNO = message[i].getOriginatingAddress();
                            msg = message[i].getMessageBody();
                            intIndex = msg.indexOf("debited");
                            if (intIndex == -1) {

                            } else {String[] contents = msg.toLowerCase().split("debit")[1].split(" ");

                                for (i = 0; i < contents.length; i++) {
                                    try {
                                        debitedAmount = Double.parseDouble(contents[i]);
                                        break;
                                    } catch (Exception e) {

                                    }
                                }
                                Toast.makeText(context, "amount:" + debitedAmount, Toast.LENGTH_LONG).show();
                                dbManager.insert("others", "others", "others", (int)debitedAmount);
                                Toast.makeText(context, "Data added Successfully", Toast.LENGTH_LONG).show();
                            }
                                /*} catch (Exception e) {
                                    e.printStackTrace();
                                }*/
                        } else {
                            message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                            /* m = regEx.matcher(message[i].getDisplayOriginatingAddress());
                            if (m.find()) {
                                try {*/
                            //  String phoneNumber = m.group(0);
                            PhoneNO = message[i].getOriginatingAddress();
                            msg = message[i].getMessageBody();
                            intIndex = msg.indexOf("debited");
                            if (intIndex == -1) {

                            } else {
                                String[] contents = msg.toLowerCase().split("debit")[1].split(" ");

                                for (i = 0; i < contents.length; i++) {
                                    try {
                                        debitedAmount = Double.parseDouble(contents[i]);
                                        break;
                                    } catch (Exception e) {

                                    }
                                }
                            }
                            Toast.makeText(context, "amount:" + debitedAmount, Toast.LENGTH_LONG).show();
                            dbManager.insert("others", "others", "others", (int)debitedAmount);
                            Toast.makeText(context, "Data added Successfully", Toast.LENGTH_LONG).show();
                            /*Intent data=new Intent(context,CreateMsgRecord.class);
                            intent.putExtra("amount",(int)debitedAmount);
                            context.startActivity(intent);*/
                        }
                    }


                }




                       /* CharSequence intentData = intent.getCharSequenceExtra("message");
                        Toast.makeText(context, " message: "+intentData, Toast.LENGTH_LONG).show();*/

//                Toast.makeText(context, "Message:" + msg + "\n Number:" + PhoneNO, Toast.LENGTH_LONG).show();


       /* Bundle bundle  = intent.getExtras();

        try {
           *//* if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                        Log.e("Current Message", format + " : " + currentMessage.getDisplayOriginatingAddress());
                    } else {
                        currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    }
                    Pattern regEx =
                            Pattern.compile("[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}");
                    Matcher m = regEx.matcher(currentMessage.getDisplayOriginatingAddress());
                    if (m.find()) {
                        try {
                            String phoneNumber = m.group(0);
                            Long date = currentMessage.getTimestampMillis();
                            String message = currentMessage.getDisplayMessageBody();
                            Log.e("SmsReceiver Mine", "senderNum: " + phoneNumber + "; message: " + message);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("Mismatch", "Mismatch value");
                    }
                }*//*
            CharSequence intentData = intent.getCharSequenceExtra("message");
            Toast.makeText(context, " message: "+intentData, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }*/



            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }

    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
}
}
