/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.myaddressbook.database.ContactsBaseHelper;
import com.example.android.myaddressbook.database.ContactsCursorWrapper;
import com.example.android.myaddressbook.database.ContactsDBSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

//class which get/add/remove/update data in a database
public class ContactsList {

    private static ContactsList mContactsList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private ContactsList(Context context){
        mContext = context.getApplicationContext();

        ContactsBaseHelper contactsBaseHelper = new ContactsBaseHelper(mContext);

        if(contactsBaseHelper.doesDatabaseExist(mContext)){
            mDatabase = contactsBaseHelper.getWritableDatabase();
        }else{
            mDatabase = contactsBaseHelper.getWritableDatabase();
            ContactData data_one = new ContactData("Tom Brady", "555-555-1212",
                    "TB12@Patriots.com", "1 Patriot Place", "Foxboro", "Mass.", "01234");

            ContactData data_two = new ContactData("David Ortiz", "617-555-1212", "DOrtiz@RedSox.com",
                    "1 Landsdown Street", "Boston", "Mass.", "01235");

            ContactData data_three = new ContactData("Patrice Bergeron", "978-555-1212", "PBergeron@BostonBruins.com",
                    "1 Causeway Street", "Boston", "Mass.", "01236");

            addNewContact(data_one);
            addNewContact(data_two);
            addNewContact(data_three);
        }
    }



    public static ContactsList get(Context context){
        if(mContactsList == null){
            mContactsList = new ContactsList(context);
        }

        return mContactsList;
    }

    public List<ContactData> getContactDataList(){

        List<ContactData> list = new ArrayList<>();

        ContactsCursorWrapper cursor = queryContactsData(null, null);

        try{
            //start from first data
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){

                //add each contact data to a list
                list.add(cursor.getContactData());
                cursor.moveToNext();
            }

        }finally{
            cursor.close();
        }

        //implement compareTo interface
        Collections.sort(list);

        return list;
    }


    public ContactData getContactByID(int id){
        ContactData data = null;

        ContactsCursorWrapper cursor = queryContactsData(
                ContactsDBSchema.ContactsTable.Columns.CONTACT_ID +
                            " = ?", new String[]{String.valueOf(id)});

        try{

            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();

            data = cursor.getContactData();
            System.out.println("GET CONTACT BY ID "+data.toString());
        }finally{
            cursor.close();
        }

       return data;
    }


    public void addNewContact(ContactData data){

        //if contact is not empty
        if(!data.getContactName().equals("")) {

            ContentValues values = getContentValues(data);

            mDatabase.insert(ContactsDBSchema.ContactsTable.CONTACTS_TABLE_NAME, null, values);
        }
    }


    public void updateContactData(ContactData data){

        if(!data.getContactName().equals("")) {

            String id = String.valueOf(data.getContactID());

            ContentValues values = getContentValues(data);

            mDatabase.update(ContactsDBSchema.ContactsTable.CONTACTS_TABLE_NAME,
                    values,
                    ContactsDBSchema.ContactsTable.Columns.CONTACT_ID + " = ?",
                    new String[]{id});
        }
    }


    public void deleteContactData(ContactData data){

        String id = String.valueOf(data.getContactID());

        mDatabase.delete(ContactsDBSchema.ContactsTable.CONTACTS_TABLE_NAME,
                ContactsDBSchema.ContactsTable.Columns.CONTACT_ID + " = ?",
                new String[]{id});
    }


    //read data from a database
    private ContactsCursorWrapper queryContactsData(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(ContactsDBSchema.ContactsTable.CONTACTS_TABLE_NAME,
                                        null,
                                        whereClause,
                                        whereArgs,
                                        null,
                                        null,
                                        null);

        return new ContactsCursorWrapper(cursor);
    }

    //method that put ContentData value into ContentValue object
    //which represents a map object (key-value pair)
    private static ContentValues getContentValues(ContactData data){
        ContentValues values = new ContentValues();

        values.put(ContactsDBSchema.ContactsTable.Columns.CONTACT_NAME, data.getContactName());
        values.put(ContactsDBSchema.ContactsTable.Columns.PHONE, data.getContactPhone());
        values.put(ContactsDBSchema.ContactsTable.Columns.EMAIL, data.getContactEmail());
        values.put(ContactsDBSchema.ContactsTable.Columns.STREET, data.getContactStreet());
        values.put(ContactsDBSchema.ContactsTable.Columns.CITY, data.getContactCity());
        values.put(ContactsDBSchema.ContactsTable.Columns.STATE, data.getContactState());
        values.put(ContactsDBSchema.ContactsTable.Columns.ZIP, data.getContactZip());

        return values;
    }

}
