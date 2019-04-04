package com.example.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Telephony.Mms.Part.TEXT;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private final static String DATABASE_NAME="ContactDb";
    private final static int DATABASE_VERSION=1;
    private final static String CONTACT_TABLE="contact";
    private final String CONTACT_NAME="contact_name";
    private final String Contact_NO="contact_no";
    private final static String CONTACT_ID="contact_id";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+CONTACT_TABLE+" ( "+CONTACT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+CONTACT_NAME+" TEXT , "+Contact_NO+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+CONTACT_TABLE);
        onCreate(db);

    }

    public long insertContact(String name,String no)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CONTACT_NAME,name);
        contentValues.put(Contact_NO,no);

        long last_id=db.insert(CONTACT_TABLE,null,contentValues);
        db.close();
        return(last_id);
    }

    public List<ContactModel> getAllContacts(){
        List<ContactModel>contactModelList=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+CONTACT_TABLE,null);

        if(cursor.moveToFirst()){
            do {
                ContactModel contactModel = new ContactModel();
                contactModel.setLogo(R.drawable.photo);
                contactModel.setContact_name(cursor.getString(cursor.getColumnIndex(CONTACT_NAME)));
                contactModel.setContact_no(cursor.getString(cursor.getColumnIndex(Contact_NO)));
                contactModel.setUpdate(R.drawable.update);
                contactModel.setUpdate(R.drawable.delete);
                contactModel.setId(cursor.getInt(cursor.getColumnIndex(CONTACT_ID)));
                contactModelList.add(contactModel);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactModelList;
    }

    public int updateContact(ContactModel contactModel){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_NAME,contactModel.getContact_name());
        contentValues.put(Contact_NO,contactModel.getContact_no());
        int rows = db.update(CONTACT_TABLE,contentValues,CONTACT_ID +"= '"+contactModel.getId()+"'",null);

        return rows;

    }

    public  int delContact(ContactModel contactModel){

        SQLiteDatabase db = this.getWritableDatabase();

        int rows = db.delete(CONTACT_TABLE,CONTACT_ID +"= '"+contactModel.getId()+"'",null);


        return  rows;
    }


}

