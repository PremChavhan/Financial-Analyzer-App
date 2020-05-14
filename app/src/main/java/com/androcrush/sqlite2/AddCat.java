package com.androcrush.sqlite2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class AddCat extends Activity implements View.OnClickListener {

    private EditText catedit1;
    String item;
    Button addTodoBtn1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_catlist);
        catlist.categories.add("Automobile");
        catlist.categories.add("Business Services");
        catlist.categories.add("Computers");
        catlist.categories.add("Education");
        catlist.categories.add("Personal");
        catlist.categories.add("Travel");
         addTodoBtn1=(Button)findViewById(R.id.add_cat1);
        catedit1=findViewById(R.id.cat1_edittext);
        addTodoBtn1.setOnClickListener(this);
    }
    @SuppressLint("ShowToast")
    @Override
    public void onClick(View v) {

        item=catedit1.getText().toString();
        catlist.setCategories(item);
        makeText(this,"Category added Successfully",Toast.LENGTH_SHORT);
                Intent i=new Intent(getApplicationContext(),CountryListActivity.class);
                startActivity(i);
        catedit1.setText(" ");
    }
}
