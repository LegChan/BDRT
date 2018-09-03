package com.nrk4220.android.bdrt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CoverFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.song_fragment, container, false);

        CoverUtils coverUtils = new CoverUtils();
        ArrayList<SongDetail> cover = coverUtils.getSong();
        final CustomAdapter coverSongAdapter = new CustomAdapter(getActivity(), cover);
        ListView coverSongs = (ListView) rootView.findViewById(R.id.list);
        coverSongs.setAdapter(coverSongAdapter);
        coverSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SongDetail song = coverSongAdapter.getItem(i);

                ImageView cover = (ImageView) getActivity().findViewById(R.id.songCover);
                TextView eng = (TextView) getActivity().findViewById(R.id.engName);
                TextView jap = (TextView) getActivity().findViewById(R.id.japName);

                cover.setImageResource(song.getImageData());
                eng.setText(song.getEngName());
                jap.setText(song.getJapName());

                SongDetail.setStreamUrl(song.getUrl());

            }
        });

        return rootView;
    }
}
