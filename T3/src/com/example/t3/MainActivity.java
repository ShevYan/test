package com.example.t3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;



public class MainActivity extends XmlActivity {

	/*private String xml = Environment.getDataDirectory().getAbsolutePath()
			+ "/data/com.example.t3/temp.xml";*/
	private String xml = "/sdcard/temp.xml";
	
	public void genTestXml(String xml) {
/*		File file = new File(xml);
		if (file.exists()) {
			return;
		}*/
		AutoCompleteTextView v;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("root");
		root.addElement("head").setText("仁寿县人民医院");
		root.addElement("sub_head").setText("住院志");
		root.addElement("科室").setText("心内科");
		root.addElement("病区").setText("心内科");
		root.addElement("床号").setText("1");
		root.addElement("住院号").setText("0001");
		
		root.addElement("tag1").setText("点击可修改");
		root.addElement("tag2").setText("点击可修改");
		root.addElement("tag3").setText("不可修改");
		
		root.addElement("spinner").setText("广州");
		root.addElement("image_path").setText("/sdcard/8.jpg");
		root.addElement("date").setText("2014-05-23");
		root.addElement("time").setText("10:30:00");
		
		root.addElement("list").setText("可编辑区域");
		root.addElement("list").setText("可编辑区域");
		root.addElement("list").setText("可编辑区域");
		root.addElement("list").setText("可编辑区域");
		root.addElement("list").setText("可编辑区域");
		root.addElement("list").setText("可编辑区域");
		root.addElement("list").setText("可编辑区域");
		root.addElement("list").setText("可编辑区域");
		root.addElement("list").setText("可编辑区域");
		root.addElement("list2").setText("可编辑区域");
		root.addElement("list2").setText("可编辑区域");
		root.addElement("list2").setText("可编辑区域");
		root.addElement("list2").setText("可编辑区域");
		root.addElement("list2").setText("可编辑区域");
		root.addElement("list2").setText("可编辑区域");
		root.addElement("list2").setText("可编辑区域");
		root.addElement("list2").setText("可编辑区域");
		root.addElement("list2").setText("可编辑区域");

		org.dom4j.io.OutputFormat outputFormat = new org.dom4j.io.OutputFormat(); // 设置xml输出格式
		outputFormat.setEncoding("utf-8");
		outputFormat.setIndent(false);
		outputFormat.setNewlines(true);
		outputFormat.setTrimText(true);

		StringWriter xmlWriter = new StringWriter();
		org.dom4j.io.XMLWriter output = new XMLWriter(xmlWriter, outputFormat); // 保存xml
		try {
			output.write(document);
			output.close();

			FileOutputStream out = new FileOutputStream(xml);
			out.write(xmlWriter.toString().getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		genTestXml(xml);
		setXml(xml);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.save();
				
				//移动焦点
                v.requestFocus();
                v.requestFocusFromTouch();
                v.clearFocus();
			}
		});
		
	/*	Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
		spinner1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//setText(mItems[position]);
			}
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

