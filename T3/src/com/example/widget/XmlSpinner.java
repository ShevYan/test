package com.example.widget;

import java.util.List;

import org.dom4j.Element;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.t3.MUtil;
import com.example.t3.R;
import com.example.t3.XmlActivity;

public class XmlSpinner extends Spinner {
	private XmlActivity activity;
	private String xmlPath;
	private Element element;
	private String spinner_res;
	String[] mItems;
	
	public XmlSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		MUtil.ASSERT(context instanceof XmlActivity);
		activity = (XmlActivity)context;
		TypedArray a = context.obtainStyledAttributes(attrs,   
				R.styleable.xml);
		xmlPath = a.getString(R.styleable.xml_path);
		spinner_res = a.getString(R.styleable.xml_spinner_res);
		
		a.recycle();
		
		int resource = 0;
		try {
			resource = R.array.class.getField(spinner_res).getInt(new R.array());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mItems = getResources().getStringArray(resource);  
		// 建立Adapter并且绑定数据源   
		ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, mItems);  
		//绑定 Adapter到控件   
		this.setAdapter(_Adapter);
		
		// get element
		List list = activity.getDoc().selectNodes(xmlPath);
		MUtil.ASSERT(list.size() == 1);
		element = (Element)list.get(0);
		
		// set text
		setText(element.getText());

		this.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				setText(mItems[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		/*this.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//setText(mItems[position]);
			}
		});*/
	}
	
	public void setText(String text) {
		int i = 0;
		for (i=0; i<mItems.length; i++)
			if (text.equals(mItems[i])) {
				break;
		}
		MUtil.ASSERT(i != mItems.length);
		MUtil.ASSERT(element != null);
		this.setSelection(i);
		element.setText(mItems[i]);
		activity.setbModified(true);
	}
}
