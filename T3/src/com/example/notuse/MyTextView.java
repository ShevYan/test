package com.example.notuse;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {
	private List<HashMap<String, String>> xmlObject;
	
	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public List<HashMap<String, String>> getXmlObject() {
		return xmlObject;
	}

	public void setXmlObject(List<HashMap<String, String>> xmlObject) {
		this.xmlObject = xmlObject;
	}

	private String getValue(String s) {
		return null;
	}


	@Override
	public void setText(CharSequence text, BufferType type) {
		// TODO Auto-generated method stub
		String orgText = String.format("%s", text);
		String name = orgText.replace("@xml:", "");
		
		super.setText("ABCDEDDDDDDDDDDDDDDDDDDDDDDDDDD", type);
	}
	
	
}
