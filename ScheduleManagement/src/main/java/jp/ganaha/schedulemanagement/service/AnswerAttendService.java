package jp.ganaha.schedulemanagement.service;

import java.util.List;
import java.util.Map;

import jp.ganaha.schedulemanagement.form.answerAttendForm;

public interface AnswerAttendService {
	public Map<String, Object> getEventData(String eventUrl);

	public List<Map<String, Object>> getEventDate(String eventId);

	public List<Map<String, Object>> getAnswerUserName(answerAttendForm answerAttendForm);

	public Map<String, Map<String, Object>> getAnswerAttendance(answerAttendForm answerAttendForm,List<Map<String,Object>> eventDate);

	public List<String> getHeaderList(List<Map<String,Object>> eventDateList);

	public List<List<String>> getAnswerList(answerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList);

}
