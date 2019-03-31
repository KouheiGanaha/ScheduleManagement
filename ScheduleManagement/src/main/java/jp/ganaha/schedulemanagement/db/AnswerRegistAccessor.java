package jp.ganaha.schedulemanagement.db;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnswerRegistAccessor {


	@Autowired JdbcTemplate jdbcTemplate;

	/**
	 * イベント情報を取得
	 */
	public Map<String, Object> getEventData(String eventUrl) {
		Map<String, Object> row = jdbcTemplate.queryForMap("select * from TB_MST_EVENT where EVENT_URL = ?", eventUrl);
		return row;
	}

	/**
	 * 候補日を取得
	 * @param eventId
	 * @return 取得結果
	 */
	public List<Map<String, Object>> getEventDate(String eventId) {
		List<Map<String, Object>> row = jdbcTemplate.queryForList("select * from TB_MST_EVENT_DATE where EVENT_ID = ?", eventId);
		return row;
	}

	/**
	 * 回答者を登録
	 */
	public int insertUserAnswer(String eventId, String answerUserName, String answerUserComment) {
		int resultCount = jdbcTemplate.update("insert into TB_TRN_USER_ANSWER(EVENT_ID, ANSWER_USER_NAME, ANSWER_USER_COMMENT)"
				+ "values (?,?,?)", eventId,answerUserName, answerUserComment);
		return resultCount;
	}


	/**
	 * 回答情報を登録
	 */
	public int insertAnswerAttend(String eventId, String answerUserName,String answerEventDate,String answerAttendance) {
		int resultCount = jdbcTemplate.update("insert into TB_TRN_ANSWER_ATTEND(ANSWER_ATTEND_NO,EVENT_ID,ANSWER_USER_NAME, ANSWER_EVENT_DATE,ANSWER_ATTENDANCE)"
				+ "values (ANSWER_ATTEND_NO.NEXTVAL,?,?,?,?)",eventId,answerUserName,answerEventDate,answerAttendance);
		return resultCount;
	}

}
