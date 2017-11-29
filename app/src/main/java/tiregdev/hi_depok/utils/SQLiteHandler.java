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
        db.execSQL(CREATE_LOGIN_TABLE);

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

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
