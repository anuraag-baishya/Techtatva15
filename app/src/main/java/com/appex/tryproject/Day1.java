package com.appex.tryproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.appex.tryproject.Resources.Constants;
import com.appex.tryproject.Resources.EventAdapter;
import com.appex.tryproject.Resources.RowItem;

import java.util.ArrayList;
import java.util.List;

public class Day1 extends Fragment {
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
        View rootView = inflater.inflate(R.layout.day_1,container,false);
        eventItems = new ArrayList<RowItem>();
        for (int i = 0; i < events.length; i++) {
            RowItem item = new RowItem(events[i], locations[i], time[i], date[i], contact[i]);
            eventItems.add(item);
        }
        eventView = (ListView) rootView.findViewById(R.id.eventListDay1);
        EventAdapter adapter = new EventAdapter(getActivity(), eventItems);
        eventView.setAdapter(adapter);
        eventView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast toast = Toast.makeText(getActivity(),
                        "Item " + (position + 1) + ": " + eventItems.get(position),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });
        return rootView;
    }
}



