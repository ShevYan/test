package com.example.widget;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.t3.MUtil;
import com.example.t3.R;
import com.example.t3.XmlActivity;

public class XmlListView extends ListView {
	private XmlActivity activity;
	private String xmlPath;
	
	public static class XmlAdapter extends BaseAdapter {
		private String xmlPath;
		private int mResource;
		private LayoutInflater mInflater;
		private List<Element> elements;
		
		public XmlAdapter(Context context, String xmlPath, int resource) {
			super();
			this.xmlPath = xmlPath;
			this.mResource = resource;
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			XmlActivity activity = (XmlActivity)context;
			List list = activity.getDoc().selectNodes(xmlPath);
			elements = new LinkedList<Element>(list);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return elements.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return elements.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = null;
			if (convertView == null) {
				view = mInflater.inflate(mResource, parent, false);
	        } else {
	        	view = convertView;
	        }
	        
			//设置子控件的xml对应
			List<View> subViews = getSubViews(view);
			for (View v : subViews) {
				if (v instanceof XmlTextView) {
					XmlTextView xtv = (XmlTextView)v;
					xtv.setIndex(position);
					xtv.fetchElement();
				} else if (v instanceof XmlEditText) {
					XmlEditText xet = (XmlEditText)v;
					xet.setIndex(position);
					xet.fetchElement();
				}
			}
			return view;
		}
		
		List<View> getSubViews(View view) {
			ArrayList<View> views = new ArrayList<View>();
			LinkedList<View> stack = new LinkedList<View>();
			stack.add(view);
			while (stack.size() > 0) {
				View v = stack.removeFirst();
				views.add(v);
				if (v instanceof ViewGroup) {
					ViewGroup vg = (ViewGroup)v;
					int childCount = vg.getChildCount();
					for (int i=0; i<childCount; i++) {
						v = vg.getChildAt(i);
						stack.addLast(v);
					}
				}
			}
			
			return views;
		}
	}
	
	public XmlListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		MUtil.ASSERT(context instanceof XmlActivity);
		activity = (XmlActivity)context;
		TypedArray a = context.obtainStyledAttributes(attrs,   
				R.styleable.xml);
		xmlPath = a.getString(R.styleable.xml_path);
		String list_item = a.getString(R.styleable.xml_list_item);
		a.recycle();
		
		int resource = 0;
		try {
			resource = R.layout.class.getField(list_item).getInt(new R.layout());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XmlAdapter adapter = new XmlAdapter(context, xmlPath, resource); 
		this.setAdapter(adapter);
	}


}
