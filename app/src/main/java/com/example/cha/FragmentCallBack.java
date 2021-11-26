package com.example.cha;

import android.graphics.Bitmap;

public interface FragmentCallBack {
    void callNextFrag(String fragments);
    void getTextView(String title);
    void setResultData(String result, Bitmap resImg, boolean imgF);
    void hideActionBar(boolean flag);
    void pop();
    void setRoom(String r);
    void setFloorRooms(String floor);
    void setProfileData(String[] data);
}
