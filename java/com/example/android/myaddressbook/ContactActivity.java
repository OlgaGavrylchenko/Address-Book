/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ContactActivity extends MainActivity{

    private static final String EXTRA_CONTACT_ID = "contact_id";

    @Override
    protected Fragment createFragment() {
        int contact_id = (int) getIntent().getSerializableExtra(EXTRA_CONTACT_ID);
        System.out.println("ACTIVITY ID: "+String.valueOf(contact_id));
        return ContactFragment.newInstance(contact_id);
    }

    public static Intent newIntent(Context context, int contact_id){
        Intent intent = new Intent(context, ContactActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, contact_id);
        return intent;
    }
}
