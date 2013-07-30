package com.example.notuse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmlObject {
	private List<XmlObjectItem> list;
	
	public static class XmlObjectItem {
		String section;
		HashMap<String, String> key_pair_list;
	}
	
	
	public XmlObject(List<XmlObjectItem> list) {
		super();
		this.list = list;
	}
	
	public void add(String section, HashMap<String, String> tmp) {
		
	}

	public List<XmlObjectItem> getItem(String section) {
		List<XmlObjectItem> ret = new ArrayList<XmlObject.XmlObjectItem>();
		
		for (XmlObjectItem i : list) {
			if (i.section.equalsIgnoreCase(section)) {
				ret.add(i);
			}
		}
		
		return ret;
	}
	
	public String getValue(XmlObjectItem item, String key) {
		return item.key_pair_list.get(key);
	}
}
