package com.example.fragtest;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class mySCAdapter extends SimpleCursorAdapter{
	
	public mySCAdapter( Context context, int layout, Cursor c, String[] from, int[] to){
		super(context, layout, c, from, to);
	}
	
	
}
