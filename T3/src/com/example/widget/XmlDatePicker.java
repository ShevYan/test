package com.example.widget;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.dom4j.Element;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.t3.MUtil;
import com.example.t3.R;
import com.example.t3.XmlActivity;

public class XmlDatePicker extends TextView {
	private XmlActivity activity;
	private String path;
	private int index;
	Element element;
	int year; 
	int month; // 1-12
	int dayOfMonth; //1-31
	
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
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		MUtil.ASSERT(1900 < year && year < 2100);
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		MUtil.ASSERT(1 <= month && month <= 12);
		this.month = month;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) {
		MUtil.ASSERT(1 <= dayOfMonth && dayOfMonth <= 31);
		this.dayOfMonth = dayOfMonth;
	}

	public Element fetchElement() {
		MUtil.ASSERT(null != path);
		List nodes = activity.getDoc().selectNodes(path);
		int count = nodes.size();
		MUtil.ASSERT(count > 0 && count > index);
		element = (Element)nodes.get(index);
		MUtil.ASSERT(null != element);
		
		StringTokenizer token = new StringTokenizer(element.getText(), "-");
	
		// year
		MUtil.ASSERT(token.hasMoreTokens());
		setYear(Integer.parseInt(token.nextToken()));
		
		// month
		MUtil.ASSERT(token.hasMoreTokens());
		setMonth(Integer.parseInt(token.nextToken()));
		
		// day
		MUtil.ASSERT(token.hasMoreTokens());
		setDayOfMonth(Integer.parseInt(token.nextToken()));
		
		return element;
	}
	
	public void update() {
		String str = String.format("%d-%02d-%02d", getYear(), getMonth(), getDayOfMonth());
		setText(str);
		element.setText(str);
	}

	public XmlDatePicker(Context context, AttributeSet attrs) {
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
				DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int year,
							int month, int dayOfMonth) {
						setYear(year);
						setMonth(month + 1);
						setDayOfMonth(dayOfMonth);
						update();
						activity.setbModified(true);
					}
				};

				Calendar calendar = Calendar.getInstance();
				calendar.set(getYear(), getMonth()-1, getDayOfMonth());
				DatePickerDialog dialog = new DatePickerDialog(
						activity, dateListener, 
						calendar.get(Calendar.YEAR), 
						calendar.get(Calendar.MONTH), 
						calendar.get(Calendar.DAY_OF_MONTH));
				
				dialog.show();
			}
		});
	}
}
