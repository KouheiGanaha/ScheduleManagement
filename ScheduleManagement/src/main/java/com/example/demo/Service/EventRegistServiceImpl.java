package com.example.demo.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Form.EventRegistForm;
import com.example.demo.db.EventRegistAccessor;

@Service
public class EventRegistServiceImpl implements EventRegistService{

	@Autowired
	EventRegistAccessor eventRegistAccessor;


	@Override
	public Map<String, Object> create(EventRegistForm eventRegistForm) {

		int updateCount = eventRegistAccessor.insertEvent(eventRegistForm.getEventId(),eventRegistForm.getEventName(),
				eventRegistForm.getEventMemo(), eventRegistForm.getEventUrl());

		Map<String, Object> row = eventRegistAccessor.getEventData(eventRegistForm.getEventId());

		return row;

	}



}
