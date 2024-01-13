package com.example.tprom.properties;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.errorprone.annotations.Var;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Member implements Parcelable {
    private String name;
    private String role;
    private int avatar;
    private boolean isSelected;
    private int complete;
    private List<String> file;

    protected Member(Parcel in) {
        name = in.readString();
        complete = in.readInt();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("complete", complete );
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
        dest.writeInt(complete);
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

    public Member(String name,int complete, boolean isSelected) {
        this.name = name;
        this.complete = complete;
        this.isSelected = isSelected;
    }

    public Member(String name,int complete, boolean isSelected, List<String> file) {
        this.name = name;
        this.complete = complete;
        this.isSelected = isSelected;
        this.file = file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }

    public List<String> getFile() {
        return file;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getComplete() {
        return complete;
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
