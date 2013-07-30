package com.example.widget;

import java.util.List;

import org.dom4j.Element;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.t3.MUtil;
import com.example.t3.R;
import com.example.t3.XmlActivity;

public class XmlImageView extends ImageView {
	private XmlActivity activity;
	private String path;
	private int index = 0;
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
		MUtil.ASSERT(nodes.size() != 0);
		element = (Element)nodes.get(index);
		return element;
	}
	
	public void update() {
		Drawable drawable = Drawable.createFromPath(element.getText());
		this.setImageDrawable(drawable);
	}

	public XmlImageView(Context context, AttributeSet attrs) {
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
		update();
		
		a.recycle();
	}
}
