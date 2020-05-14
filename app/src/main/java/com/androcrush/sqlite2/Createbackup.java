package com.androcrush.sqlite2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.github.mikephil.charting.utils.Utils;

import java.io.File;

public class Createbackup extends AppCompatActivity {
    DatabaseHelper dbhelper;
    DBManager dbmanager;
    private ProgressDialog mDialog;


    /*String username=getIntent().getStringExtra("username");
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_backup);
        mDialog = new ProgressDialog(this);
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // Export SQLite DB as EXCEL FILE
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DatabaseHelper.DB_NAME, directory_path);
        sqliteToExcel.exportSingleTable("COUNTRIES", "Records.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
                //Toast.makeText(Createbackup.this, "Started backup process", Toast.LENGTH_SHORT).show();
                mDialog.setMessage("Started backup Process Please Wait...");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
            }
            @Override
            public void onCompleted(String filePath) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable(){
                    public void run() {
                        mDialog.hide();
                    }
                },3000);
                Intent i=new Intent(Createbackup.this,CountryListActivity.class);
                Toast.makeText(Createbackup.this, "Backup Completed Successfully,You can check in 'Backup' folder", Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }
            @Override
            public void onError(Exception e) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable(){
                    public void run() {
                        mDialog.hide();
                    }
                },3000);
                Intent i=new Intent(Createbackup.this,CountryListActivity.class);
                Toast.makeText(Createbackup.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }
        });
    }
}


