package com.example.androidfinalproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalproject.R;
import com.google.gson.Gson;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;

public class ListFragment extends Fragment {
    private RecyclerView list_LST_recipes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_recipes.setLayoutManager(linearLayoutManager);
//        list_LST_recipes.setAdapter(scoreAdapter);
    }

//    ScoreCallback clickScoreItemCallback = new ScoreCallback() {
//
//        @Override
//        public void onScoreClick(double lan, double lat) {
//            sendMapData.sendLocation(lan,lat);
//        }
//    };

    private void findViews(View view) {
        list_LST_recipes = view.findViewById(R.id.list_LST_recipes);
    }
}