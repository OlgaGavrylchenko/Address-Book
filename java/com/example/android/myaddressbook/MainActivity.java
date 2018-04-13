/*
*   CIT243-H1
*   Project - Address Book
*   @Created by Olga Gavrylchenko, 04/10/2018
* */
package com.example.android.myaddressbook;


import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public abstract class MainActivity extends AppCompatActivity {

    //abstract method
    protected abstract Fragment createFragment();
    private TextView mAPILevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        //instantiate fragment manager object which add view to activity
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            //get fragment
            fragment = createFragment();

            //used to add, remove, attach, detach, replace fragments in the fragments list
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
}
