package chipset.techtatva.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import chipset.techtatva.R;
import chipset.techtatva.model.events.Category;
import chipset.techtatva.model.events.Event;

public class EventAdapterNew extends BaseExpandableListAdapter{
    private class ViewHolder {
        TextView textName;
        TextView textMaxNo;
        CardView eventCardView;
    }
    private ViewHolder mViewHolder;
    private Context mContext;
    private ArrayList<Category> mCategories;
    public EventAdapterNew(Context context,ArrayList<Category> categories){
        mContext= context;
        mCategories=new ArrayList<Category>();
        mCategories.addAll(categories);
    }
    @Override
    public int getGroupCount() {
        return mCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCategories.get(groupPosition).getEvents().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mCategories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mCategories.get(groupPosition).getEvents().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.cat_item, null);
        }
        TextView CatHeader = (TextView) convertView
                .findViewById(R.id.catName);
        CatHeader.setText(mCategories.get(groupPosition).getCatName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_item_new, null);
            mViewHolder = new ViewHolder();
            mViewHolder.eventCardView=(CardView)convertView.findViewById(R.id.eventCards);
            mViewHolder.textName=(TextView)convertView.findViewById(R.id.eventName);
            mViewHolder.textMaxNo=(TextView)convertView.findViewById(R.id.eventMaxNo);

            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
       Event event = mCategories.get(groupPosition).getEvents().get(childPosition);
        mViewHolder.textName.setText(event.getEvent_name());
        mViewHolder.textMaxNo.setText("Max Number of Players : " +event.getEventMaxTeamNumber());
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
