package com.example.notuse;

public class XmlItemCtrl {
	private int id;
	private String text;
	private boolean editable;
	
	public XmlItemCtrl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public XmlItemCtrl(int id, String text, boolean editable) {
		super();
		this.id = id;
		this.text = text;
		this.editable = editable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	

}