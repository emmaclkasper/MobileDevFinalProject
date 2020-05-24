package edu.uga.cs.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PreviousCosts implements Parcelable {
    ArrayList<Roomates> roomates;
    String id;
    String date;

    public PreviousCosts(){}
    public PreviousCosts(ArrayList<Roomates> roomates, String id) {
        this.id = id;
        this.roomates = roomates;
    }

    protected PreviousCosts(Parcel in) {
        id = in.readString();
        date = in.readString();
    }

    public static final Creator<PreviousCosts> CREATOR = new Creator<PreviousCosts>() {
        @Override
        public PreviousCosts createFromParcel(Parcel in) {
            return new PreviousCosts(in);
        }

        @Override
        public PreviousCosts[] newArray(int size) {
            return new PreviousCosts[size];
        }
    };

    public ArrayList<Roomates> getRoomates() {
        return roomates;
    }

    public void setRoomates(ArrayList<Roomates> roomates) {
        this.roomates = roomates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        dest.writeString(id);
        dest.writeString(date);
    }
}
