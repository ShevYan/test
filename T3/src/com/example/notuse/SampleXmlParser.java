package com.example.notuse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class SampleXmlParser {
	public static List<XmlItemCtrl> parse(String xmlPath) throws XmlPullParserException, IOException {
		List<XmlItemCtrl> ctrls = null;
		XmlItemCtrl ctrl = null;
		FileInputStream in = new FileInputStream(xmlPath);
	
		XmlPullParser parser = Xml.newPullParser(); 
		parser.setInput(in, "UTF-8");  
		int eventType = parser.getEventType();  
        while (eventType != XmlPullParser.END_DOCUMENT) {  
            switch (eventType) {  
            case XmlPullParser.START_DOCUMENT:  
            	ctrls = new ArrayList<XmlItemCtrl>();  
                break;  
            case XmlPullParser.START_TAG:  
                if (parser.getName().equals("ctrl")) {  
                	ctrl = new XmlItemCtrl();  
                } else if (parser.getName().equals("id")) {  
                    eventType = parser.next();  
                    ctrl.setId(Integer.parseInt(parser.getText()));  
                } else if (parser.getName().equals("text")) {  
                    eventType = parser.next();  
                    ctrl.setText(parser.getText());  
                } else if (parser.getName().equals("editable")) {  
                    eventType = parser.next();  
                    ctrl.setEditable(parser.getText().equalsIgnoreCase("yes"));  
                }  
                break;  
            case XmlPullParser.END_TAG:  
                if (parser.getName().equals("ctrl")) {  
                	ctrls.add(ctrl);  
                	ctrl = null;      
                }  
                break;  
            }  
            eventType = parser.next();  
        }  
        return ctrls;  
    
	}
	
	public static String serialize(List<XmlItemCtrl> ctrls) throws IllegalArgumentException, IllegalStateException, IOException {
		XmlSerializer serializer = Xml.newSerializer(); //由android.util.Xml创建一个XmlSerializer实例   
        StringWriter writer = new StringWriter();  
        serializer.setOutput(writer);   //设置输出方向为writer   
        serializer.startDocument("UTF-8", true);  
        serializer.startTag("", "ctrls");  
        for (XmlItemCtrl ctrl : ctrls) {  
            serializer.startTag("", "ctrl");
            
            serializer.startTag("", "id");  
            serializer.text(ctrl.getId() + "");  
            serializer.endTag("", "id");  
              
            serializer.startTag("", "text");  
            serializer.text(ctrl.getText());  
            serializer.endTag("", "text");  
              
            serializer.startTag("", "editable");  
            serializer.text(ctrl.isEditable() ? "yes" : "no");  
            serializer.endTag("", "editable");  
              
            serializer.endTag("", "ctrl");  
        }  
        serializer.endTag("", "ctrls");  
        serializer.endDocument();  
          
        return writer.toString();  
	}
	
	public static void dumpXmlToFile(List<XmlItemCtrl> ctrls, String xmlPath) throws IllegalArgumentException, IllegalStateException, IOException {
		String str = serialize(ctrls);
		Log.d("XmlView", str);
		FileOutputStream out = new FileOutputStream(xmlPath);
		out.write(str.getBytes());
		out.close();
	}
}
