package com.example.t3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class XmlActivity extends Activity {
	public static String BUNDLE_XML_KEY = "BUNDLE_XML_KEY";
	public static String DEFAULT_TEXT = "无可对应数据";
	private String xml;
	private Document doc;
	private boolean bModified;

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
		doc = load(xml);
	}

	public Document getDoc() {
		MUtil.ASSERT(null != doc);
		return doc;
	}

	public boolean isbModified() {
		return bModified;
	}

	public void setbModified(boolean bModified) {
		this.bModified = bModified;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (null != bundle) {
			xml = bundle.getString(BUNDLE_XML_KEY);
			if (null != xml && !xml.equals("")) {
				doc = load(xml);
			}
		}
	}
	
	private Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
	
	public String save() {
		org.dom4j.io.OutputFormat outputFormat = new org.dom4j.io.OutputFormat();   // 设置xml输出格式   
        outputFormat.setEncoding("utf-8");  
        outputFormat.setIndent(false);  
        outputFormat.setNewlines(true);  
        outputFormat.setTrimText(true);  
          
        StringWriter xmlWriter = new StringWriter();
        org.dom4j.io.XMLWriter output = new XMLWriter(xmlWriter, outputFormat);     // 保存xml   
        try {
			output.write(doc);
			output.close();
			
			File file = new File(xml);
			file.delete();
			FileOutputStream out=new FileOutputStream(file);
			
			byte[] bytes = xmlWriter.toString().getBytes();
			out.write(bytes);
			out.flush();
			out.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println(xmlWriter.toString());
        return xmlWriter.toString();
	}
	
	
}
