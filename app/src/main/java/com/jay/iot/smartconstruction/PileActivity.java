package com.jay.iot.smartconstruction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.jay.iot.smartconstruction.view.MapContainer;
import com.jay.iot.smartconstruction.view.Marker;

import java.util.ArrayList;

/**
 * Created by H151136 on 12/14/2017.
 */

public class PileActivity extends AppCompatActivity {
    private static final String TAG = PileActivity.class.getName();
    private SearchView mPileSearchView;
    private MapContainer mPileMapContainer;
    private ArrayList<Marker> mPileMarkers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pile_position);
        mPileSearchView = findViewById(R.id.sv_pile_id);
        mPileSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "Query " + s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mPileMapContainer = (MapContainer) findViewById(R.id.map_pile);
        mPileMapContainer.getMapView().setImageResource(R.drawable.bg_net);
        mPileMarkers = new ArrayList<>();
        mPileMarkers.add(new Marker(0.25f, 0.25f, R.drawable.ic_point_red));
        mPileMarkers.add(new Marker(0.5f, 0.5f, R.drawable.ic_point_yellow));
        mPileMarkers.add(new Marker(0.75f, 0.75f, R.drawable.ic_point_green));
        mPileMapContainer.setMarkers(mPileMarkers);
    }
}
