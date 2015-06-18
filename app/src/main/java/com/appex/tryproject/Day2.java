package com.appex.tryproject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.appex.tryproject.Resources.Constants;
import com.appex.tryproject.Resources.EventAdapter;
import com.appex.tryproject.Resources.RowItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day2 extends Fragment {
    EventAdapter eventAdapter;
    ExpandableListView eventListView;
    List<String> catItem;
    HashMap<String, List<RowItem>> eventItem;
    String categories[]=Constants.categories,locations[]=Constants.locations,time[]=Constants.time,date[]=Constants.date,contact[]= Constants.contact;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day2, container, false);
        eventListView=(ExpandableListView)rootView.findViewById(R.id.catListDay2);
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/RB.ttf");
        Typeface typeface2=Typeface.createFromAsset(getActivity().getAssets(), "fonts/RL.ttf");
        catItem=new ArrayList<String>();
        eventItem=new HashMap<String, List<RowItem>>();
        for(int i=0;i<categories.length;i++){
            catItem.add(categories[i]);
        }
        RowItem Item1=new RowItem("Event 1",locations[0],time[0],date[0],contact[0]);
        RowItem Item2=new RowItem("Event 2",locations[1],time[1],date[1],contact[1]);
        RowItem Item3=new RowItem("Event 3",locations[2],time[2],date[2],contact[2]);
        List<RowItem> Row1=new ArrayList<RowItem>();
        Row1.add(Item1);
        Row1.add(Item2);
        Row1.add(Item3);
        for (int i=0;i<categories.length;i++)
        {
            eventItem.put(catItem.get(i), Row1);
        }
        eventAdapter= new EventAdapter(getActivity(), catItem,eventItem,typeface,typeface2);
        eventListView.setAdapter(eventAdapter);
        return rootView;
    }
}



