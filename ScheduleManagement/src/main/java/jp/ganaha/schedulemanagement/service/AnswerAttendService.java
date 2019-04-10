package jp.ganaha.schedulemanagement.service;

import java.util.List;
import java.util.Map;

import jp.ganaha.schedulemanagement.form.AnswerAttendForm;

public interface AnswerAttendService {
	public Map<String, Object> getEventData(String eventUrl);

	public List<Map<String, Object>> getEventDate(String eventId);

	public List<Map<String, Object>> getAnswerUserName(AnswerAttendForm answerAttendForm);

	public Map<String, Map<String, Object>> getAnswerAttendance(AnswerAttendForm answerAttendForm,List<Map<String,Object>> eventDate);

	public List<String> getHeaderList(List<Map<String,Object>> eventDateList);

	public List<List<String>> getAnswerList(AnswerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList);

}
