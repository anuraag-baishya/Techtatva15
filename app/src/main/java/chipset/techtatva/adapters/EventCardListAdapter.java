package chipset.techtatva.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chipset.potato.Potato;
import chipset.techtatva.R;
import chipset.techtatva.database.DBHelper;
import chipset.techtatva.model.events.Event;
/**
 * Created by saketh on 18/9/15.
 */
public class EventCardListAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private ArrayList<Event> mEventList;
    private Context mContext;
    private DBHelper dbHelper;
    public EventCardListAdapter(Context c, ArrayList<Event> events){
        this.mContext = c;
        this.mEventList = new ArrayList<Event>();
        this.mEventList.addAll(events);
        for (Event event:mEventList){
            Log.d("eventsc", "Event name : " + event.getEvent_name());
        }
        Log.d("adapter", "created");
        dbHelper = new DBHelper(mContext);
    }
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.event_item, viewGroup, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EventViewHolder mHolder, int i) {
        final Event event = mEventList.get(i);
        mHolder.textName.setText(event.getEvent_name());
        mHolder.textDate.setText("1/1/1");
        mHolder.textLocation.setText("mit");
        mHolder.textTime.setText("00:00");
        mHolder.textMaxSize.setText("max no per team : "+ event.getEventMaxTeamNumber());
        mHolder.textContact.setText("Contact the cat head");
        mHolder.textFav.setText("Add to favs");
        mHolder.textFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addToFavorites(event);
            }
        });
        mHolder.textCall.setText("Click here to call 999");
        mHolder.eventInfoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =mHolder.textName.getText().toString();
                for (final Event e : mEventList) {
                    if (e.getEvent_name().toLowerCase().equals(name.toLowerCase())) {
                        TextView message = new TextView(mContext);
                        message.setPadding(15,15,15,15);
                        message.setMovementMethod(LinkMovementMethod.getInstance());
                        message.setText(Html.fromHtml(e.getDescription()));
                        message.setMovementMethod(LinkMovementMethod.getInstance());
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle(name);
                        builder.setCancelable(true);
                        builder.setView(message);
                        builder.setIcon(R.drawable.ic_action_about);
                        builder.show();
                    }
                }
            }
        });
        mHolder.textContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.textCall.setVisibility(View.VISIBLE);
            }
        });
        mHolder.textCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate().Intents().callIntent(mContext, "999");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
    public void filterData(String query){
        ArrayList<Event> allEvents = new ArrayList<Event>();
        allEvents.addAll(dbHelper.getAllEvents());
        mEventList.clear();
        for(Event event : allEvents){
            if(event.getEvent_name().toLowerCase().contains(query.toLowerCase()))
                mEventList.add(event);
        }
        notifyDataSetChanged();
    }
}
