package com.appex.tryproject.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.appex.tryproject.R;
import com.appex.tryproject.model.events.CatItem;
import com.appex.tryproject.model.events.RowItem;

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

    private ViewHolder mViewHolder;
    private Context mContext;
    private ArrayList<CatItem> mCatItem;
    private ArrayList<CatItem> mCategoryItem;

    public EventAdapter(Context context, ArrayList<CatItem> CatItem) {
        mContext = context;
        mCatItem = new ArrayList<CatItem>();
        mCatItem.addAll(CatItem);
        mCategoryItem = new ArrayList<CatItem>();
        mCategoryItem.addAll(CatItem);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        ArrayList<RowItem> EventItem = mCatItem.get(groupPosition).getEventItem();
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
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.textName = (TextView) convertView.findViewById(R.id.eventName);
            mViewHolder.textLocation = (TextView) convertView.findViewById(R.id.eventLocation);
            mViewHolder.textTime = (TextView) convertView.findViewById(R.id.eventTime);
            mViewHolder.textDate = (TextView) convertView.findViewById(R.id.eventDate);
            mViewHolder.textContact = (TextView) convertView.findViewById(R.id.eventContact);
            mViewHolder.textCall=(TextView)convertView.findViewById(R.id.contactCall);
            mViewHolder.textContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewHolder.textCall.getVisibility() == View.GONE)
                        mViewHolder.textCall.setVisibility(View.VISIBLE);
                    else
                        mViewHolder.textCall.setVisibility(View.GONE);
                }
            });
            mViewHolder.textCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    final String finalContact = "999";
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callIntent(mContext, finalContact);
                        }
                    });
                    builder.setNegativeButton("NO", null);
                    builder.setMessage("Call " + 999 + " ?");
                    builder.create();
                    builder.show();
                }
            });
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getChild(groupPosition, childPosition);
        mViewHolder.textName.setText(rowItem.getEventName());
        mViewHolder.textLocation.setText(rowItem.getEventLocation());
        mViewHolder.textTime.setText(rowItem.getEventTime());
        mViewHolder.textDate.setText(rowItem.getEventDate());
        mViewHolder.textContact.setText(rowItem.getEventContact());
        mViewHolder.textCall.setText(rowItem.getEventCall());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<RowItem> eventList = mCatItem.get(groupPosition).getEventItem();
        return eventList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mCatItem.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mCatItem.size();
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
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.cat_item, null);
        }

        TextView CatHeader = (TextView) convertView
                .findViewById(R.id.catName);
        CatHeader.setText(catTitle.getCategory());

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
        mCatItem.clear();
        if (Query.isEmpty()) {
            mCatItem.addAll(mCategoryItem);
        } else {
            for (CatItem catItem : mCategoryItem) {
                ArrayList<RowItem> eventList = catItem.getEventItem();
                ArrayList<RowItem> eventList2 = new ArrayList<RowItem>();
                for (RowItem event : eventList) {
                    if (event.getEventName().toLowerCase().contains(Query)) {
                        eventList2.add(event);
                    }
                }
                if (eventList2.size() > 0) {
                    CatItem newCatItem = new CatItem(catItem.getCategory(), eventList2);
                    mCatItem.add(newCatItem);
                } else if (catItem.getCategory().toLowerCase().contains(Query)) {
                    mCatItem.add(catItem);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void callIntent(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}

