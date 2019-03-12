package jp.ganaha.schedulemanagement.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ganaha.schedulemanagement.db.AnswerRegistAccessor;

@Service
public class AnswerRegistServiceImpl implements AnswerRegistService{
	@Autowired AnswerRegistAccessor answerRegistAccessor;

	@Override
	public Map<String,Object> getEventData(String eventUrl){
	Map<String,Object> eventData = answerRegistAccessor.getEventData(eventUrl);
	return eventData;
	}

}
