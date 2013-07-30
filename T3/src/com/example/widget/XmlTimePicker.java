package com.example.widget;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.dom4j.Element;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.t3.MUtil;
import com.example.t3.R;
import com.example.t3.XmlActivity;

public class XmlTimePicker extends TextView {
	private XmlActivity activity;
	private String path;
	private int index;
	Element element;
	int hour; 
	int minute; // 1-12
	
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

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		MUtil.ASSERT(0 <= hour && hour <= 23);
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		MUtil.ASSERT(0 <= minute && minute <= 59);
		this.minute = minute;
	}

	public Element fetchElement() {
		MUtil.ASSERT(null != path);
		List nodes = activity.getDoc().selectNodes(path);
		int count = nodes.size();
		MUtil.ASSERT(count > 0 && count > index);
		element = (Element)nodes.get(index);
		MUtil.ASSERT(null != element);
		
		StringTokenizer token = new StringTokenizer(element.getText(), ":");
	
		// hour
		MUtil.ASSERT(token.hasMoreTokens());
		setHour(Integer.parseInt(token.nextToken()));
		
		// minute
		MUtil.ASSERT(token.hasMoreTokens());
		setMinute(Integer.parseInt(token.nextToken()));
		
		return element;
	}
	
	public void update() {
		String str = String.format("%02d:%02d:00", getHour(), getMinute());
		setText(str);
		element.setText(str);
	}

	public XmlTimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		MUtil.ASSERT(context instanceof XmlActivity);
		activity = (XmlActivity)context;
		TypedArray a = context.obtainStyledAttributes(attrs,   
				R.styleable.xml);
		path = a.getString(R.styleable.xml_path);
		a.recycle();
		
		String strIndex = a.getString(R.styleable.xml_index);
		if (null != strIndex && !strIndex.equals("")) {
			index = Integer.parseInt(strIndex);
		} else {
			index = 0;
		}
		fetchElement();
		update();
		
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker timerPicker,
							int hourOfDay, int minute) {
						setHour(hourOfDay);
						setMinute(minute);
						update();
						activity.setbModified(true);
					}
				};

				Calendar calendar = Calendar.getInstance();
				calendar.set(0, 0, 0, getHour(), getMinute());
				TimePickerDialog dialog = new TimePickerDialog(activity, timeListener, 
						calendar.get(Calendar.HOUR_OF_DAY), 
						calendar.get(Calendar.MINUTE), true);
				dialog.show();
			}
		});
	}
}
