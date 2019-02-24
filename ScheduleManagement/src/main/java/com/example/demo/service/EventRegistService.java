package com.example.demo.service;

import java.util.Map;

import com.example.demo.form.EventRegistForm;

public interface EventRegistService {

	public Map<String, Object> create(EventRegistForm eventRegistForm ,String eventUrl,String eventDate[]);

	public String[] getEventDate(EventRegistForm eventRegistForm);

	public String getEventUrl();
}
