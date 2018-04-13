/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AlertDialogBox extends DialogFragment {

    private static final String ARG_MESSAGE = "message";
    public static final String DELETE_RESULT = "is_delete";
    private static final String SAVE_MSG_STATE = "save_msg_state";
    private String mMessage;

    public static AlertDialogBox newInstance(String msg){
        Bundle arg = new Bundle();
        arg.putSerializable(ARG_MESSAGE, msg);
        AlertDialogBox alertBox = new AlertDialogBox();
        alertBox.setArguments(arg);
        return alertBox;
    }

    public Dialog onCreateDialog(Bundle savedInstanceSate){

        if(savedInstanceSate != null){
            mMessage = (String) savedInstanceSate.getString(SAVE_MSG_STATE);
        }

        mMessage = getArguments().getSerializable(ARG_MESSAGE).toString();

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.alert_box_title)
                .setMessage(mMessage)
                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, 0);
                    }
                })
                .setNegativeButton(R.string.btn_no, null)
                .create();
    }

    private void sendResult(int resultCode, int value){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(DELETE_RESULT, value);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);

        bundle.putString(SAVE_MSG_STATE, mMessage);
    }
}
