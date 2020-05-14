package com.androcrush.sqlite2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ajts.androidmads.library.ExcelToSQLite;
import com.github.mikephil.charting.utils.Utils;

import java.io.File;

public class Restorebackup extends AppCompatActivity {
    DatabaseHelper dbhelper= new DatabaseHelper(this);
    DBManager dbmanager;
    /*String username=getIntent().getStringExtra("username");*/
    private ProgressDialog mDialog;
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/Records.xls";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_backup);
        mDialog = new ProgressDialog(this);
        File file = new File(directory_path);
        if (!file.exists()) {
//            Utils.showSnackBar(view, "No file");
            Toast.makeText(this, "No File", Toast.LENGTH_LONG).show();
            return;
        }
        dbmanager=new DBManager(this);
        dbmanager.open();
        // Is used to import data from excel without dropping table
        // ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), DBHelper.DB_NAME);

        // if you want to add column in excel and import into DB, you must drop the table
        ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), dbhelper.DB_NAME, false);
        // Import EXCEL FILE to SQLite
        excelToSQLite.importFromFile(directory_path, new ExcelToSQLite.ImportListener() {
            @Override
            public void onStart() {
                mDialog.setMessage("Restoring backup Please Wait...");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
            }

            @Override
            public void onCompleted(String dbName) {
               /* Utils.showSnackBar(view, "Excel imported into " + dbName);*/
                Handler handler=new Handler();
                handler.postDelayed(new Runnable(){
                    public void run() {
                        mDialog.hide();
                    }
                },3000);
                Intent i=new Intent(Restorebackup.this,CountryListActivity.class);
                Toast.makeText(Restorebackup.this, "Excel imported into " + dbName, Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }

            @Override
            public void onError(Exception e) {
               /* Utils.showSnackBar(view, "Error : " + e.getMessage());*/
                Handler handler=new Handler();
                handler.postDelayed(new Runnable(){
                    public void run() {
                        mDialog.hide();
                    }
                },3000);
                Intent i=new Intent(Restorebackup.this,CountryListActivity.class);
                Toast.makeText(Restorebackup.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }
        });
        dbmanager.close();
    }
}

