package com.example.android.myaddressbook.database;

//class which defines table names and columns
public class ContactsDBSchema {

    public static final class ContactsTable{
        public static final String CONTACTS_TABLE_NAME = "contacts";

        public static final class Columns{
            public static final String CONTACT_ID = "id";
            public static final String CONTACT_NAME = "name";
            public static final String PHONE = "phone";
            public static final String EMAIL = "email";
            public static final String STREET = "street";
            public static final String CITY = "city";
            public static final String STATE = "state";
            public static final String ZIP = "zip";
        }
    }
}
