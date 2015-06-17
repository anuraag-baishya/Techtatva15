package com.appex.tryproject.Resources;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appex.tryproject.R;

import java.util.List;

public class EventAdapter extends BaseAdapter{
    Context context;
    ViewHolder holder;
    List<RowItem> eventItems;
    Typeface tf1,tf2;
    public EventAdapter(Context context, List<RowItem> items,Typeface typeface1, Typeface typeface2) {
        this.context = context;
        this.eventItems = items;
        this.tf1 = typeface1;
        this.tf2 = typeface2;
    }
    private class ViewHolder{
        TextView textName;
        TextView textLocation;
        TextView textTime;
        TextView textDate;
        TextView textContact;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;

        final LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.event_item, null);
            holder = new ViewHolder();
            holder.textName = (TextView) convertView.findViewById(R.id.eventName);
            holder.textLocation = (TextView) convertView.findViewById(R.id.eventLocation);
            holder.textTime = (TextView) convertView.findViewById(R.id.eventTime);
            holder.textDate = (TextView) convertView.findViewById(R.id.eventDate);
            holder.textContact = (TextView) convertView.findViewById(R.id.eventContact);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getItem(position);

        holder.textName.setText(rowItem.getEventName());
        holder.textLocation.setText(rowItem.getEventLocation());
        holder.textTime.setText(rowItem.getEventTime());
        holder.textDate.setText(rowItem.getEventDate());
        holder.textContact.setText(rowItem.getEventContact());
        holder.textName.setTypeface(tf1);
        holder.textContact.setTypeface(tf2);
        holder.textDate.setTypeface(tf2);
        holder.textLocation.setTypeface(tf2);
        holder.textDate.setTypeface(tf2);
        holder.textTime.setTypeface(tf2);
        return convertView;
    }
    @Override
    public int getCount() {
        return eventItems.size();
    }

    @Override
    public Object getItem(int position) {
        return eventItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventItems.indexOf(getItem(position));
    }
}
