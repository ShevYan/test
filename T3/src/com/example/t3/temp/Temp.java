package com.example.t3.temp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.t3.R;

public class Temp extends Activity {
	AutoCompleteTextView view;
	PagedAutoCompleteAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp);
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		for (int i=0; i<13; i++) {
			values.add(new String[] {"" + i, "" + i, "" + 1});
		}
		
		view = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
		adapter = new PagedAutoCompleteAdapter(this, values, 10) ;
		view.setAdapter(adapter);
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView tv = (TextView)view.findViewById(R.id.tv0);
				Temp.this.view.setText(tv.getText());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.temp, menu);
		return true;
	}

}
