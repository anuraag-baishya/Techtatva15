package chipset.techtatva.model.events;

/**
 * Created by saketh on 19/8/15.
 */
public class Event {
    private   int Event_id;
    private   String Event_name;
    private   String Description;
    private   int CatId;
    private   int EventMaxTeamNumber;


    public  void setDescription(String description) {
        Description = description;
    }

    public void setCatId(int catId) {
        CatId = catId;
    }

    public void setEvent_id(int event_id) {
        Event_id = event_id;
    }

    public void setEvent_name(String event_name) {
        Event_name = event_name;
    }

    public void setEventMaxTeamNumber(int eventMaxTeamNumber) {
        EventMaxTeamNumber = eventMaxTeamNumber;
    }

    public String getDescription() {
        return Description;
    }

    public int getCatId() {
        return CatId;
    }

    public int getEvent_id() {
        return Event_id;
    }

    public String getEvent_name() {
        return Event_name;
    }

    public int getEventMaxTeamNumber() {
        return EventMaxTeamNumber;
    }
}

