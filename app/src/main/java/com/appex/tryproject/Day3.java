package com.appex.tryproject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.appex.tryproject.Resources.Constants;
import com.appex.tryproject.Resources.EventAdapter;
import com.appex.tryproject.Resources.RowItem;

import java.util.ArrayList;
import java.util.List;

public class Day3 extends Fragment {
    String events[]= Constants.events,locations[]=Constants.locations,time[]=Constants.time,date[]=Constants.date,contact[]= Constants.contact;
    List<RowItem> eventItems;
    ListView eventView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day3,container,false);
        eventItems = new ArrayList<RowItem>();
        for (int i = 0; i < events.length; i++) {
            RowItem item = new RowItem(events[i], locations[i], time[i], date[i], contact[i]);
            eventItems.add(item);
        }
        eventView = (ListView) rootView.findViewById(R.id.eventListDay3);
        Typeface custom_font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RB.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RL.ttf");
        EventAdapter adapter = new EventAdapter(getActivity(), eventItems,custom_font1,custom_font2);
        eventView.setAdapter(adapter);
        eventView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if(view.findViewById(R.id.description).getVisibility() == View.GONE) {
                    view.findViewById(R.id.description).setVisibility(View.VISIBLE);
                }else{
                    view.findViewById(R.id.description).setVisibility(View.GONE);
                }
            }
        });
        return rootView;
    }
}



