package com.example.cha;

import android.graphics.Bitmap;

public interface FragmentCallBack {
    public void callNextFrag(String fragments);
    public void getTextView(String title);
    public void setResultData(String result, Bitmap resImg);
    public void hideActionBar(boolean flag);
    public void exception(String e);
    public void pop();
    public void setRoom(String r);
    public void setFloorRooms(String floor);
    public void setProfileData(String[] data);
}
