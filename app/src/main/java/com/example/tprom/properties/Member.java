package com.example.tprom.properties;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class Member implements Parcelable {
    private String name;
    private String role;
    private int avatar;
    private boolean isSelected;

    protected Member(Parcel in) {
        name = in.readString();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Member() {
        // Empty constructor required for Firebase
    }

    public Member(String name, String role, int avatar) {
        this.name = name;
        this.role = role;
        this.avatar = avatar;
    }

    public Member(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Member(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Member(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
