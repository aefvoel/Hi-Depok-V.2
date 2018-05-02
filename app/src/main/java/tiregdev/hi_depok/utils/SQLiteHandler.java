package tiregdev.hi_depok.utils;

/**
 * Created by SONY-VAIO on 10/5/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id_user";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_NO_TELP = "no_telp";
    private static final String KEY_TANGGAL_LAHIR = "tanggal_lahir";
    private static final String KEY_BIO = "bio";
    private static final String KEY_FOTO_USER = "foto";
    private static final String KEY_JENIS_KELAMIN = "jenis_kelamin";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";

    private static final String TABLE_ROOM = "chatroom";
    private static final String ID_ROOM = "chat_room_id";
    private static final String NAME_ROOM = "name";
    private static final String TGL_ROOM = "created_at";

    private static final String TABLE_MESSAGE = "messages";
    private static final String ID_MESSAGE = "message_id";
    private static final String FK_ID_ROOM = "chat_room_id";
    private static final String FK_ID_USER = "user_id";
    private static final String MESSAGE = "message";
    private static final String TGL_MESSAGE = "created_at";
    private static final String ANALYSIS = "analysis";

    private static final String TABLE_BROADCAST = "broadcasts";
    private static final String ID_BROADCAST = "id";
    private static final String MESSAGE_BROADCAST = "message";
    private static final String TGL_BRROADCAST = "created_at";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_ALAMAT + " TEXT," + KEY_NO_TELP + " TEXT,"
                + KEY_TANGGAL_LAHIR + " TEXT," + KEY_BIO + " TEXT,"
                + KEY_FOTO_USER + " TEXT," + KEY_JENIS_KELAMIN + " TEXT,"
                + KEY_CREATED_AT + " TEXT," + KEY_UPDATED_AT + " TEXT" + ")";

        String CREATE_ROOM_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ROOM + "("
                + ID_ROOM + " INTEGER PRIMARY KEY," + NAME_ROOM + " TEXT,"
                + TGL_ROOM + " TEXT" + ")";

        String CREATE_ROOM_MESSAGE = "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGE + "("
                + ID_MESSAGE + " INTEGER PRIMARY KEY," + FK_ID_ROOM + " TEXT,"
                + FK_ID_USER + " TEXT," + MESSAGE + " TEXT," + TGL_MESSAGE+ " TEXT,"
                + ANALYSIS + " TEXT" + ")";

        String CREATE_BROADCAST = "CREATE TABLE IF NOT EXISTS " + TABLE_BROADCAST + "("
                + ID_BROADCAST + " INTEGER PRIMARY KEY," + MESSAGE_BROADCAST + " TEXT,"
                + TGL_BRROADCAST + " TEXT" + ")";

        db.execSQL(CREATE_ROOM_TABLE);

        db.execSQL(CREATE_ROOM_MESSAGE);

        db.execSQL(CREATE_LOGIN_TABLE);

        db.execSQL(CREATE_BROADCAST);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String alamat, String no_telp,
                        String tanggal_lahir, String bio, String foto_user, String jenis_kelamin,
                        String created_at, String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid);
        values.put(KEY_ALAMAT, alamat); // Created At
        values.put(KEY_NO_TELP, no_telp);
        values.put(KEY_TANGGAL_LAHIR, tanggal_lahir);
        values.put(KEY_BIO, bio);
        values.put(KEY_FOTO_USER, foto_user);
        values.put(KEY_JENIS_KELAMIN, jenis_kelamin);
        values.put(KEY_CREATED_AT, created_at);
        values.put(KEY_UPDATED_AT, updated_at);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void insertBroadcast(String message, String created){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MESSAGE_BROADCAST, message);
        values.put(TGL_BRROADCAST, created);

        long id = db.insert(TABLE_BROADCAST, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "New Broadcast inserted into sqlite: " + id);

    }

    public void insertRoom(String id_room, String name, String created){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_ROOM, id_room);
        values.put(NAME_ROOM, name);
        values.put(TGL_ROOM, created);

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ROOM + " WHERE " +  ID_ROOM
                + " = '"+ id_room +"'", null);
        if(c.moveToFirst()) {
            db.close(); // Closing database connection
        }
        else {
            // Inserting Row
            long id = db.insert(TABLE_ROOM, null, values);
            db.close(); // Closing database connection
            Log.d(TAG, "New Room inserted into sqlite: " + id);
        }


    }

    public void insertMessage(String id_message, String id_room, String id_user, String message,
                              String created, String analysis){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_MESSAGE, id_message);
        values.put(FK_ID_ROOM, id_room);
        values.put(FK_ID_USER, id_user);
        values.put(MESSAGE, message);
        values.put(TGL_MESSAGE, created);
        values.put(ANALYSIS, analysis);

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MESSAGE + " WHERE " +  ID_MESSAGE
                + " = '"+ id_message +"'", null);
        if(c.moveToFirst()) {
            db.close(); // Closing database connection
        }
        else {
            // Inserting Row
            long id = db.insert(TABLE_MESSAGE, null, values);
            db.close(); // Closing database connection
            Log.d(TAG, "New Message inserted into sqlite: " + id);
        }

    }

    public void updateUser(String name, String email, String uid, String alamat, String no_telp,
                           String tanggal_lahir, String bio, String foto_user, String jenis_kelamin,
                           String created_at, String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid);
        values.put(KEY_ALAMAT, alamat); // Created At
        values.put(KEY_NO_TELP, no_telp);
        values.put(KEY_TANGGAL_LAHIR, tanggal_lahir);
        values.put(KEY_BIO, bio);
        values.put(KEY_FOTO_USER, foto_user);
        values.put(KEY_JENIS_KELAMIN, jenis_kelamin);
        values.put(KEY_CREATED_AT, created_at);
        values.put(KEY_UPDATED_AT, updated_at);

        // Inserting Row
        long id = db.update(TABLE_USER, values, KEY_UID + " = ? ", new String[] { String.valueOf(uid) });
        db.close(); // Closing database connection

        Log.d(TAG, "user edited into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("alamat", cursor.getString(4));
            user.put("no_telp", cursor.getString(5));
            user.put("tanggal_lahir", cursor.getString(6));
            user.put("bio", cursor.getString(7));
            user.put("foto", cursor.getString(8));
            user.put("jenis_kelamin", cursor.getString(9));
            user.put("created_at", cursor.getString(10));
            user.put("updated_at", cursor.getString(11));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public HashMap<String, ArrayList<String>> getUserActiveInRoom(String id_user) {
        HashMap<String, ArrayList<String>> stats = new HashMap<>();

        ArrayList<String> roomName = new ArrayList<>();
        ArrayList<String> totalPost = new ArrayList<>();
        String query = "SELECT a." + ID_ROOM + ", a." + NAME_ROOM + ", COUNT(b." + FK_ID_USER
                +") AS total_post FROM " + TABLE_ROOM + " a JOIN " + TABLE_MESSAGE + " b ON a."
                + ID_ROOM + " = b." + FK_ID_ROOM + " WHERE " + FK_ID_USER + " = '" + id_user
                + "' GROUP BY a." + ID_ROOM + ", a." + NAME_ROOM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String rName = c.getString(c.getColumnIndex("name"));
                    String tPost = c.getString(c.getColumnIndex("total_post"));
                    roomName.add(rName);
                    totalPost.add(tPost);
                }while (c.moveToNext());
            }
        }
        c.close();
        db.close();

        stats.put("room_name", roomName);
        stats.put("total_post", totalPost);

        return stats;
    }

    public HashMap<String, ArrayList<String>> getSentimentPostByUser(String id_user) {
        HashMap<String, ArrayList<String>> stats = new HashMap<>();

        ArrayList<String> sentimentName = new ArrayList<>();
        ArrayList<String> totalPost = new ArrayList<>();
        String query = "SELECT " + ANALYSIS + ", COUNT(" + FK_ID_USER
                +") AS total_post FROM " + TABLE_MESSAGE +  " WHERE " + FK_ID_USER + " = '" + id_user
                + "' GROUP BY " + ANALYSIS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String rName = c.getString(c.getColumnIndex("analysis"));
                    String tPost = c.getString(c.getColumnIndex("total_post"));
                    sentimentName.add(rName);
                    totalPost.add(tPost);
                }while (c.moveToNext());
            }
        }
        c.close();
        db.close();

        stats.put("sentiment_name", sentimentName);
        stats.put("total_post", totalPost);

        return stats;
    }

    public HashMap<String, ArrayList<String>> getBroadcastMessages() {
        HashMap<String, ArrayList<String>> stats = new HashMap<>();

        ArrayList<String> idBroadcast = new ArrayList<>();
        ArrayList<String> messageBroadcast = new ArrayList<>();
        ArrayList<String> tglBroadcast = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MESSAGE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String rId = c.getString(c.getColumnIndex("id"));
                    String rName = c.getString(c.getColumnIndex("message"));
                    String tPost = c.getString(c.getColumnIndex("created_at"));
                    idBroadcast.add(rId);
                    messageBroadcast.add(rName);
                    tglBroadcast.add(tPost);
                }while (c.moveToNext());
            }
        }
        c.close();
        db.close();

        stats.put("id", idBroadcast);
        stats.put("message", messageBroadcast);
        stats.put("created_at", tglBroadcast);

        return stats;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.delete(TABLE_BROADCAST, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
