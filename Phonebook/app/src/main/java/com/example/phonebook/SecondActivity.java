package com.example.phonebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    List<ContactModel>data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final EditText name=findViewById(R.id.edt_name);
        final EditText no=findViewById(R.id.edt_no);
        Button submit=findViewById(R.id.btn_new);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data=new ArrayList<>();
                final String str1=name.getText().toString();
                final String str2=no.getText().toString();
                final DatabaseHelper databaseHelper=new DatabaseHelper(SecondActivity.this);
                long id=databaseHelper.insertContact(str1,str2);
                if(id>0)
                    Toast.makeText(SecondActivity.this,"Inserted"+id,Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SecondActivity.this, "Not Inserted", Toast.LENGTH_SHORT).show();



            }
        });



    }
}

