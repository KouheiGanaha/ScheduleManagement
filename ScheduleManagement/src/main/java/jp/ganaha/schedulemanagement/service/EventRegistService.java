package jp.ganaha.schedulemanagement.service;

import java.util.Map;

import jp.ganaha.schedulemanagement.form.EventRegistForm;

public interface EventRegistService {

	public Map<String, Object> create(EventRegistForm eventRegistForm ,String eventUrl,String eventDate[]);

	public String getRondom();

	public String[] getEventDate(EventRegistForm eventRegistForm);


	public String getEventUrl(String eventRondomNumber);
}
