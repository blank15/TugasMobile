package com.piusanggoro.catetanku.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.piusanggoro.catetanku.model.Catetan;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.piusanggoro.catetanku.db.DatabaseContract.NoteColumns.DATE;
import static com.piusanggoro.catetanku.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.piusanggoro.catetanku.db.DatabaseContract.NoteColumns.INFO;
import static com.piusanggoro.catetanku.db.DatabaseContract.NoteColumns.TITLE;
import static com.piusanggoro.catetanku.db.DatabaseContract.TABLE_NOTE;

public class NoteHelper {
    private static String DATABASE_TABLE = TABLE_NOTE;
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    public NoteHelper(Context context){
        this.context = context;
    }

    public NoteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Catetan> query(){
        ArrayList<Catetan> arrayList = new ArrayList<Catetan>();
        Cursor cursor = database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null,_ID +" DESC"
                ,null);
        cursor.moveToFirst();
        Catetan catetan;
        if (cursor.getCount()>0) {
            do {
                catetan = new Catetan();
                catetan.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                catetan.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                catetan.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                catetan.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                catetan.setInfo(cursor.getString(cursor.getColumnIndexOrThrow(INFO)));

                arrayList.add(catetan);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Catetan catetan){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(TITLE, catetan.getTitle());
        initialValues.put(DESCRIPTION, catetan.getDescription());
        initialValues.put(DATE, catetan.getDate());
        initialValues.put(INFO,catetan.getInfo());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Catetan catetan){
        ContentValues args = new ContentValues();
        args.put(TITLE, catetan.getTitle());
        args.put(DESCRIPTION, catetan.getDescription());
        args.put(DATE, catetan.getDate());
        args.put(INFO,catetan.getInfo());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + catetan.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLE_NOTE, _ID + " = '"+id+"'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
