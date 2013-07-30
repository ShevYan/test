package com.example.t3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.widget.PagerView;

public class PagerViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pager_view);
		PagerView pagerView = (PagerView)findViewById(R.id.pager_view);
		TextView tv1 = new TextView(this);
		tv1.setText("RED");
		
		TextView tv2 = new TextView(this);
		tv2.setText("GREEN");
		
		TextView tv3 = new TextView(this);
		tv3.setText("BLUE");
		
		pagerView.addPage(tv1, getLayoutInflater().inflate(R.layout.red, null));
		pagerView.addPage(tv2, getLayoutInflater().inflate(R.layout.green, null));
		pagerView.addPage(tv3, getLayoutInflater().inflate(R.layout.blue, null));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pager_view, menu);
		return true;
	}

}

