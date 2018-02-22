package com.iamrobots.connectfour;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamrobots.connectfour.database.Player;

import java.util.ArrayList;

/**
 * Created by David Lively on 2/22/18.
 * lively@iamrobots.com
 */

public class TopScoresAdaptor extends RecyclerView.Adapter<TopScoresAdaptor.ViewHolder> {

    private ArrayList<Player> mPlayers;
    private Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView scoreTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.top_score_player_name);
            scoreTextView = itemView.findViewById(R.id.top_score_player_wins);
        }
    }

    public TopScoresAdaptor(Context context, ArrayList<Player> players) {
        mPlayers = players;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public TopScoresAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View scoreView = inflater.inflate(R.layout.item_player_score, parent, false);

        ViewHolder viewHolder = new ViewHolder(scoreView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TopScoresAdaptor.ViewHolder holder, int position) {
        Player player = mPlayers.get(position);

        TextView nameTextView = holder.nameTextView;
        TextView scoreTextView = holder.scoreTextView;

        nameTextView.setText(player.getName());
        scoreTextView.setText(String.valueOf(player.getWins()));
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }
}
