package com.example.android.myaddressbook.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.android.myaddressbook.ContactData;


//class that holds method to read data from a database

public class ContactsCursorWrapper extends CursorWrapper{

    public ContactsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ContactData getContactData(){

        int contactID = getInt(getColumnIndex(ContactsDBSchema.ContactsTable.Columns.CONTACT_ID));

        String contactName = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Columns.CONTACT_NAME));

        String contactPhone = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Columns.PHONE));

        String contactEmail = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Columns.EMAIL));

        String contactStreet = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Columns.STREET));

        String contactCity = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Columns.CITY));

        String contactState = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Columns.STATE));

        String contactZip = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Columns.ZIP));

        ContactData data = new ContactData(contactID, contactName, contactPhone, contactEmail, contactStreet,
                contactCity, contactState, contactZip);

        return data;
    }
}
