/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static com.example.android.myaddressbook.AlertDialogBox.DELETE_RESULT;
import static com.example.android.myaddressbook.UpdateContactActivity.EXTRA_UPDATE_CONTACT;

public class ContactFragment extends Fragment {

    private static final String ARG_CONTACT_ID = "contact_id";
    private static final String ALERT_BOX = "alert_box";
    public static final String EDIT_CONTACT = "edit_contact";
    private static final String CURRENT_CONTACT = "current_contact";
    private static final int REQUEST_RESULT = 0;
    private static final int REQUEST_EDIT_CONTACT = 1;


    private ContactData mContactData;
    private EditText mContactName, mContactPhone, mContactEmail,
                    mContactStreet, mContactCity, mContactState, mContactZip;

    private Callbacks mCallbacks;

    private ContactsList mContactsList;

    /*
     Required interface for hosting activities
     */
    public interface Callbacks{
        //access to recycle view in contact fragment list
        void onContactUpdate(ContactData data);
        void onDeleteContactUpdate(ContactData data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState != null){
            mContactData = (ContactData) savedInstanceState.getSerializable(CURRENT_CONTACT);
        }else {

            //get contact id
            int contactID = (int) getArguments().getSerializable(ARG_CONTACT_ID);

            mContactData = ContactsList.get(getActivity()).getContactByID((contactID));
        }

        mContactsList = ContactsList.get(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        //get layout and put it in view parent; false - add in activity code
        View view = inflater.inflate(R.layout.activity_contact_data, container, false);

        mContactName  = (EditText) view.findViewById(R.id.contact_name);
        mContactPhone = (EditText) view.findViewById(R.id.contact_phone);
        mContactEmail = (EditText) view.findViewById(R.id.contact_email);
        mContactStreet = (EditText) view.findViewById(R.id.contact_street);
        mContactCity = (EditText) view.findViewById(R.id.contact_city);
        mContactState = (EditText) view.findViewById(R.id.contact_state);
        mContactZip = (EditText) view.findViewById(R.id.contact_zip);


        mContactName.setText(mContactData.getContactName());
        mContactPhone.setText(mContactData.getContactPhone());
        mContactEmail.setText(mContactData.getContactEmail());
        mContactStreet.setText(mContactData.getContactStreet());
        mContactCity.setText(mContactData.getContactCity());
        mContactState.setText(mContactData.getContactState());
        mContactZip.setText(mContactData.getContactZip());

        enabledEditText(false);

        return view;
    }//onCreateView

    @Override
    public void onPause() {
        super.onPause();

        ContactsList.get(getActivity()).updateContactData(mContactData);
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
       super.onSaveInstanceState(outState);

        outState.putSerializable(CURRENT_CONTACT, mContactData);
    }




    public static ContactFragment newInstance(int contactID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT_ID, contactID);

        ContactFragment cf = new ContactFragment();

        cf.setArguments(args);
        return cf;
    }


    @Override
    //create a menu instance
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem changeContactItem = menu.findItem(R.id.change_contact_info);
        MenuItem deleteContactItem = menu.findItem(R.id.delete_contact_info);


    }//onCreateOptionsMenu


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
            case R.id.change_contact_info:

                Intent intent = new Intent(getContext(), UpdateContactActivity.class);
                intent.putExtra(EDIT_CONTACT, mContactData);
                startActivityForResult(intent,REQUEST_EDIT_CONTACT);

                return true;

            case R.id.delete_contact_info:

                FragmentManager fm = getFragmentManager();

                String msg = "Are you sure, you would like to DELETE this contact?\n\n"
                        +mContactData.toString();
                AlertDialogBox alertDialogBox = AlertDialogBox.newInstance(msg);
                alertDialogBox.setTargetFragment(ContactFragment.this, REQUEST_RESULT);
                alertDialogBox.show(fm, ALERT_BOX);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }//onOptionsItemSelected

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_RESULT){
            int value = (int) data.getSerializableExtra(DELETE_RESULT);

            if(value == 0){
                mContactsList.deleteContactData(mContactData);
                mCallbacks.onDeleteContactUpdate(mContactData);
            }
        }

        if(requestCode == REQUEST_EDIT_CONTACT){

            mContactData = (ContactData) data.getSerializableExtra(EXTRA_UPDATE_CONTACT);

            mContactsList.updateContactData(mContactData);
            mCallbacks.onContactUpdate(mContactData);
        }
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

}
