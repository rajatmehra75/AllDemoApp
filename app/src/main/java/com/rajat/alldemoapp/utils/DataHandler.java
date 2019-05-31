package com.rajat.alldemoapp.utils;

import android.content.Intent;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by ist on 31/5/19.
 */

public class DataHandler {
    private static DataHandler mDataHandler;
    private Deque<Intent> stack = new ArrayDeque<>();

    private DataHandler() {
    }

    public static DataHandler getInstance(){
        if(mDataHandler == null){
            mDataHandler = new DataHandler();
        }
        return mDataHandler;
    }

    public Deque<Intent> getStack() {
        return stack;
    }

    public void setStack(Deque<Intent> stack) {
        this.stack = stack;
    }
}
