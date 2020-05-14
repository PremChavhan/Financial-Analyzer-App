package com.androcrush.sqlite2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyCountryActivity extends Activity implements OnClickListener,AdapterView.OnItemSelectedListener{

    private EditText titleText,amtText;
    private Button updateBtn, deleteBtn,selectcat;
    private EditText descText;
    TextView viewcat;
    String item,result;
    ProgressDialog mDialog;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_record);
        mDialog = new ProgressDialog(this);
        dbManager = new DBManager(this);
        dbManager.open();

        titleText = (EditText) findViewById(R.id.subject_edittext);
        amtText = (EditText) findViewById(R.id.amount_edittext);
        descText = (EditText) findViewById(R.id.description_edittext);
       viewcat=findViewById(R.id.viewcat);
       selectcat=findViewById(R.id.cat_button);
        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

       /* spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catlist.categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);*/

        Intent intent = getIntent();

        String name = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String id = intent.getStringExtra("id");
        String cat= intent.getStringExtra("cat");
        int amt = intent.getIntExtra("amt",1);


        _id = Long.parseLong(id);

        titleText.setText(name);
        amtText.setText(Integer.toString(amt));
        descText.setText(desc);
        viewcat.setText(cat);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
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





    private boolean validateTitle() {
        String titleInput = titleText.getText().toString().trim();

        if (titleInput.isEmpty()) {
            titleText.setError("Field can't be empty");
            return false;
        }
        else {
            titleText.setError(null);
            return true;
        }
    }

    private boolean validateAmt() {
        String amtInput = amtText.getText().toString().trim();

        if (amtInput.isEmpty()) {
            amtText.setError("Field can't be empty");
            return false;
        }
        else {
            amtText.setError(null);
            return true;
        }
    }

    private boolean validateDesc() {
        String descInput = descText.getText().toString().trim();

        if (descInput.isEmpty()) {
            descText.setError("Field can't be empty");
            return false;
        } else {
            descText.setError(null);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                if (!validateTitle() | !validateDesc() | !validateAmt() | !validateCat()) {
                    return;
                }
                mDialog.setMessage("Updating data Please Wait...");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
                String title = titleText.getText().toString();
                int amount = Integer.parseInt(amtText.getText().toString());
                String desc = descText.getText().toString();
                String cat=viewcat.getText().toString();

                dbManager.update(_id,title,desc,cat,amount);
                Toast.makeText(ModifyCountryActivity.this, "Updation done Successfully", Toast.LENGTH_LONG).show();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable(){
                    public void run() {
                        mDialog.hide();
                    }
                },3000);
                this.returnHome();
                break;

            case R.id.btn_delete:
                mDialog.setMessage("Updating data Please Wait...");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
                dbManager.delete(_id);
                Toast.makeText(ModifyCountryActivity.this, "Deletion done Successfully", Toast.LENGTH_LONG).show();
                Handler handler1=new Handler();
                handler1.postDelayed(new Runnable(){
                    public void run() {
                        mDialog.hide();
                    }
                },3000);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), CountryListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
