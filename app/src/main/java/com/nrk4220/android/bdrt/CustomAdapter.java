package com.nrk4220.android.bdrt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<SongDetail> {

    private String status;

    public CustomAdapter(Context context, ArrayList<SongDetail> songDetails){
        super(context,0, songDetails);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View parentView = convertView;
        if(parentView == null){
            parentView = LayoutInflater.from(getContext()).inflate(R.layout.song_list, parent, false);
        }

        SongDetail song = getItem(position);

        TextView eng = (TextView) parentView.findViewById(R.id.eng);
        TextView jap = (TextView) parentView.findViewById(R.id.jap);

        eng.setText(song.getEngName());
        jap.setText(song.getJapName());

        return parentView;
    }
}
