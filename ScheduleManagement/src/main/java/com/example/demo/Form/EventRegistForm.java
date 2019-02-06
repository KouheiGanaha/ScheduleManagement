package com.example.demo.Form;

public class EventRegistForm {

	private String eventId;
	private String eventName;
	private String EventMemo;
	private String EventUrl;
	private String EventDate;
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventMemo() {
		return EventMemo;
	}
	public void setEventMemo(String eventMemo) {
		EventMemo = eventMemo;
	}
	public String getEventUrl() {
		return EventUrl;
	}
	public void setEventUrl(String eventUrl) {
		EventUrl = eventUrl;
	}
	public String getEventDate() {
		return EventDate;
	}
	public void setEventDate(String eventDate) {
		EventDate = eventDate;
	}
	@Override
	public String toString() {
		return "EventRegistForm [eventId=" + eventId + ", eventName=" + eventName + ", EventMemo=" + EventMemo
				+ ", EventUrl=" + EventUrl + ", EventDate=" + EventDate + "]";
	}



}
