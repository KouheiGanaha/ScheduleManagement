package jp.ganaha.schedulemanagement.service;

import java.util.List;
import java.util.Map;

import jp.ganaha.schedulemanagement.form.answerAttendForm;

public interface AnswerAttendService {
	public Map<String, Object> getEventData(String eventUrl);

	public List<Map<String, Object>> getEventDate(String eventId);

	public List<Map<String, Object>> getAnswerUserName(answerAttendForm answerAttendForm);

	public Map<String, Map<String, Object>> getAnswerAttendance(answerAttendForm answerAttendForm,List<Map<String,Object>> eventDate);

	public List<Map<String, Object>> getAnswer(answerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList);

	public Map<String,Map<String,Object>> getAnswer2(answerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList);

	public Map<String,List<String>> getAnswer3(answerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList);
}
