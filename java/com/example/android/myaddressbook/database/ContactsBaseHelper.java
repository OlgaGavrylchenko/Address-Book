package com.example.android.myaddressbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class ContactsBaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "MyAddressBook.db";

    public ContactsBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + ContactsDBSchema.ContactsTable.CONTACTS_TABLE_NAME + "(" +
                ContactsDBSchema.ContactsTable.Columns.CONTACT_ID + " integer primary key autoincrement, " +
                ContactsDBSchema.ContactsTable.Columns.CONTACT_NAME + " text not null" + ", " +
                ContactsDBSchema.ContactsTable.Columns.PHONE + " text not null" +  ", " +
                ContactsDBSchema.ContactsTable.Columns.EMAIL + " text not null" +  ", " +
                ContactsDBSchema.ContactsTable.Columns.STREET + " text not null" +  ", " +
                ContactsDBSchema.ContactsTable.Columns.CITY + " text not null" +  ", " +
                ContactsDBSchema.ContactsTable.Columns.STATE + " text not null" +  ", " +
                ContactsDBSchema.ContactsTable.Columns.ZIP + " text not null" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing to do
    }

    public boolean doesDatabaseExist(Context context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }
}
