/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.android.myaddressbook.NewContactActivity.EXTRA_NEW_CONTACT;



public class ContactListFragment extends Fragment{

    private static final int REQUEST_CONTACT = 1;

    private RecyclerView mContactListRecyclerView;
    private ContactAdapter mContactAdapter;


    private int selectedItem = 0;
    private Callbacks mCallbacks;

    /*
     Required interface for hosting activities
     */
    public interface Callbacks{
        void onContactSelected(ContactData data);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        //get layout and put it in view parent; false - add in activity code
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        //get recycle view object
        mContactListRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler_view);
        //layout manager set items in a screen and define how scrolling works
        mContactListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //update recycler view
        updateRecyclerView();

        return view;
    }


    public void updateRecyclerView(){
        //get list of contacts
        ContactsList list = ContactsList.get(getActivity());

        List<ContactData> contactDataList = list.getContactDataList();

        /*if(mContactAdapter == null){

            mContactAdapter = new ContactAdapter(contactDataList);
            mContactListRecyclerView.setAdapter(mContactAdapter);
        }
        else{

           //mContactAdapter
            mContactAdapter.setContactDataList(contactDataList);
            mContactAdapter.notifyItemChanged(selectedItem);
        }*/

        mContactAdapter = null;
        mContactAdapter = new ContactAdapter(contactDataList);
        mContactListRecyclerView.setAdapter(mContactAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
    }


    //define view holder class
    private class ContactHolder extends RecyclerView.ViewHolder
                                implements View.OnClickListener{

        private ContactData mContactData;
        private TextView mContactName;

        public ContactHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(viewType, parent,false));
            itemView.setOnClickListener(this);

            mContactName = (TextView) itemView.findViewById(R.id.contact_nickname);
        }

        //display each contact
        public void bind(ContactData data){

            mContactData = data;
            mContactName.setText(mContactData.getContactName());
        }

        @Override
        public void onClick(final View view) {
            //selectedItem = getLayoutPosition();
            selectedItem = mContactListRecyclerView.getChildLayoutPosition(view);

            mCallbacks.onContactSelected(mContactData);
        }
    }


    //define adapter class
    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder>{

        private List<ContactData> mContactDataList;

        public ContactAdapter(List<ContactData> data){

            mContactDataList = data;
        }

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //obtain layout from a given context
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            ContactHolder mainHolder = new ContactHolder(layoutInflater, parent, viewType);
            return mainHolder;
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            //final ContactHolder mainHolder = (ContactHolder) holder;
            ContactData mContact = mContactDataList.get(position);
            holder.bind(mContact);
        }

        @Override
        public int getItemCount() {
            return mContactDataList.size();
        }

        public int getItemViewType(int position){
            if(position % 2 == 0) {
                return R.layout.fragment_contact;
            }
            else{
                return R.layout.fragment_contact_two;
            }
        }//getItemViewType

        public void setContactDataList(List<ContactData> data){
            mContactDataList = data;
        }
    }


    @Override
    //create a menu instance
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.new_contact, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem addContactItem = menu.findItem(R.id.add_new_contact);

    }//onCreateOptionsMenu


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
            case R.id.add_new_contact:
                Intent intent = new Intent(getContext(), NewContactActivity.class);
                startActivityForResult(intent, REQUEST_CONTACT);
                return true; //no further processing is necessary

            default:
        return super.onOptionsItemSelected(item);
        }
    }//onOptionsItemSelected



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        // Check which request we're responding to
        if (requestCode == REQUEST_CONTACT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                if(data == null){
                    return;
                }else{
                    ContactData contact = (ContactData) data.getExtras().getSerializable(EXTRA_NEW_CONTACT);

                    if(!contact.getContactName().equals("")){
                        ContactsList list = ContactsList.get(getActivity());
                        list.addNewContact(contact);
                        updateRecyclerView();
                    }
                }

            }
        }
    }//OnActivityResult

}
