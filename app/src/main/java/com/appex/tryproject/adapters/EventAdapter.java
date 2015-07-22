package com.appex.tryproject.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.appex.tryproject.R;
import com.appex.tryproject.model.events.RowItem;
import com.appex.tryproject.model.events.CatItem;

import java.util.ArrayList;

public class EventAdapter extends BaseExpandableListAdapter {
    private class ViewHolder {
        RowItem item;
        TextView textName;
        TextView textLocation;
        TextView textTime;
        TextView textDate;
        TextView textContact;
        TextView textCall;

    }

    private ViewHolder holder;
    private Context context;
    private ArrayList<CatItem> CatItem;
    private ArrayList<CatItem> CategoryItem;
    Typeface tf, tf2;

    public EventAdapter(Context context, ArrayList<CatItem> CatItem, Typeface tf, Typeface tf2) {
        this.context = context;
        this.CatItem = new ArrayList<CatItem>();
        this.CatItem.addAll(CatItem);
        this.CategoryItem = new ArrayList<CatItem>();
        this.CategoryItem.addAll(CatItem);
        this.tf = tf;
        this.tf2 = tf2;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        ArrayList<RowItem> EventItem = CatItem.get(groupPosition).getEventItem();
        return EventItem.get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_item, null);
            holder = new ViewHolder();
            holder.textName = (TextView) convertView.findViewById(R.id.eventName);
            holder.textLocation = (TextView) convertView.findViewById(R.id.eventLocation);
            holder.textTime = (TextView) convertView.findViewById(R.id.eventTime);
            holder.textDate = (TextView) convertView.findViewById(R.id.eventDate);
            holder.textContact = (TextView) convertView.findViewById(R.id.eventContact);
            holder.textCall=(TextView)convertView.findViewById(R.id.contactCall);
            holder.textContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.textCall.getVisibility() == View.GONE)
                        holder.textCall.setVisibility(View.VISIBLE);
                    else
                        holder.textCall.setVisibility(View.GONE);
                }
            });
            holder.textCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final String finalContact = "999";
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callIntent(context, finalContact);
                        }
                    });
                    builder.setNegativeButton("NO", null);
                    builder.setMessage("Call " + 999 + " ?");
                    builder.create();
                    builder.show();
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getChild(groupPosition, childPosition);
        holder.textName.setText(rowItem.getEventName());
        holder.textLocation.setText(rowItem.getEventLocation());
        holder.textTime.setText(rowItem.getEventTime());
        holder.textDate.setText(rowItem.getEventDate());
        holder.textContact.setText(rowItem.getEventContact());
        holder.textName.setTypeface(tf);
        holder.textLocation.setTypeface(tf2);
        holder.textDate.setTypeface(tf2);
        holder.textTime.setTypeface(tf2);
        holder.textContact.setTypeface(tf2);
        holder.textCall.setText(rowItem.getEventCall());
        holder.textCall.setTypeface(tf2);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<RowItem> eventList = CatItem.get(groupPosition).getEventItem();
        return eventList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.CatItem.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.CatItem.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        CatItem catTitle = (CatItem) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.cat_item, null);
        }

        TextView CatHeader = (TextView) convertView
                .findViewById(R.id.catName);
        CatHeader.setText(catTitle.getCategory());
        CatHeader.setTypeface(tf);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String Query) {
        Query = Query.toLowerCase();
        CatItem.clear();
        if (Query.isEmpty()) {
            CatItem.addAll(CategoryItem);
        } else {
            for (CatItem catItem : CategoryItem) {
                ArrayList<RowItem> eventList = catItem.getEventItem();
                ArrayList<RowItem> eventList2 = new ArrayList<RowItem>();
                for (RowItem event : eventList) {
                    if (event.getEventName().toLowerCase().contains(Query)) {
                        eventList2.add(event);
                    }
                }
                if (eventList2.size() > 0) {
                    CatItem newCatItem = new CatItem(catItem.getCategory(), eventList2);
                    CatItem.add(newCatItem);
                } else if (catItem.getCategory().toLowerCase().contains(Query)) {
                    CatItem.add(catItem);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void callIntent(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}

