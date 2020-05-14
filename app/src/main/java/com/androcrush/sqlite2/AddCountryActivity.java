package com.androcrush.sqlite2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddCountryActivity extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {

    private Button addTodoBtn,select_cat;
    private EditText titleEditText;
    private ProgressDialog mDialog;
    private EditText descEditText;
    private EditText amtEditText;
    private TextView viewcat;
    String item;
    String result;
    //ArrayList<String> arrPackageData;
    catlist catlist;
    private static final Pattern title=
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +
                    "$");
    private static final Pattern pass=
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "$");
    // ArrayList<String> arrPackageData= new ArrayList<String>();

    private DBManager dbManager;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.activity_add_record);


        titleEditText = (EditText) findViewById(R.id.subject_edittext);
        mDialog = new ProgressDialog(this);
        descEditText = (EditText) findViewById(R.id.description_edittext);
        amtEditText = (EditText) findViewById(R.id.amount_edittext);
        viewcat = (TextView) findViewById(R.id.viewcat);
        addTodoBtn = (Button) findViewById(R.id.add_record);
        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);

    }
    int LAUNCH_SECOND_ACTIVITY = 1;

    public void Selectcat(View v)
    {

        Intent i = new Intent(this, Category_list.class);
        //startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                result=data.getStringExtra("result");
                viewcat.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                result="Missing";
            }
        }
    }//




    // attaching data adapter to spinner
    @Override
    public void onClick(View v) {

        if (!validateTitle() | !validateDesc() | !validateAmt() | !validateCat()) {
            return;
        }
           mDialog.setMessage("Adding Record Please Wait...");
           mDialog.setCanceledOnTouchOutside(false);
           mDialog.show();
           final String name = titleEditText.getText().toString();
           final int amt = Integer.parseInt(amtEditText.getText().toString());
           final String desc = descEditText.getText().toString();

           dbManager.insert(name, desc, result, amt);

           Intent main = new Intent(AddCountryActivity.this, CountryListActivity.class)
                   .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            public void run() {
                mDialog.hide();
            }
        },3000);
           startActivity(main);
       }

    private boolean validateTitle() {
        String titleInput = titleEditText.getText().toString().trim();

        if (titleInput.isEmpty()) {
            titleEditText.setError("Field can't be empty");
            return false;
        }
        else {
            titleEditText.setError(null);
            return true;
        }
    }

    private boolean validateAmt() {
        String amtInput = amtEditText.getText().toString().trim();

        if (amtInput.isEmpty()) {
            amtEditText.setError("Field can't be empty");
            return false;
        }
            else {
            amtEditText.setError(null);
            return true;
        }
    }

    private boolean validateDesc() {
        String descInput = descEditText.getText().toString().trim();

        if (descInput.isEmpty()) {
            descEditText.setError("Field can't be empty");
            return false;
        } else {
            descEditText.setError(null);
            return true;
        }
    }

    private boolean validateCat() {
        String catInput = viewcat.getText().toString().trim();
        if (catInput.isEmpty()) {
            viewcat.setHint("Field can't be empty");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    private boolean isEmpty(EditText etText)
    {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}