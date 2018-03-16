package com.iamrobots.connectfour.gamePlay;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.offline.GameMenuActivity;
import com.iamrobots.connectfour.offline.PlayerSelectActivity;

/*
 *  No longer used after implementing GameHomeActivity. Since there is no menu to return to,
 *  the dialog no longer made sense.
 */

public class PlayAgainDialog extends DialogFragment {

    private static final String TAG = "PlayAgainDialog";

    private TextView mAgainYes, mAgainNo;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_play_again, container, false);

        mAgainYes = view.findViewById(R.id.againYes);
        mAgainNo = view.findViewById(R.id.againNo);

        mAgainNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: returning to menu");
                Intent i = new Intent(getActivity(), GameMenuActivity.class);
                startActivity(i);
            }
        });

        mAgainYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: returning to player selection");
                Intent i = new Intent(getActivity(), PlayerSelectActivity.class);
                i.putExtra("FROM_TAB", "");
                startActivity(i);
            }
        });

        return view;
    }
}