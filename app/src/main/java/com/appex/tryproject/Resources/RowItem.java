package com.appex.tryproject.Resources;

public class RowItem {
    private String EventName;
    private String EventLocation;
    private String EventTime;
    private String EventDate;
    private String EventContact;
    public RowItem(String EventName, String EventLocation, String EventTime, String EventDate, String EventContact) {
        this.EventName=EventName;
        this.EventLocation=EventLocation;
        this.EventTime=EventTime;
        this.EventDate=EventDate;
        this.EventContact=EventContact;
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