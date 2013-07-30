package com.example.widget;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class XmlDatePicker_old extends TextView {
	Context context;
	
	public XmlDatePicker_old(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;  
		this.setText("点击选择日期");
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog.OnDateSetListener dateListener =  
					    new DatePickerDialog.OnDateSetListener() { 
					        @Override 
					        public void onDateSet(DatePicker datePicker,  
					                int year, int month, int dayOfMonth) { 
					            System.out.println(year + "-" + month + "-" + dayOfMonth);
					        } 
					    }; 

					    Calendar calendar = Calendar.getInstance();
	    
				DatePickerDialog dialog = new DatePickerDialog(
						XmlDatePicker_old.this.context,
                        dateListener, 
                        calendar.get(Calendar.YEAR), 
                        calendar.get(Calendar.MONTH), 
                        calendar.get(Calendar.DAY_OF_MONTH)); 
				dialog.show();
			}
		});
	}

}
