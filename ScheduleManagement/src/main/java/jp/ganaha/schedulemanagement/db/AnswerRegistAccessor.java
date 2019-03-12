package jp.ganaha.schedulemanagement.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class AnswerRegistAccessor {


	@Autowired JdbcTemplate jdbcTemplate;

	/**
	 * イベント情報を取得
	 */
	public Map<String, Object> getEventData(String eventUrl) {
		Map<String, Object> row = jdbcTemplate.queryForMap("select * from TB_MST_EVENT where EVENT_ID = ?", eventUrl);
		return row;
	}

	/**
	 * 回答者を登録
	 */
	public int insertEvent(int eventId, String answerUserName, String answerUserComment) {
		int resultCount = jdbcTemplate.update("insert into TB_TRN_USER_ANSWER(EVENT_ID, ANSWER_USER_NAME, ANSWER_USER_COMMENT)"
				+ "values (?,?,?)", eventId,eventId, answerUserName, answerUserComment);
		return resultCount;
	}


}
