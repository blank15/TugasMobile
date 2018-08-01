package com.piusanggoro.catetanku.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.piusanggoro.catetanku.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.piusanggoro.catetanku.db.DatabaseContract.getColumnInt;
import static com.piusanggoro.catetanku.db.DatabaseContract.getColumnString;

public class Catetan implements Parcelable {
    private int id;
    private String title;
    private String description;
    private String date;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
    }

    public Catetan() {

    }

    public Catetan(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.NoteColumns.TITLE);
        this.description = getColumnString(cursor, DatabaseContract.NoteColumns.DESCRIPTION);
        this.date = getColumnString(cursor, DatabaseContract.NoteColumns.DATE);
    }

    protected Catetan(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Catetan> CREATOR = new Parcelable.Creator<Catetan>() {
        @Override
        public Catetan createFromParcel(Parcel source) {
            return new Catetan(source);
        }

        @Override
        public Catetan[] newArray(int size) {
            return new Catetan[size];
        }
    };
}
