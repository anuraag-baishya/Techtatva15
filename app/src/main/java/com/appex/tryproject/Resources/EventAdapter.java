package com.appex.tryproject.Resources;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.appex.tryproject.R;

import java.util.HashMap;
import java.util.List;

public class EventAdapter extends BaseExpandableListAdapter{
    Context context;
    ViewHolder holder;
    Typeface tf1,tf2;
    private List<String> CatItem;
    private HashMap<String,List<RowItem>> EventItem;
    public EventAdapter(Context context, List<String> CatItem,
                        HashMap<String, List<RowItem>> EventItem, Typeface tf1,Typeface tf2) {
        this.context = context;
        this.CatItem = CatItem;
        this.EventItem=EventItem;
        this.tf1=tf1;
        this.tf2=tf2;
    }
    private class ViewHolder{
        TextView textName;
        TextView textLocation;
        TextView textTime;
        TextView textDate;
        TextView textContact;

    }
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.EventItem.get(this.CatItem.get(groupPosition))
                .get(childPosititon);
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
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getChild(groupPosition, childPosition);
        holder.textName.setText(rowItem.getEventName());
        holder.textLocation.setText(rowItem.getEventLocation());
        holder.textTime.setText(rowItem.getEventTime());
        holder.textDate.setText(rowItem.getEventDate());
        holder.textContact.setText(rowItem.getEventContact());
        holder.textName.setTypeface(tf1);
        holder.textLocation.setTypeface(tf2);
        holder.textTime.setTypeface(tf2);
        holder.textDate.setTypeface(tf2);
        holder.textContact.setTypeface(tf2);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.EventItem.get(this.CatItem.get(groupPosition))
                .size();
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
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.cat_item, null);
        }

        TextView CatHeader = (TextView) convertView
                .findViewById(R.id.catName);
        CatHeader.setText(headerTitle);
        CatHeader.setTypeface(tf1);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
