package com.appex.tryproject.model.events;

public class RowItem {
    private String EventName;
    private String EventLocation;
    private String EventTime;
    private String EventDate;
    private String EventContact;
    private String EventCall;

    public RowItem(String EventName, String EventLocation, String EventTime, String EventDate, String EventContact,String EventCall) {
        this.EventName = EventName;
        this.EventLocation = EventLocation;
        this.EventTime = EventTime;
        this.EventDate = EventDate;
        this.EventContact = EventContact;
        this.EventCall=EventCall;
    }
    public String getEventCall() {
        return EventCall;
    }

    public void setEventCall(String eventCall) {
        EventCall = eventCall;
    }


    public void setEventContact(String eventContact) {
        EventContact = eventContact;
    }

    public void setEventDate(String eventDate) {

        EventDate = eventDate;
    }

    public void setEventTime(String eventTime) {

        EventTime = eventTime;
    }

    public void setEventLocation(String eventLocation) {

        EventLocation = eventLocation;
    }

    public void setEventName(String eventName) {

        EventName = eventName;
    }

    public String getEventContact() {

        return EventContact;
    }

    public String getEventDate() {

        return EventDate;
    }

    public String getEventTime() {

        return EventTime;
    }

    public String getEventLocation() {

        return EventLocation;
    }

    public String getEventName() {

        return EventName;
    }
}