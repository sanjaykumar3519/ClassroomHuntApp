package com.example.cha;

import android.graphics.Bitmap;

public interface FragmentCallBack {
    void callNextFrag(String fragments);
    void getTextView(String title);
    void setResultData(String result, Bitmap resImg);
    void hideActionBar(boolean flag);
    void exception(String e);
    void pop();
    void setRoom(String r);
    void setFloorRooms(String floor);
    void setProfileData(String[] data);
}
