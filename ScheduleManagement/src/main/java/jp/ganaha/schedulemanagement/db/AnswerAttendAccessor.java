package jp.ganaha.schedulemanagement.db;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnswerAttendAccessor {

	@Autowired JdbcTemplate jdbcTemplate;

	final static Logger logger =LoggerFactory.getLogger(AnswerAttendAccessor.class);

	/**
	 * イベント情報を取得
	 */
	public Map<String, Object> getEventData(String eventUrl) {
		Map<String, Object> row = jdbcTemplate.queryForMap("select * from TB_MST_EVENT where EVENT_URL = ?", eventUrl);
		System.out.println(row);
		return row;
	}

	/**
	 * イベントの候補日を取得
	 * @return
	 */
	public List<Map<String, Object>> getAnswerAttendEventDate(String eventId) {
		List<Map<String, Object>> row = jdbcTemplate.queryForList("select EVENT_DATE from TB_MST_EVENT_DATE where EVENT_ID = ?",eventId);
		return row;
	}

	/**
	 * 候補日の○の数をカウント
	 * @param eventDate
	 * @return 出欠集計結果
	 */
	public Map<String, Object> getAnswerCountMaru(String eventDate, String eventId) {
		Map<String,Object> answerCount = jdbcTemplate.queryForMap("select count(*) AS ANSWER_COUNT from TB_TRN_ANSWER_ATTEND where ANSWER_ATTENDANCE = '0' and ANSWER_EVENT_DATE = ? and EVENT_ID = ?", eventDate, eventId);
		return answerCount;
	}

	/**
	 * 候補日の△の数をカウント
	 * @param eventDate
	 * @return 出欠集計結果
	 */
	public Map<String, Object> getAnswerCountSankaku(String eventDate, String eventId) {
		Map<String,Object> answerCount = jdbcTemplate.queryForMap("select count(*) AS ANSWER_COUNT from TB_TRN_ANSWER_ATTEND where ANSWER_ATTENDANCE = '1' and ANSWER_EVENT_DATE = ? and EVENT_ID = ?",eventDate, eventId);
		return answerCount;
	}

	/**
	 * 候補日の×の数をカウント
	 * @param eventDate
	 * @return 出欠集計結果
	 */
	public Map<String, Object> getAnswerCountBatu(String eventDate, String eventId) {
		Map<String,Object> answerCount = jdbcTemplate.queryForMap("select count(*) AS ANSWER_COUNT from TB_TRN_ANSWER_ATTEND where ANSWER_ATTENDANCE = '2' and ANSWER_EVENT_DATE = ? and EVENT_ID = ?",eventDate, eventId);
		return answerCount;
	}

	/**
	 * イベントごとの回答者の氏名を取得
	 * @param eventDate
	 * @return
	 */
	public List<Map<String, Object>> getAnswerUserName(String eventId) {
		List<Map<String, Object>> answerCount = jdbcTemplate.queryForList("select ANSWER_USER_NAME from TB_TRN_USER_ANSWER where EVENT_ID = ?",eventId);
		return answerCount;
	}

	/**
	 * 候補日と氏名を指定して回答を取得
	 * @param eventDate
	 * @param userName
	 * @return
	 */
	public Map<String, Object> getAnswerAttendance(String eventId, String eventDate, String userName) {
		Map<String,Object> answerCount = jdbcTemplate.queryForMap("select ANSWER_ATTENDANCE from TB_TRN_ANSWER_ATTEND where EVENT_ID = ? and ANSWER_EVENT_DATE = ? and ANSWER_USER_NAME = ?", eventId,eventDate,userName);
		return answerCount;
	}

	/**
	 * イベントIDと氏名を指定してコメントを取得
	 * @param eventId
	 * @param userName
	 * @return
	 */
	public Map<String,Object> getComment(String eventId, String userName){
		Map<String,Object> comment = jdbcTemplate.queryForMap("select ANSWER_USER_COMMENT from TB_TRN_USER_ANSWER where EVENT_ID = ? and ANSWER_USER_NAME = ?",eventId,userName);
		return comment;
	}


}
