package com.example.fragtest;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Fragment2 extends Fragment {
	private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;

    private DBMgr mDbHelper;
	private ListView mlv;
	private LinearLayout mll;
	private View mv;
	private CheckBox mcb;
	private ListActivity la;
	private TextView mTv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mDbHelper = new DBMgr(getActivity());
        mDbHelper.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mv = inflater.inflate(R.layout.task_list, container, false);
		mll = (LinearLayout) mv.findViewById(R.id.tab02);
		mlv = (ListView) mv.findViewById(android.R.id.list);
		mlv.setAdapter(fillData());
		mlv.setClickable(true);
		
		mlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent i = new Intent(getActivity(), TaskEdit.class);
				i.putExtra(DBMgr.KEY_ROWID, id);
				mTv = (TextView)mv.findViewById(R.id.text2);
				mTv.setTextColor(getResources().getColor(R.color.red));
				startActivity(i);
			}
			
		});
		registerForContextMenu(mlv);
		return mv;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
        case DELETE_ID:
            AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            mDbHelper.deleteTask(info.id);
            mlv.setAdapter(fillData());
            return true;
		}
		return super.onContextItemSelected(item);
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
        case INSERT_ID:
            createTask();
            return true;
		}
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	private SimpleCursorAdapter fillData() {
        Cursor tasksCursor = mDbHelper.fetchAllTasks();
        getActivity().startManagingCursor(tasksCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{DBMgr.TASK_TITLE};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text2};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter tasks = 
            new SimpleCursorAdapter(getActivity(), R.layout.task_row, tasksCursor, from, to);
//        mll.setAdapter(notes);
        return tasks;
    }

/*
	private SimpleCursorAdapter fillCheckbox() {
        Cursor tasksCursor = mDbHelper.fetchAllTasks();
        getActivity().startManagingCursor(tasksCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{DBMgr.TASK_TITLE};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text2};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter tasks = 
            new SimpleCursorAdapter(getActivity(), R.layout.task_row, tasksCursor, from, to);
//        mll.setAdapter(notes);
        return tasks;
    }
*/
	
	private void createTask() {
        Intent i = new Intent(getActivity(), TaskEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mlv.setAdapter(fillData());
	}
}
