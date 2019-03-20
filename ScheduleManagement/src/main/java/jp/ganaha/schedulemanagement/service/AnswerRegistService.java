package jp.ganaha.schedulemanagement.service;

import java.util.List;
import java.util.Map;

public interface AnswerRegistService {

	public Map<String,Object> getEventData(String eventUrl);

	public List<Map<String,Object>> getEventDate(Map<String,Object> eventData);

	public Map<String,String> getSelectItems();

}
