package com.example.notuse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;



public class XmlView extends LinearLayout {
	private String xml;
	private Document doc;
	private HashMap<Integer, View> viewMap = new HashMap<Integer, View>();
	private int curId = 1001;
	
	public static void ASSERT(boolean b) {
		if (!b) {
			int d = 5 / 0;
		}
	}
	
	public XmlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setOrientation(LinearLayout.VERTICAL);
	}
	
	public String save() {
		LinkedList<Element> itemStack = new LinkedList<Element>();
		itemStack.add(doc.getRootElement());
		
		// 遍历所有element
		while (itemStack.size() > 0) {
			Element item = itemStack.removeFirst();
			
			// update 
			Integer id = Integer.parseInt(item.attributeValue("key"));
			View v = viewMap.get(id);
			if (v instanceof EditText) {
				EditText et = (EditText)v;
				item.setText(et.getText().toString());
			}
			
			List<Element> list = item.elements();
			for (Element e : list) {
				itemStack.add(e);
			}
		}
		
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
			
			FileOutputStream out = new FileOutputStream(xml);
	        out.write(xmlWriter.toString().getBytes());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return xmlWriter.toString();
	}
	
	public void setXml(String xml) {
		this.xml = xml;
		doc = load(xml);
		initViews();
	}

	public void initViews() {
		
		LinkedList<ViewData> itemStack = new LinkedList<ViewData>();
		itemStack.add(new ViewData(this, doc.getRootElement()));
		
		while (itemStack.size() > 0) {
			ViewData vd = itemStack.removeFirst();
			System.out.println("--" + vd.item.getName());
			ViewGroup parent = InsertView(vd);
			List<Element> list = vd.item.elements();
			for (Element e : list) {
				ASSERT(null != parent);
				itemStack.add(new ViewData(parent, e));
			}
		}
	}

	private ViewGroup.LayoutParams attrsToLayoutParams(View parent, List<Attribute> attrs) {
		ViewGroup.LayoutParams lp = null;
		XmlViewAttributeSet as = new XmlViewAttributeSet(attrs);
		
		if (parent instanceof LinearLayout) {
			lp = attrsToLinearLayoutParams(attrs);
		} else if (parent instanceof RelativeLayout) {
			lp = attrsToRelativeLayoutParams(attrs);
		} else {
			lp = attrsToLinearLayoutParams(attrs);
		}

		return lp;
	}
	
	private LinearLayout.LayoutParams attrsToLinearLayoutParams(List<Attribute> attrs) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 0);
		for (Attribute attr : attrs) {
			String value = attr.getValue();
			
			if (attr.getName().equals("layout_width")) {
				// width
				if (value.equals("fill_parent")) {
					lp.width = ViewGroup.LayoutParams.FILL_PARENT;
				} else if (value.equals("match_parent")) {
					lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
				} else if (value.equals("wrap_content")) {
					lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
				} else if (value.endsWith("dp")) {
					lp.width = Integer.parseInt(value.replace("dp", ""));
				}
			} else if (attr.getName().equals("layout_height")) {
				// height
				if (value.equals("fill_parent")) {
					lp.height = ViewGroup.LayoutParams.FILL_PARENT;
				} else if (value.equals("match_parent")) {
					lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
				} else if (value.equals("wrap_content")) {
					lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
				} else if (value.endsWith("dp")) {
					lp.height = Integer.parseInt(value.replace("dp", ""));
				}
			} else if (attr.getName().equals("layout_weight")) {
				// weight
				lp.weight = Integer.parseInt(value);
			} else {
				//暂不支持其他属性
				
			}
		}
		
		return lp;
	}
	
	private RelativeLayout.LayoutParams attrsToRelativeLayoutParams(List<Attribute> attrs) {
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(0, 0);
		for (Attribute attr : attrs) {
			String value = attr.getValue();

			if (attr.getName().equals("layout_width")) {
				// width
				if (value.equals("fill_parent")) {
					lp.width = ViewGroup.LayoutParams.FILL_PARENT;
				} else if (value.equals("match_parent")) {
					lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
				} else if (value.equals("wrap_content")) {
					lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
				} else if (value.endsWith("dp")) {
					lp.width = Integer.parseInt(value.replace("dp", ""));
				}
			} else if (attr.getName().equals("layout_height")) {
				// height
				if (value.equals("fill_parent")) {
					lp.height = ViewGroup.LayoutParams.FILL_PARENT;
				} else if (value.equals("match_parent")) {
					lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
				} else if (value.equals("wrap_content")) {
					lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
				} else if (value.endsWith("dp")) {
					lp.height = Integer.parseInt(value.replace("dp", ""));
				}
			} else if (attr.getName().equals("layout_toRightOf")) {
				lp.addRule(RelativeLayout.RIGHT_OF, Integer.parseInt(value));
			} else if (attr.getName().equals("layout_toLeftOf")) {
				lp.addRule(RelativeLayout.LEFT_OF, Integer.parseInt(value));
			} else if (attr.getName().equals("layout_below")) {
				lp.addRule(RelativeLayout.BELOW, Integer.parseInt(value));
			} else if (attr.getName().equals("layout_above")) {
				lp.addRule(RelativeLayout.ABOVE, Integer.parseInt(value));
			} else if (attr.getName().equals("layout_centerInParent")) {
				lp.addRule(RelativeLayout.CENTER_IN_PARENT);
			} else if (attr.getName().equals("layout_centerHorizontal")) {
				lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			} else if (attr.getName().equals("layout_centerVertical")) {
				lp.addRule(RelativeLayout.CENTER_VERTICAL);
			}

		}
		
		return lp;
	}
	
	private ViewGroup InsertView(ViewData vd ) {
		View v = null;
		ViewGroup viewGroup = null;
		ViewGroup parent = vd.parent;
		Element item = vd.item;
		ASSERT(null != parent);
		ASSERT(null != item);
		
		String viewType = item.attributeValue("viewType");
		Integer id = Integer.parseInt(item.attributeValue("key"));
		String tag = item.getName();
		String text = item.getText();
		List<Attribute> attrs = item.attributes();
		
		ViewGroup.LayoutParams lp = attrsToLayoutParams(parent, attrs);
		// create view
		ASSERT(null != viewType);
		ASSERT(!viewType.equals(""));
		if (viewType.equalsIgnoreCase("LinearLayout")) {
			LinearLayout layout = new LinearLayout(vd.parent.getContext());
			if (item.attribute("orientation").getValue().equals("vertical")) {
				layout.setOrientation(LinearLayout.VERTICAL);
			} else if (item.attribute("orientation").getValue().equals("horizontal")) {
				layout.setOrientation(LinearLayout.HORIZONTAL);
			} 
			v = layout;
			viewGroup = layout;
		} else if (viewType.equalsIgnoreCase("RelativeLayout")) {
			RelativeLayout layout = new RelativeLayout(vd.parent.getContext());
			v = layout;
			viewGroup = layout;
		} else if (viewType.equalsIgnoreCase("TextView")) {
			TextView tv = new TextView(vd.parent.getContext());
			v = tv;
			if (null != tv) {
				tv.setText(text);
			}
		} else if (viewType.equalsIgnoreCase("EditText")) {
			EditText et = new EditText(vd.parent.getContext());
			v = et;
			if (null != et) {
				et.setText(text);
			}
		} else {
			ASSERT(false);
		}
		TableLayout tl = new TableLayout(vd.parent.getContext());
		v.setLayoutParams(lp);
		v.setId(id);
		viewMap.put(id, v);
		parent.addView(v);
		
		System.out.println(parent.hashCode() + "<--v:" + v.hashCode() + " tag:" + 
		item.getName() + " text:" + item.getText());
		return viewGroup;
	}
	
	class ViewData {
		ViewGroup parent;
		Element item;
		public ViewData(ViewGroup parent, Element item) {
			super();
			this.parent = parent;
			this.item = item;
		}
	}
	
	public static Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
}
