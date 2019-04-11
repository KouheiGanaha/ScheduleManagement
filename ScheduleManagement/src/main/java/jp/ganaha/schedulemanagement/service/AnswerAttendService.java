package jp.ganaha.schedulemanagement.service;

import java.util.List;
import java.util.Map;

public interface AnswerAttendService {
	public Map<String, Object> getEventData(String eventUrl);

	public List<Map<String, Object>> getEventDate(String eventId);

	public Map<String, Map<String, Object>> getAnswerAttendance(String eventId,List<Map<String,Object>> eventDate);

	public List<String> getHeaderList(List<Map<String,Object>> eventDateList);

	public List<List<String>> getAnswerList(String eventId, List<Map<String,Object>> eventDateList);

}
