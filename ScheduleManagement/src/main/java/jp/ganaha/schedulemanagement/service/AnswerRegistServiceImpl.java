package jp.ganaha.schedulemanagement.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ganaha.schedulemanagement.db.EventRegistAccessor;

@Service
public class AnswerRegistServiceImpl implements AnswerRegistService{
	@Autowired EventRegistAccessor answerRegistAccessor;

	@Override
	public Map<String,Object> getEventData(String eventUrl){
	Map<String,Object> eventData = answerRegistAccessor.getsEventData(eventUrl);
	return eventData;
	}

	@Override
	public List<Map<String,Object>> getEventDate(Map<String,Object> eventData){
		String eventId = eventData.get("EVENT_ID").toString();
		List<Map<String,Object>> eventDate = answerRegistAccessor.getEventDate(eventId);
		return eventDate;
	}

	public Map<String,String> getSelectItems(){
		Map<String,String> selectMap = new LinkedHashMap<String,String>();
		selectMap.put("key_A","○");
		selectMap.put("key_B","△");
		selectMap.put("key_C","×");
		return selectMap;
	}

}
