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

	//登録できる候補日の最大値分を初期化
	@NotEmpty
	private int answerAttendance[] = new int[57];

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

	public int[] getAnswerAttendance() {
		return answerAttendance;
	}

	public void setAnswerAttendance(int[] answerAttendance) {
		this.answerAttendance = answerAttendance;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}



}
