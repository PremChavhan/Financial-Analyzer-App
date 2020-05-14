package com.androcrush.sqlite2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;


public class CountryListActivity<user> extends AppCompatActivity {

    GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";
    private static final String ARG_NAME = "username";
    static String user;
    Cursor cursor;
    PieChart pieChart;
    PieData data;
    private static final int MY_PREMISSION_REQUEST_RECEIVE_SMS=0;
    private static final int MY_PREMISSION_REQUEST_READ_SMS=1;
    private static final int MY_PREMISSION_REQUEST_INTERNET=2;
    private static final int MY_PREMISSION_REQUEST_READ_EXTERNAL_STORAGE=3;
    private static final int MY_PREMISSION_REQUEST_WRITE_EXTERNAL_STORAGE=4;
    PieDataSet dataSet;



    public static void startActivity(Context context, String username) {
        Intent intent = new Intent(context, CountryListActivity.class);
        intent.putExtra(ARG_NAME, username);
        user=username;
        context.startActivity(intent);
    }
    //String username=getIntent().getStringExtra("username");
    private DBManager dbManager;
    private DatabaseHelper dbhelper;
    private ListView listView;
    public static final int[] COLORS = {
            Color.rgb(52, 104, 168), Color.rgb(46, 92, 149), Color.rgb(40, 80, 129),
            Color.rgb(34, 68, 110), Color.rgb(28, 56, 91), Color.rgb(22, 44, 71), Color.rgb(16, 32, 52)
    };
    static final String DB_NAME = "EXPENSES.DB";

    // database version
    static final int DB_VERSION = 1;
    SQLiteDatabase db;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[]{DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DESC, DatabaseHelper.AMT};

    final int[] to = new int[]{R.id.id, R.id.title, R.id.desc, R.id.amnt};;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_emp_list);
        firebaseAuth = FirebaseAuth.getInstance();

        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);
        firebaseAuth = FirebaseAuth.getInstance();
        dbManager=new DBManager(this);
        dbhelper=new DatabaseHelper(this);
        db=dbhelper.getReadableDatabase();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS))
            {

            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},MY_PREMISSION_REQUEST_RECEIVE_SMS);
            }
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_SMS))
            {

            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},MY_PREMISSION_REQUEST_READ_SMS);
            }
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET))
            {

            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},MY_PREMISSION_REQUEST_INTERNET);
            }
        }
         if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {

            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PREMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {

            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PREMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        /*ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(13f,0));
        yvalues.add(new Entry(13f,1));
        yvalues.add(new Entry(13f,2));
        yvalues.add(new Entry(13f,3));
        yvalues.add(new Entry(13f,4));
        yvalues.add(new Entry(13f,5));
        yvalues.add(new Entry(13f,6));
        yvalues.add(new Entry(13f,7));
        yvalues.add(new Entry(13f,8));
       // for(int i=0;i<db.execSQL("SELECT COUNT(*) FROM EXPENSES");i++)
        //{



        PieDataSet dataSet = new PieDataSet(yvalues, "Expenses");

        ArrayList<String> xVals = new ArrayList<String>();

       xVals.add("January");
        xVals.add("February");
        xVals.add("March");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");*/
    try {
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < queryYData().size(); i++)
            yVals.add(new Entry(queryYData().get(i), i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < queryXData().size(); i++)
            xVals.add(queryXData().get(i));
        dataSet = new PieDataSet(yVals, "Expenses");
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        dataSet.setColors(COLORS);
        pieChart.setDescription(" ");
        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setHoleRadius(35f);
        pieChart.animateXY(1400, 1400);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
    }
    catch(Exception e)
    {
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(13f,0));
        yvalues.add(new Entry(13f,1));
        yvalues.add(new Entry(13f,2));
        yvalues.add(new Entry(13f,3));
        yvalues.add(new Entry(13f,4));
        yvalues.add(new Entry(13f,5));
        yvalues.add(new Entry(13f,6));
        yvalues.add(new Entry(13f,7));
        yvalues.add(new Entry(13f,8));

        PieDataSet dataSet = new PieDataSet(yvalues, "Expenses");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("January");
        xVals.add("February");
        xVals.add("March");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");

        data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        dataSet.setColors(COLORS);
        pieChart.setDescription(" ");
        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setHoleRadius(35f);
        pieChart.animateXY(1400, 1400);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);

    }
       /* Smsreceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                //From the received text string you may do string operations to get the required OTP
                //It depends on your SMS format
                Log.e("Message", messageText);
                Toast.makeText(CountryListActivity.this, "Message: " + messageText, Toast.LENGTH_LONG).show();
                Toast.makeText(CountryListActivity.this, "Message Read Successfully", Toast.LENGTH_LONG).show();
            }
        });*/


        dbManager = new DBManager(this);
        dbManager.open();
        final Cursor[] cursor = {dbManager.fetch()};

        Animation anim = AnimationUtils.loadAnimation(
                this, android.R.anim.slide_in_left
        );
        anim.setDuration(1000);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.startAnimation(anim);
        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor[0], from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idTextView = (TextView) findViewById(R.id.id);
                TextView titleTextView = (TextView) findViewById(R.id.title);
                TextView descTextView = (TextView) findViewById(R.id.desc);
                TextView amtTextView = (TextView) findViewById(R.id.amnt);

               cursor[0] = (Cursor) listView.getItemAtPosition(position);
//                cursor.getString(cursor.getColumnIndexOrThrow("");

               /* String nid = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();
                int amt = Integer.parseInt(amtTextView.getText().toString());
                if (amt < 0) {
                    amt = 1;
                }*/

               String nid= cursor[0].getString(cursor[0].getColumnIndexOrThrow("_id"));
               String title= cursor[0].getString(cursor[0].getColumnIndexOrThrow("subject"));
               String desc= cursor[0].getString(cursor[0].getColumnIndexOrThrow("description"));
               String cat= cursor[0].getString(cursor[0].getColumnIndexOrThrow("category"));
               int amt= cursor[0].getInt(cursor[0].getColumnIndexOrThrow("amount"));
                Intent modify_intent = new Intent(getApplicationContext(), ModifyCountryActivity.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("desc", desc);
                modify_intent.putExtra("id", nid);
                modify_intent.putExtra("amt", amt);
                modify_intent.putExtra("cat", cat);
                startActivity(modify_intent);
                return true;
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case MY_PREMISSION_REQUEST_RECEIVE_SMS:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permission Granted For Recive SMS", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied For Recive SMS", Toast.LENGTH_SHORT).show();
                }
            }
            case MY_PREMISSION_REQUEST_READ_SMS:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permission Granted For Reading SMS", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied For Reading SMS", Toast.LENGTH_SHORT).show();
                }
            }

            case MY_PREMISSION_REQUEST_INTERNET:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permission Granted For Internet", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied For Internet", Toast.LENGTH_SHORT).show();
                }
            }

            case MY_PREMISSION_REQUEST_READ_EXTERNAL_STORAGE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permission Granted For Reading External Storage", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied For Reading External Storage", Toast.LENGTH_SHORT).show();
                }
            }
            case MY_PREMISSION_REQUEST_WRITE_EXTERNAL_STORAGE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permission Granted For Writing External Storage", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied For Writing External Storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /*  public ArrayList<String> getCategories() {
            ArrayList<String> rv = new ArrayList<>();
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            Cursor csr = db.query(dbhelper.DB_NAME,null,null,null,null,null,null);
            while (csr.moveToNext()) {
                String h = new String(
                        csr.getString(csr.getColumnIndex("category"))
                );
                rv.add(h);
            }
            csr.close();
            return rv;
        }*/
  public ArrayList<String> queryXData(){
      ArrayList<String> xNewData = new ArrayList<String>();
      String query="SELECT "+ dbhelper.CAT+" FROM "+dbhelper.TABLE_NAME;
      Cursor cursor = db.rawQuery(query,null);
      for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
          xNewData.add(cursor.getString(cursor.getColumnIndex(dbhelper.CAT)));
      }
      cursor.close();
      return xNewData;
  }

    public ArrayList<Integer> queryYData(){
        ArrayList<Integer> yNewData= new ArrayList<Integer>();
        String query="SELECT "+ dbhelper.AMT +" FROM "+dbhelper.TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            yNewData.add(cursor.getInt(cursor.getColumnIndex(dbhelper.AMT)));
        }
        cursor.close();
        return yNewData;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {

            Intent add_mem = new Intent(this, AddCountryActivity.class);
            startActivity(add_mem);

        }

        if (id == R.id.emi) {

            Intent add_mem = new Intent(this, emi_calc.class);
            startActivity(add_mem);

        }
        if (id == R.id.sip) {

            Intent add_mem = new Intent(this, Sip_calc.class);
            startActivity(add_mem);

        }
        if (id == R.id.credit) {

            Intent add_mem = new Intent(this, CreditScore.class);
            startActivity(add_mem);

        }
        if (id == R.id.createbackup) {
            Intent add_mem = new Intent(this, Createbackup.class);
            startActivity(add_mem);
        }
           /* if (user!= null) {
                Intent add_mem = new Intent(this, Createbackup.class);
                add_mem.putExtra("username", user);
                startActivity(add_mem);
            }
            else{
                Toast.makeText(this, "Cannot proceed username is null", Toast.LENGTH_LONG).show();
            }
        }*/

        if (id == R.id.refresh) {
           /* Intent add_mem = new Intent(this, CountryListActivity.class);
            startActivity(add_mem);*/
           /* listView.clear();
            listView.addAll(dbhelper.getAllCotacts());
            arrayAdapter.notifyDataSetChanged();
            listView.invalidateViews();
//            adapter.notifyDataSetChanged();*/
           /* Animation anim = AnimationUtils.loadAnimation(
                    this, android.R.anim.slide_in_left
            );
            anim.setDuration(1500);
            listView.startAnimation(anim);

            new Handler().postDelayed(new Runnable() {

                public void run() {

                    listView.invalidate();
                    listView.refreshDrawableState();
                    cursor= dbManager.fetch();
                    adapter.swapCursor(cursor);
                    adapter.notifyDataSetChanged();
                    dataSet.setDrawValues(false);


                }

            }, anim.getDuration());*/
           /* pieChart.clearValues();
            pieChart.setData(data);
            pieChart.notifyDataSetChanged();
            pieChart.invalidate();
*/
            Intent intent = getIntent();
            finish();
            startActivity(intent);

        }

        if (id == R.id.restorebackup) {

            Intent add_mem = new Intent(this, Restorebackup.class);
//            add_mem.putExtra("username", user);
            startActivity(add_mem);
          /*  if (user != null) {
                Intent add_mem = new Intent(this, Restorebackup.class);
                add_mem.putExtra("username", user);
                startActivity(add_mem);
            }
            else{
                Toast.makeText(this, "Cannot proceed username is null", Toast.LENGTH_LONG).show();
            }
*/
        }
        if (id == R.id.logout) {

            // Firebase sign out
            firebaseAuth.signOut();
            // Google sign out
            googleSignInClient.signOut().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Google Sign In failed, update UI appropriately
                            Log.w(TAG, "Signed out of google");
                            FirebaseAuth.getInstance().signOut();
                            Intent add_mem = new Intent(CountryListActivity.this, Signin.class);
                            startActivity(add_mem);
                            finish();
                        }

                       /* @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Google Sign In failed, update UI appropriately
                            Log.w(TAG, "Signed out of google");
                        }*/
                    });
        }
        return super.onOptionsItemSelected(item);
    }

}