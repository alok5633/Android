package com.example.phonebook;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.clickListener {
   private static final int REQUEST_CALL=1;
   String call;
    List<ContactModel> data;
    List<ContactModel>data1=new ArrayList<>();
    ImageView imageView;
    DatabaseHelper db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        imageView=findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
        db=new DatabaseHelper(MainActivity.this);
        data1=db.getAllContacts();
        //setData();
        ContactAdapter contactAdapter=new ContactAdapter(data1,this,this);
        RecyclerView recyclerView=findViewById(R.id.rv_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(contactAdapter);



    }



    @Override
    public void onUpdateClick(final ContactModel model) {

        View view= LayoutInflater.from(this).inflate(R.layout.dialog_update,null);
        final EditText name=view.findViewById(R.id.edt_name);
        final EditText no=view.findViewById(R.id.edt_no);
        name.setText(model.getContact_name());
        no.setText(model.getContact_no());

        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("Update Contact")
                .setView(view)
                .setMessage("please fill the new contact or new no")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();


        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactModel contactModel=new ContactModel(R.drawable.photo,name.getText().toString(),no.getText().toString(),R.drawable.update,R.drawable.delete);
                contactModel.setId(model.getId());
                db.updateContact(contactModel);
            }
        });
    }



    @Override
    public void onDeleteClick(final ContactModel model) {

        View view= LayoutInflater.from(this).inflate(R.layout.dialog_delete,null);
        final TextView name=view.findViewById(R.id.tv_name);
        final TextView no=view.findViewById(R.id.tv_no);
        name.setText(model.getContact_name());
        no.setText(model.getContact_no());

        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setView(view)
                .setMessage("Are you Sure you want to delete this contact")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();


        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactModel contactModel=new ContactModel(R.drawable.photo,name.getText().toString(),no.getText().toString(),R.drawable.update,R.drawable.delete);
                contactModel.setId(model.getId());
                db.delContact(contactModel);
            }
        });

    }

    @Override
    public void onNameClick(ContactModel model) {

        call=model.getContact_no();
        makePhoneCall(call);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                makePhoneCall(call);
            }
            else{
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makePhoneCall(String call)
    {
        if(call.trim().length()>0)
        {  if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else{
           String dial="tel:"+call;
           startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));}
        }
        else
        {
            Toast.makeText(MainActivity.this,"NO number",Toast.LENGTH_SHORT).show();
        }
    }


}
