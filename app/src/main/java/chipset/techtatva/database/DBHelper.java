package chipset.techtatva.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import chipset.techtatva.model.events.Category;
import chipset.techtatva.model.events.Event;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TTEvents.db";
    public static final String CATEGORIES_TABLE_NAME="categories";
    public static final String EVENTS_TABLE_NAME="events";
    public static final String COLUMN_ID="id";
    public static final String CATEGORY_ID="cid";
    public static final String EVENT_ID="eid";
    public static final String DESCRIPTION="description";
    public static final String NAME="name";
    public static final String EVENT_MAX_NO="maxno";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF  NOT EXISTS "+CATEGORIES_TABLE_NAME+" ("+COLUMN_ID+" INTEGER PRIMARY KEY,"+CATEGORY_ID
        +" INTEGER,"+NAME+" TEXT,"+DESCRIPTION+" TEXT);");
        db.execSQL("CREATE TABLE IF  NOT EXISTS " + EVENTS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY," + EVENT_ID
                + " INTEGER," + NAME + " TEXT," + DESCRIPTION + " TEXT," + EVENT_MAX_NO + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ CATEGORIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ EVENTS_TABLE_NAME);
            onCreate(db);
    }
    public void insertCategory(Category category){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_ID,category.getCatId());
        contentValues.put(NAME, category.getCatName());
        contentValues.put(DESCRIPTION, category.getDescription());
        db.insert(CATEGORIES_TABLE_NAME, null, contentValues);
    }
    public void insertEvent(Event event){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_ID,event.getCatId());
        contentValues.put(NAME, event.getEvent_name());
        contentValues.put(DESCRIPTION, event.getDescription());
        contentValues.put(EVENT_MAX_NO,event.getEventMaxTeamNumber());
        db.insert(EVENTS_TABLE_NAME, null, contentValues);
    }
    public void dropTables(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EVENTS_TABLE_NAME);
        db.execSQL("CREATE TABLE IF  NOT EXISTS " + CATEGORIES_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY," + CATEGORY_ID
                + " INTEGER," + NAME + " TEXT," + DESCRIPTION + " TEXT);");
        db.execSQL("CREATE TABLE IF  NOT EXISTS " + EVENTS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY," + EVENT_ID
                + " INTEGER," + NAME + " TEXT," + DESCRIPTION + " TEXT," + EVENT_MAX_NO + " INTEGER);");
    }
    public void deleteCategories(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE * FROM "+CATEGORIES_TABLE_NAME);
    }
    public void deleteEvents(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE * FROM "+EVENTS_TABLE_NAME);
    }
    public ArrayList<Category> getAllCategories(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rows =db.rawQuery("SELECT * FROM " + CATEGORIES_TABLE_NAME, null);
        ArrayList<Category> categories = new ArrayList<Category>();
        if(rows!=null) {
            for (int i = 0; i < rows.getCount(); i++) {
                rows.moveToPosition(i);
                Category category = new Category();
                category.setCatId(rows.getInt(1));
                category.setCatName(rows.getString(2));
                category.setDescription(rows.getString(3));
                categories.add(category);
            }
        }
        return categories;
    }
    public ArrayList<Event> getAllEvents(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rows =db.rawQuery("SELECT * FROM " + EVENTS_TABLE_NAME, null);
        ArrayList<Event> events = new ArrayList<Event>();
        if(rows!=null) {
            for (int i = 0; i < rows.getCount(); i++) {
                rows.moveToPosition(i);
                Event event = new Event();
                event.setCatId(rows.getInt(1));
                event.setEvent_name(rows.getString(2));
                event.setDescription(rows.getString(3));
                event.setEventMaxTeamNumber(rows.getInt(4));
                events.add(event);
            }
        }
        return events;
    }
}

