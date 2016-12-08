package com.worldline.ego.pebbletransport.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.worldline.ego.pebbletransport.pojo.TranspLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a143210 on 7/12/2016.
 */

public class DirectionDialogFragment extends DialogFragment {
    String id;
    String mode;
    CharSequence[] directions;
    //TranspLine item;

    public interface DirectionDialogListener {
        public void onFromToDirectionClick(DialogFragment dialog, String id, String mode, int direction);
    }

    DirectionDialogListener mListener;

    public static DirectionDialogFragment newInstance(TranspLine item) {
        DirectionDialogFragment ddf = new DirectionDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable("item", item);
//        args.putString("title", title);
//        args.putCharSequenceArray("directions", directions);
        ddf.setArguments(args);
        return ddf;
    }

    public static DirectionDialogFragment newInstance(String id, String mode, CharSequence[] directions) {
        DirectionDialogFragment ddf = new DirectionDialogFragment();

        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("mode", mode);
        args.putCharSequenceArray("directions", directions);
        ddf.setArguments(args);
        return ddf;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DirectionDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DirectionDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //item = getArguments().getSerializable("item");
        id = getArguments().getString("id");
        mode = getArguments().getString("mode");
        directions = getArguments().getCharSequenceArray("directions");

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Line: "+id+" Mode: "+mode)
                .setItems(directions, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("DirectionDialog", "Selected Direction "+which);
                            mListener.onFromToDirectionClick(DirectionDialogFragment.this,id, mode, which);
                    }
                });

        // 3. Get the AlertDialog from create()
        return builder.create();
    }


}
