package jp.ganaha.schedulemanagement.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventRegistAccessor {

	@Autowired JdbcTemplate jdbcTemplate;

	//TB_MST_EVENTにイベントを登録
	public int insertEvent(int eventId, String eventName, String eventMemo, String eventUrl) {
		int resultCount = jdbcTemplate.update("insert into TB_MST_EVENT(EVENT_ID, EVENT_NAME, EVENT_MEMO, EVENT_URL)"
				+ "values (?,?,?,?)", eventId,eventName, eventMemo, eventUrl);
		return resultCount;
	}

	//TB_MST_EVENT_DATEに候補日を登録
	public int insertEventDate(int eventId,String eventDate) {
		int resultCount = jdbcTemplate.update("insert into TB_MST_EVENT_DATE(EVENT_ID,EVENT_DATE_NO,EVENT_DATE)"
				+ "values (?,EVENT_DATE_NO.NEXTVAL,?)",eventId,eventDate);
		return resultCount;
	}

	//イベントIDのシーケンスを取得
	public int getEventId() {
		int eventId = jdbcTemplate.queryForObject("select EVENT_ID_SEQ.NEXTVAL FROM DUAL", int.class);
		return eventId;
	}


	//イベント情報を取得
	public Map<String, Object> getEventData(int eventId) {
		Map<String, Object> row = jdbcTemplate.queryForMap("select * from TB_MST_EVENT where EVENT_ID = ?", eventId);
		return row;

	}

	//イベントURLのランダム値を取得
	public Map<String, Object> getEventUrl(String eventUrl) {
		Map<String, Object> getEventUrl = jdbcTemplate.queryForMap("select count(*) EVENT_URL from TB_MST_EVENT where EVENT_URL = ?", eventUrl);
		return getEventUrl;

	}


}
