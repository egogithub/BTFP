package com.worldline.ego.pebbletransport.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a143210 on 7/12/2016.
 */

public class DirectionDialogFragment extends DialogFragment {
    String title;
    CharSequence[] directions;

    public static DirectionDialogFragment newInstance(String title, CharSequence[] directions) {
        DirectionDialogFragment ddf = new DirectionDialogFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putCharSequenceArray("directions", directions);
        ddf.setArguments(args);
        return ddf;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        title = getArguments().getString("title");
        directions = getArguments().getCharSequenceArray("directions");

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle(title)
                .setItems(directions, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("DirectionDialog", "Selected Direction "+which);
                    }
                });

        // 3. Get the AlertDialog from create()
        return builder.create();
    }
}
