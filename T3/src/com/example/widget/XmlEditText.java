package com.example.widget;

import java.util.List;

import org.dom4j.Element;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.example.t3.MUtil;
import com.example.t3.R;
import com.example.t3.XmlActivity;

public class XmlEditText extends EditText {
	private XmlActivity activity;
	private String path;
	private int index;
	Element element;
	
	public String getPath() {
		return path;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public Element fetchElement() {
		MUtil.ASSERT(null != path);
		List nodes = activity.getDoc().selectNodes(path);
		if (nodes.size() == 0) {
			setText(activity.DEFAULT_TEXT);
		} else {
			element = (Element)nodes.get(index);
			MUtil.ASSERT(null != element);
			setText(element.getText());
		}
		
		return element;
	}

	public XmlEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		MUtil.ASSERT(context instanceof XmlActivity);
		activity = (XmlActivity)context;
		TypedArray a = context.obtainStyledAttributes(attrs,   
				R.styleable.xml);
		path = a.getString(R.styleable.xml_path);
		String strIndex = a.getString(R.styleable.xml_index);
		if (null != strIndex && !strIndex.equals("")) {
			index = Integer.parseInt(strIndex);
		} else {
			index = 0;
		}
		
		fetchElement();
		a.recycle();
		
		this.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				System.out.println(v.getId() + (hasFocus ? "获得焦点" : "失去焦点"));
			}
		});
	}

	@Override
	protected void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {
		// TODO Auto-generated method stub
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		if (null != element) {
			element.setText(XmlEditText.this.getText().toString());
			activity.setbModified(true);
		}
	}
}
