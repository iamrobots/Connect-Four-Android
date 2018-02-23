package com.iamrobots.connectfour.offline;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iamrobots.connectfour.R;
import com.iamrobots.connectfour.database.AppDatabase;
import com.iamrobots.connectfour.database.Player;

import java.lang.ref.WeakReference;

/**
 * Created by Abraham on 2/22/18.
 */

public class AddPlayerDialog extends DialogFragment {
    private static final String TAG = "AddPlayerDialog";

    private EditText mInput;
    private TextView mActionOk, mActionCancel;
    private AppDatabase db;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_player, container, false);

        mActionCancel = view.findViewById(R.id.add_cancel);
        mActionOk = view.findViewById(R.id.add_ok);
        mInput = view.findViewById(R.id.nameInput);

        db = AppDatabase.getInstance(getActivity());

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: getting new player name");

                String input = mInput.getText().toString();
                if(!input.equals("")){
                    //Toast.makeText(getActivity(), input + " added!", Toast.LENGTH_SHORT).show();
                    Player player = new Player(input, Color.RED);
                    new NewPlayer((PlayerListActivity)getActivity(), player).execute();
                    getDialog().dismiss();
                } else {
                    getDialog().dismiss();
                }
            }
        });

        return view;
    }

    private static class NewPlayer extends AsyncTask<Void, Void, Void> {

        private WeakReference<PlayerListActivity> mActivityReference;
        private Player mPlayer;

        NewPlayer(PlayerListActivity context, Player player) {
            mActivityReference = new WeakReference<>(context);
            mPlayer = player;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mActivityReference.get().mDatabase.playerDao().insertAll(mPlayer);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //mActivityReference.get().endAdd();
        }
    }
}
