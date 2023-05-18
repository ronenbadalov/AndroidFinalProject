package com.example.androidfinalproject.Fragments;

import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import androidx.appcompat.widget.AppCompatEditText;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.android.material.button.MaterialButton;
        import com.google.android.material.textview.MaterialTextView;
        import com.google.gson.Gson;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;

public class RecipeListItem extends Fragment {
    private RecyclerView list_LST_scores;

    private ScoreAdapter scoreAdapter;
    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String KEY_LOCATION = "KEY_LOCATION";

    private ArrayList<Score> scores;

    private SendMapData sendMapData;


    public ListFragment(SendMapData sendMapData){
        this.sendMapData = sendMapData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        int userScore = getArguments().getInt(KEY_SCORE);
        double[] lanLat = getArguments().getDoubleArray(KEY_LOCATION);

        Score userScoreItem = new Score();
        userScoreItem.setScore(userScore);
        userScoreItem.setLat(lanLat[0]);
        userScoreItem.setLan(lanLat[1]);

        String fromSP =  MySP3.getInstance().getString("score-list","");
        ScoreList scorelistFromJson;
        if(fromSP == ""){
            scorelistFromJson = new ScoreList();
            scorelistFromJson.setName("score-list");
            ArrayList<Score> scoreList = new ArrayList<Score>();
            scoreList.add(userScoreItem);
            scorelistFromJson.setScores(scoreList);
        }else{
            scorelistFromJson = new Gson().fromJson(fromSP, ScoreList.class );
            scorelistFromJson.getScores().add(userScoreItem);
        }
        Comparator<Score> comparator = new Comparator<Score>() {
            @Override
            public int compare(Score obj1, Score obj2) {
                return obj2.getScore() - obj1.getScore();
            }
        };

        Collections.sort(scorelistFromJson.getScores(), comparator);
        ArrayList<Score> firstTenScores = new ArrayList<Score>(scorelistFromJson.getScores().subList(0, Math.min(scorelistFromJson.getScores().size(), 10)));
        this.scores = firstTenScores;
        scoreAdapter = new ScoreAdapter(getActivity(),scores,clickScoreItemCallback);
        String scoreListJson = new Gson().toJson(scorelistFromJson);
        MySP3.getInstance().putString("score-list", scoreListJson);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_scores.setLayoutManager(linearLayoutManager);
        list_LST_scores.setAdapter(scoreAdapter);
    }

    ScoreCallback clickScoreItemCallback = new ScoreCallback() {

        @Override
        public void onScoreClick(double lan, double lat) {
            sendMapData.sendLocation(lan,lat);
        }
    };

    private void findViews(View view) {
        list_LST_scores = view.findViewById(R.id.list_LST_scores);
    }
}