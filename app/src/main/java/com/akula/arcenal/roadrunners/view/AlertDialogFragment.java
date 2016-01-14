package com.akula.arcenal.roadrunners.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Arcenal on 14/1/2016.
 */
public class AlertDialogFragment extends DialogFragment{
    public interface OnDialogConfirmListener{
        void OnDialogConfirm();
    }

    private String mMessage = "Message Placeholder";
    private OnDialogConfirmListener mListener = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setMessage(mMessage);
        alertBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mListener != null){
                    mListener.OnDialogConfirm();
                }
            }
        });
        return alertBuilder.create();
    }

    public void setListener(OnDialogConfirmListener listener){
        mListener = listener;
    }

    public void setMessage(String message){
        mMessage = message;
    }
}
