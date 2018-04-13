/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;

import static com.example.android.myaddressbook.ContactFragment.EDIT_CONTACT;

public class UpdateContactActivity extends AppCompatActivity {

    public static final String EXTRA_UPDATE_CONTACT = "update_contact";
    private static final String SAVE_CONTACT = "save_contact";

    private EditText mContactName, mContactPhone, mContactEmail,
            mContactStreet, mContactCity, mContactState, mContactZip;

    private ContactData mData;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_contact_data);

        if(savedInstanceState != null){
            mData = (ContactData) savedInstanceState.getSerializable(SAVE_CONTACT);
        }else {
            mData = (ContactData) getIntent().getSerializableExtra(EDIT_CONTACT);
        }

        mContactName  = (EditText) findViewById(R.id.contact_name);
        mContactPhone = (EditText) findViewById(R.id.contact_phone);
        mContactEmail = (EditText) findViewById(R.id.contact_email);
        mContactStreet = (EditText) findViewById(R.id.contact_street);
        mContactCity = (EditText) findViewById(R.id.contact_city);
        mContactState = (EditText) findViewById(R.id.contact_state);
        mContactZip = (EditText) findViewById(R.id.contact_zip);


        mContactName.setText(mData.getContactName());
        mContactPhone.setText(mData.getContactPhone());
        mContactEmail.setText(mData.getContactEmail());
        mContactStreet.setText(mData.getContactStreet());
        mContactCity.setText(mData.getContactCity());
        mContactState.setText(mData.getContactState());
        mContactZip.setText(mData.getContactZip());

        enabledEditText(true);

    }//onCreateView

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVE_CONTACT, mData);
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

    private void getContactData(){

        String name = mContactName.getText().toString().trim();
        String phone = mContactPhone.getText().toString().trim();
        String email = mContactEmail.getText().toString().trim();
        String street = mContactStreet.getText().toString().trim();
        String city = mContactCity.getText().toString().trim();
        String state = mContactState.getText().toString().trim();
        String zip = mContactZip.getText().toString().trim();

        mData.setContactName(name);
        mData.setContactPhone(phone);
        mData.setContactEmail(email);
        mData.setContactStreet(street);
        mData.setContactCity(city);
        mData.setContactState(state);
        mData.setContactZip(zip);

        System.out.println("RETURN DATA FROM UPDATE ACTIVITY: "+mData.toString());
    }

    public void setUpdateContactResult() {
        getContactData();
        Intent data = new Intent();
        data.putExtra(EXTRA_UPDATE_CONTACT, mData);
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
                //update contact
                setUpdateContactResult();
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
