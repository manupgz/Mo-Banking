package com.database.Mo_Banking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.definitions.Mo_Banking.Account;

import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION           = 1;
    private static final String DB_NAME           = "userDetailInformation";
    private static final String DB_TABLE_ACCOUNTS = "accounts";

    private static final String KEY_ID            = "id";
    private static final String KEY_ACC_NUMBER    = "account_number";
    private static final String KEY_ACC_NAME      = "account_name";
    private static final String KEY_ACC_INFO      = "account_info";
    private int id;

    public DatabaseHandler(Context context) {
        super(context,DB_TABLE_ACCOUNTS,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ACCOUNTS = "CREATE TABLE "+ DB_TABLE_ACCOUNTS + "{"
                +   KEY_ID + " INTEGER PRIMARY KEY," + KEY_ACC_NAME + " TEXT,"
                +   KEY_ACC_NUMBER + " TEXT" + "}";
        db.execSQL(CREATE_TABLE_ACCOUNTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE_ACCOUNTS);
        onCreate(db);
    }

    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ACC_NAME,account.getAcc_name());
        values.put(KEY_ACC_NUMBER,account.getAcc_number());
        values.put(KEY_ACC_INFO,account.getAcc_info());

        db.insert(DB_TABLE_ACCOUNTS,null,values);
        db.close();
    }
    public Account getAccount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DB_TABLE_ACCOUNTS,
                new String[] { KEY_ID,KEY_ACC_NAME,KEY_ACC_NUMBER,KEY_ACC_INFO },KEY_ID + "=?",
                new String[] {String.valueOf(id)},null,null,null,null);
        if( cursor!=null )
            cursor.moveToFirst();

        Account account = new Account(cursor.getString(1),cursor.getString(2),cursor.getString(3));

        return account;
    }

    public HashMap<Integer, Account> getAccounts() {
        HashMap<Integer,Account> accounts = new HashMap<Integer, Account>();

        String selectQuery = "SELECT  * FROM " + DB_TABLE_ACCOUNTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.setAcc_name(cursor.getString(1));
                account.setAcc_number(cursor.getString(2));
                account.setAcc_info(cursor.getString(3));
                // Adding contact to list
                accounts.put(cursor.getInt(0),account);
            } while (cursor.moveToNext());
        }
        return accounts;
    }
}
