package com.example.notuse;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

public class BaseActivity extends Activity {
	private String xml;
	
	
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
		refresh();
	}

	public void save() {
		
	}
	
	public void refresh() {
		
	}
}

