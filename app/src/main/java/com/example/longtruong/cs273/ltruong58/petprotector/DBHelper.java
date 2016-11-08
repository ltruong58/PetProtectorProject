package com.example.longtruong.cs273.ltruong58.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;

/**
 * Support SQLite on Pet database
 */
class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final String DATABASE_NAME = "PetProtector";
    static final String DATABASE_TABLE = "Pet";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_PHONE = "phone";
    private static final String FIELD_IMAGE_URI = "image_uri";

    /**
     * Constructor
     * @param context
     */
    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *
     * @param database
     */
    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_NAME + " TEXT, "
                + FIELD_DETAILS + " TEXT, "
                + FIELD_PHONE + " TEXT, "
                + FIELD_IMAGE_URI + " TEXT" + ")";
        database.execSQL (table);
    }

    /**
     *
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    /**
     *  Create a method to add a brand new pet to the database
     * @param newPet
     */
    public void askPet(Pet newPet)
    {
        // Step 1: Create a reference to our database
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 2: make a key-value pair for each value you want to insert
        ContentValues values = new ContentValues();
        //values.put(KEY_FIELD_ID, newPet.getId());
        values.put(FIELD_NAME, newPet.getName());
        values.put(FIELD_DETAILS, newPet.getDetails());
        values.put(FIELD_PHONE, newPet.getPhone());
        values.put(FIELD_IMAGE_URI, newPet.getImageURI().toString());

        // Step 3: Insert value into db
        db.insert(DATABASE_TABLE, null, values);

        db.close();
    }
    /**
     *  Create a method to get all the pets in the database
     */
    public ArrayList<Pet> getAllPets()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        // New empty arrayList
        ArrayList<Pet> newPet = new ArrayList<>();

        // Query all records
        // The return type of query is Cursor
       // Cursor results = db.query(DATABASE_TABLE, new String[] {KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_IS_DONE});
        Cursor results = db.query(DATABASE_TABLE, null, null, null, null, null, null);
        // Loop through the results , create Task object
        if(results.moveToFirst())
        {
            do{
                int id = results.getInt(0);
                String name = results.getString(1);
                String details = results.getString(2);
                String phone = results.getString(3);
                Uri imageURI = Uri.parse(results.getString(4));

                newPet.add(new Pet(id, name, details, phone, imageURI));

            } while (results.moveToNext());
        }
        db.close();
        return newPet;
    }

    /**
     * Delete all pets in database
      */
    public  void deleteAllPets()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }
}
