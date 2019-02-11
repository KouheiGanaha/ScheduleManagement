package com.example.demo.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventRegistAccessor {

	@Autowired JdbcTemplate jdbcTemplate;

	/**
	 * TB_MST_EVENTにイベントを登録
	 * @param eventId
	 * @param eventName
	 * @param eventMemo
	 * @param eventUrl
	 * @return 更新件数
	 */
	public int insertEvent(String eventId,String eventName, String eventMemo, String eventUrl) {
		int resultCount = jdbcTemplate.update("insert into TB_MST_EVENT(EVENT_ID, EVENT_NAME, EVENT_MEMO, EVENT_URL)"
				+ "values (?,?,?,?)",eventId,eventName, eventMemo, eventUrl);

		return resultCount;
	}

	/**
	 * TB_MST_EVENT_DATEに候補日を登録
	 * @param eventId
	 * @param eventDateNo
	 * @param eventDate
	 * @return 更新件数
	 */
	public int insertEventDate(String eventId,String eventDateNo,String eventDate) {
		int resultCount = jdbcTemplate.update("insert into TB_MST_EVENT_DATE(EVENT_ID,EVENT_DATE_NO,EVENT_DATE)"
				+ "values (?,?,?)",eventId,eventDateNo, eventDate);

		return resultCount;
	}

	/**
	 * SEQ取得
	 */
	public Map<String, Object> getEventDateSequence(){
	Map<String, Object> sequence = jdbcTemplate.queryForMap("select case when max(cast(EVENT_DATE_NO as int)) is null then '1' else max(cast(EVENT_DATE_NO as int))+1 end as EVENT_DATE_NO from TB_MST_EVENT_DATE");
	return sequence;
	}
	public Map<String, Object> getEventSequence(){
	Map<String, Object> sequence = jdbcTemplate.queryForMap("select case when max(cast(EVENT_ID as int)) is null then '1' else max(cast(EVENT_ID as int))+1 end as EVENT_ID from TB_MST_EVENT");
	return sequence;
	}


	/**
	 * イベント情報を取得
	 * @param eventId
	 * @return
	 */
	public Map<String, Object> getEventData(String eventId) {
		Map<String, Object> row = jdbcTemplate.queryForMap("select * from TB_MST_EVENT where EVENT_ID = ?", eventId);
		return row;

	}

	/**
	 * イベントURLを取得
	 */
	public Map<String, Object> getEventUrl(String eventUrl) {
		Map<String, Object> getEventUrl = jdbcTemplate.queryForMap("select count(*) EVENT_URL from TB_MST_EVENT where EVENT_URL = ?", eventUrl);
		return getEventUrl;

	}


}
