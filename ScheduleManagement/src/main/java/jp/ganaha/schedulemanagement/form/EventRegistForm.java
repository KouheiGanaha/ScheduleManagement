package jp.ganaha.schedulemanagement.form;

import javax.validation.constraints.NotEmpty;

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
