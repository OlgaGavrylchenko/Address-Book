/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewContactActivity extends AppCompatActivity {

    public static final String EXTRA_NEW_CONTACT = "new_contact";
    private static final String NEW_CONTACT = "new_contact";

    private EditText mContactName, mContactPhone, mContactEmail,
            mContactStreet, mContactCity, mContactState, mContactZip;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_contact_data);


       mContactName  = (EditText) findViewById(R.id.contact_name);
        mContactPhone = (EditText) findViewById(R.id.contact_phone);
        mContactEmail = (EditText) findViewById(R.id.contact_email);
        mContactStreet = (EditText) findViewById(R.id.contact_street);
        mContactCity = (EditText) findViewById(R.id.contact_city);
        mContactState = (EditText) findViewById(R.id.contact_state);
        mContactZip = (EditText) findViewById(R.id.contact_zip);

        if(savedInstanceState != null){
            ContactData data = (ContactData) savedInstanceState.getSerializable(NEW_CONTACT);
            mContactName.setText(data.getContactName());
            mContactPhone.setText(data.getContactPhone());
            mContactEmail.setText(data.getContactEmail());
            mContactStreet.setText(data.getContactStreet());
            mContactCity.setText(data.getContactCity());
            mContactState.setText(data.getContactState());
            mContactZip.setText(data.getContactStreet());
        }
        else {


            mContactName.setText("");
            mContactPhone.setText("");
            mContactEmail.setText("");
            mContactStreet.setText("");
            mContactCity.setText("");
            mContactState.setText("");
            mContactZip.setText("");
        }

        enabledEditText(true);




    }//onCreateView


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        ContactData data = getContactData();
        outState.putSerializable(NEW_CONTACT, data);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private void enabledEditText(boolean flag){
        mContactName.setEnabled(flag);
        mContactPhone.setEnabled(flag);
        mContactEmail.setEnabled(flag);
        mContactStreet.setEnabled(flag);
        mContactCity.setEnabled(flag);
        mContactState.setEnabled(flag);
        mContactZip.setEnabled(flag);

    }

    private ContactData getContactData(){

        String name = mContactName.getText().toString().trim();
        String phone = mContactPhone.getText().toString().trim();
        String email = mContactEmail.getText().toString().trim();
        String street = mContactStreet.getText().toString().trim();
        String city = mContactCity.getText().toString().trim();
        String state = mContactState.getText().toString().trim();
        String zip = mContactZip.getText().toString().trim();

        ContactData data = new ContactData(name, phone, email, street, city, state, zip);
        System.out.println("RETURN DATA FROM NEW ACTIVITY: "+data.toString());
        return data;
    }

    public void setNewContactResult() {

        ContactData contact = getContactData();

        Intent data = new Intent();
        data.putExtra(EXTRA_NEW_CONTACT, contact);
        //set result and send it to parent activity
        setResult(RESULT_OK, data);
    }


    @Override
    //create a menu instance
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contact:
                setNewContactResult();
                finish();
                return true;

            case R.id.cancel_contact:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
