/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ContactListActivity extends MainActivity
                implements ContactListFragment.Callbacks,
                            ContactFragment.Callbacks{

    private static final String BACKSTACK_NAME = "backstack_fragment" ;

    public Fragment createFragment(){

        return new ContactListFragment();
    }

    protected int getLayoutResId(){
        return R.layout.activity_master_detail;
    }

    @Override
    public void onContactSelected(ContactData data) {
        if(findViewById(R.id.detail_fragment_container) == null){
            int id = data.getContactID();
            Intent intent = ContactPageActivity.newIntent(this, id);
            startActivity(intent);
        }else{
            int id = data.getContactID();
            Fragment newDetail = ContactFragment.newInstance(id);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .addToBackStack(BACKSTACK_NAME)
                    .commit();
        }

    }


    @Override
    public void onContactUpdate(ContactData data){
        ContactListFragment lf = (ContactListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        lf.updateRecyclerView();


        if(findViewById(R.id.fragment_container) != null) {
            onContactSelected(data);
        }
    }

    @Override
    public void onDeleteContactUpdate(ContactData data) {
        ContactListFragment lf = (ContactListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        lf.updateRecyclerView();

        //remove current fragment (ContactFragment)
        getSupportFragmentManager().popBackStack(BACKSTACK_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
