package jp.ganaha.schedulemanagement.service;

import java.util.List;
import java.util.Map;

import jp.ganaha.schedulemanagement.form.AnswerRegistForm;

public interface AnswerRegistService {

	public Map<String,Object> getEventData(String eventUrl);

	public List<Map<String,Object>> getEventDate(Map<String,Object> eventData);

	public void create(AnswerRegistForm answerRegistForm);

	public Map<String,String> getSelectItems();

}
