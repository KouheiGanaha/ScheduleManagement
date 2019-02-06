package com.example.demo.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventRegistAccessor {

	@Autowired JdbcTemplate jdbcTemplate;

	/**
	 * TB_MST_EVENTにイベントを追加
	 * @param eventId
	 * @param eventName
	 * @param eventMemo
	 * @param eventUrl
	 * @return 更新件数
	 */
	public int insertEvent(String eventId, String eventName, String eventMemo, String eventUrl) {
		int count = jdbcTemplate.update("insert into TB_MST_EVENT(EVENT_ID, EVENT_NAME, EVENT_MEMO, EVENT_URL)"
				+ "values (?,?,?,?)",eventId, eventName, eventMemo, eventUrl);

		return count;
	}


	public Map<String, Object> getEventData(String eventId) {
		Map<String, Object> row = jdbcTemplate.queryForMap("select * from TB_MST_EVENT where EVENT_ID = ?", eventId);
		return row;

	}

}
