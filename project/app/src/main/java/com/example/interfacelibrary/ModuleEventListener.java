package com.example.interfacelibrary;

/**
 * Created by tony on 2018/5/10.
 */

public interface ModuleEventListener {
    int Event_Open_Middle_Node = 1;

    void onPageEvent(int eventId);
}
