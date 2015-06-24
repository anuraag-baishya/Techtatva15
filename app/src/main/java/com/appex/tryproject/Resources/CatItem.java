package com.appex.tryproject.Resources;


import java.util.ArrayList;

public class CatItem {
    private String Category;
    private ArrayList<RowItem> EventItem;

    public CatItem(String Category, ArrayList<RowItem> EventItem) {
        super();
        this.EventItem = EventItem;
        this.Category = Category;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public ArrayList<RowItem> getEventItem() {
        return EventItem;
    }

    public void setEventItem(ArrayList<RowItem> eventItem) {
        EventItem = eventItem;
    }
}
