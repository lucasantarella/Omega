package com.lucasantarella.omega;

/**
 * This class handle RSS Item <item> node in rss xml
 * */
public class RSSItem {

	// All <item> node name
	String _title;
	String _link;
	String _description;
	String _pubdate;
	String _guid;
	String _content;
	String _author;
	// constructor
	public RSSItem() {

	}

	// constructor with parameters
	public RSSItem(String title, String link, String description,
			String pubdate, String guid, String content, String author) {
		this._title = title;
		this._link = link;
		this._description = description;
		this._pubdate = pubdate;
		this._guid = guid;
		this._content = content;
		this._author = author;
	}

	public String get_title() {
		return _title;
	}

	public String get_link() {
		return _link;
	}

	public String get_description() {
		return _description;
	}

	public String get_pubdate() {
		return _pubdate;
	}

	public String get_guid() {
		return _guid;
	}

	public String get_content() {
		return _content;
	}

	public String get_author() {
		return _author;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public void set_link(String _link) {
		this._link = _link;
	}

	public void set_description(String _description) {
		this._description = _description;
	}

	public void set_pubdate(String _pubdate) {
		this._pubdate = _pubdate;
	}

	public void set_guid(String _guid) {
		this._guid = _guid;
	}

	public void set_content(String _content) {
		this._content = _content;
	}

	public void set_author(String _author) {
		this._author = _author;
	}

}
