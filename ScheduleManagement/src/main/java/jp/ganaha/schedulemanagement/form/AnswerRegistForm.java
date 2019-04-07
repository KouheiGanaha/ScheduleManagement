package jp.ganaha.schedulemanagement.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AnswerRegistForm {

	private String eventId;

	@NotEmpty
	@Size(max=255)
	private String answerName;

	@Size(max=255)
	private String comment;

	@NotEmpty
	private String answerAttendance[];

	private String eventUrl;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getAnswerName() {
		return answerName;
	}

	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String[] getAnswerAttendance() {
		return answerAttendance;
	}

	public void setAnswerAttendance(String[] answerAttendance) {
		this.answerAttendance = answerAttendance;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}



}
