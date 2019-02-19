package com.example.demo.Service;

import java.util.Map;

import com.example.demo.Form.EventRegistForm;

public interface EventRegistService {

	public Map<String, Object> create(EventRegistForm eventRegistForm);

	public Map<String, Object> getResult(String eventId);

	public String[] eventDate(EventRegistForm eventRegistForm);

}
