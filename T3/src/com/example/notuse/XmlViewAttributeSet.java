package com.example.notuse;

import java.util.List;

import org.dom4j.Attribute;

import android.util.AttributeSet;

public class XmlViewAttributeSet implements AttributeSet {

	// attributes in dom4j
	private List<Attribute> attrs;
	private void ASSERT(boolean b) {
		if (!b) {
			int i = 5 / 0;
		}
	}
	
	public XmlViewAttributeSet(List<Attribute> attrs) {
		super();
		this.attrs = attrs;
	}

	@Override
	public int getAttributeCount() {
		// TODO Auto-generated method stub
		return attrs.size();
	}

	@Override
	public String getAttributeName(int index) {
		// TODO Auto-generated method stub
		return attrs.get(index).getName();
	}

	@Override
	public String getAttributeValue(int index) {
		// TODO Auto-generated method stub
		return attrs.get(index).getValue();
	}

	@Override
	public String getAttributeValue(String namespace, String name) {
		// TODO Auto-generated method stub
		for (Attribute attr : attrs) {
			if (attr.getName().equalsIgnoreCase(namespace + ":" + name)) {
				return attr.getValue();
			}
		}
		
		return null;
	}

	@Override
	public String getPositionDescription() {
		// TODO Auto-generated method stub
		ASSERT(false);
		return null;
	}

	@Override
	public int getAttributeNameResource(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAttributeListValue(String namespace, String attribute,
			String[] options, int defaultValue) {
		// TODO Auto-generated method stub
		ASSERT(false);
		return 0;
	}

	@Override
	public boolean getAttributeBooleanValue(String namespace, String attribute,
			boolean defaultValue) {
		// TODO Auto-generated method stub
		for (Attribute attr : attrs) {
			return attr.getName().equalsIgnoreCase("YES");
		}
		return defaultValue;
	}

	@Override
	public int getAttributeResourceValue(String namespace, String attribute,
			int defaultValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAttributeIntValue(String namespace, String attribute,
			int defaultValue) {
		// TODO Auto-generated method stub
		for (Attribute attr : attrs) {
			return Integer.parseInt(attr.getName());
		}
		return defaultValue;
	}

	@Override
	public int getAttributeUnsignedIntValue(String namespace, String attribute,
			int defaultValue) {
		// TODO Auto-generated method stub
		return getAttributeIntValue(namespace, attribute, defaultValue);
	}

	@Override
	public float getAttributeFloatValue(String namespace, String attribute,
			float defaultValue) {
		// TODO Auto-generated method stub
		for (Attribute attr : attrs) {
			return Float.parseFloat(attr.getName());
		}
		return defaultValue;
	}

	@Override
	public int getAttributeListValue(int index, String[] options,
			int defaultValue) {
		// TODO Auto-generated method stub
		ASSERT(false);
		return 0;
	}

	@Override
	public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
		// TODO Auto-generated method stub
		return attrs.get(index).getValue().equalsIgnoreCase("YES");
	}

	@Override
	public int getAttributeResourceValue(int index, int defaultValue) {
		// TODO Auto-generated method stub
		ASSERT(false);
		return 0;
	}

	@Override
	public int getAttributeIntValue(int index, int defaultValue) {
		// TODO Auto-generated method stub
		return Integer.parseInt(attrs.get(index).getValue());
	}

	@Override
	public int getAttributeUnsignedIntValue(int index, int defaultValue) {
		// TODO Auto-generated method stub
		return Integer.parseInt(attrs.get(index).getValue());
	}

	@Override
	public float getAttributeFloatValue(int index, float defaultValue) {
		// TODO Auto-generated method stub
		return Float.parseFloat(attrs.get(index).getValue());
	}

	@Override
	public String getIdAttribute() {
		// TODO Auto-generated method stub
		ASSERT(false);
		return null;
	}

	@Override
	public String getClassAttribute() {
		// TODO Auto-generated method stub
		ASSERT(false);
		return null;
	}

	@Override
	public int getIdAttributeResourceValue(int defaultValue) {
		// TODO Auto-generated method stub
		ASSERT(false);
		return 0;
	}

	@Override
	public int getStyleAttribute() {
		// TODO Auto-generated method stub
		ASSERT(false);
		return 0;
	}

}
