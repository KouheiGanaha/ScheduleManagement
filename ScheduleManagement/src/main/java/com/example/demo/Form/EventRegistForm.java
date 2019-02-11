package com.example.demo.Form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class EventRegistForm {

	/**イベントID**/
	@NotEmpty
	private String eventId;

	/**イベント名**/
	@NotEmpty
	private String eventName;

	/**イベントメモ**/
	private String eventMemo;

	/**イベントURL**/
	@NotEmpty
	private String eventUrl;

	/**候補日**/
	@NotEmpty
	/**YYYY/mm/dd HH:MM形式を許可する**/
	@Pattern(regexp="^[0-9]{4}/[01]?[0-9]/[0123]?[0-9]\\s([0-1][0-9]|2[0-3]):[0-5][0-9]$")
	private String eventDate;

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
		return eventMemo;
	}

	public void setEventMemo(String eventMemo) {
		this.eventMemo = eventMemo;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	@Override
	public String toString() {
		return "EventRegistForm [eventId=" + eventId + ", eventName=" + eventName + ", eventMemo=" + eventMemo
				+ ", eventUrl=" + eventUrl + ", eventDate=" + eventDate + "]";
	}



}
