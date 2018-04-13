/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;


public class ContactPageActivity extends AppCompatActivity
        implements ContactFragment.Callbacks{

    private static final String EXTRA_CONTACT_ID = "contact_id";

    private final int SIZE = 1;
    private int i;

    private List<ContactData> mContactDataList;
    private ViewPager mViewPager;
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        int contactID = (int) getIntent().getSerializableExtra(EXTRA_CONTACT_ID);

        mViewPager = (ViewPager) findViewById(R.id.contact_view_page);

        mContactDataList = ContactsList.get(this).getContactDataList();

        FragmentManager fm = getSupportFragmentManager();

        mAdapter = new MyPagerAdapter(fm);

        //override adapter for a view pager
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOffscreenPageLimit(SIZE);

        for(i=0; i<mContactDataList.size(); i++){
            if(mContactDataList.get(i).getContactID() == contactID){
                //must be index from an array
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }


    public static Intent newIntent(Context context, int contact_id){
        Intent intent = new Intent(context, ContactPageActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, contact_id);
        return intent;
    }


    @Override
    public void onContactUpdate(ContactData data) {

        FragmentManager fm = getSupportFragmentManager();

        mAdapter = null;
        mAdapter = new MyPagerAdapter(fm);

        //override adapter for a view pager
        mViewPager.setAdapter(mAdapter);

        int contactID = data.getContactID();
        mContactDataList = ContactsList.get(this).getContactDataList();
        mAdapter.notifyDataSetChanged();
        for(i=0; i<mContactDataList.size(); i++){
            if(mContactDataList.get(i).getContactID() == contactID){
                //must be index from an array
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onDeleteContactUpdate(ContactData data) {

        mContactDataList = ContactsList.get(this).getContactDataList();

        mAdapter.notifyDataSetChanged();

        mViewPager.setCurrentItem(0);

       /* if(mContactDataList.size() > 0) {
            if (i < 0) {
                i = 0;
                mViewPager.setCurrentItem(i);
            } else{
                i= i-1;
                mViewPager.setCurrentItem(i);
            }
        }*/

    }//onDeleteContactUpdate


    private class MyPagerAdapter extends FragmentStatePagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            ContactData data = mContactDataList.get(position);

            return ContactFragment.newInstance(data.getContactID());
        }

        @Override
        public int getCount() {
            return mContactDataList.size();
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }
    }


}
