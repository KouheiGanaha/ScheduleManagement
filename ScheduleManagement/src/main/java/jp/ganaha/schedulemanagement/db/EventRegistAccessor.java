package jp.ganaha.schedulemanagement.db;

import java.util.List;
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
	public int insertEvent(int eventId, String eventName, String eventMemo, String eventUrl) {
		int resultCount = jdbcTemplate.update("insert into TB_MST_EVENT(EVENT_ID, EVENT_NAME, EVENT_MEMO, EVENT_URL)"
				+ "values (?,?,?,?)", eventId,eventName, eventMemo, eventUrl);
		return resultCount;
	}

	/**
	 * TB_MST_EVENT_DATEに候補日を登録
	 * @param eventId
	 * @param eventDate
	 * @return 更新件数
	 */
	public int insertEventDate(int eventId,String eventDate) {
		int resultCount = jdbcTemplate.update("insert into TB_MST_EVENT_DATE(EVENT_ID,EVENT_DATE_NO,EVENT_DATE)"
				+ "values (?,EVENT_DATE_NO.NEXTVAL,?)",eventId,eventDate);
		return resultCount;
	}

	/**
	 * イベントIDのシーケンスを取得
	 * @return シーケンス値
	 */
	public int getEventId() {
		int eventId = jdbcTemplate.queryForObject("select EVENT_ID_SEQ.NEXTVAL FROM DUAL", int.class);
		return eventId;
	}


	/**
	 * イベント情報を取得
	 */
	public Map<String, Object> getEventData(int eventId) {
		Map<String, Object> row = jdbcTemplate.queryForMap("select * from TB_MST_EVENT where EVENT_ID = ?", eventId);
		return row;

	}

	/**
	 * イベントURLのランダム値を取得
	 * @param eventUrl
	 * @return ランダム値
	 */
	public Map<String, Object> getEventUrl(String eventUrl) {
		Map<String, Object> getEventUrl = jdbcTemplate.queryForMap("select count(*) EVENT_URL from TB_MST_EVENT where EVENT_URL = ?", eventUrl);
		return getEventUrl;

	}

	/**
	 * イベント情報を取得
	 */
	public Map<String, Object> getsEventData(String eventUrl) {
		Map<String, Object> row = jdbcTemplate.queryForMap("select * from TB_MST_EVENT where EVENT_URL = ?", eventUrl);
		return row;
	}

	/**
	 * 候補日を取得
	 * @param eventId
	 * @return 取得結果
	 */
	public List<Map<String, Object>> getEventDate(String eventId) {
		List<Map<String, Object>> row = jdbcTemplate.queryForList("select EVENT_DATE from TB_MST_EVENT_DATE where EVENT_ID = ?", eventId);
		return row;
	}


	/**
	 * イベントの候補日を取得＠AnswerAttend用
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
	public Map<String, Object> getAnswerCountMaru(String eventDate) {
		Map<String,Object> answerCount = jdbcTemplate.queryForMap("select count(*) AS ANSWER_COUNT from TB_TRN_ANSWER_ATTEND where ANSWER_ATTENDANCE = '1' and ANSWER_EVENT_DATE = ?",eventDate);
		return answerCount;
	}

	/**
	 * 候補日の△の数をカウント
	 * @param eventDate
	 * @return 出欠集計結果
	 */
	public Map<String, Object> getAnswerCountSankaku(String eventDate) {
		Map<String,Object> answerCount = jdbcTemplate.queryForMap("select count(*) AS ANSWER_COUNT from TB_TRN_ANSWER_ATTEND where ANSWER_ATTENDANCE = '2' and ANSWER_EVENT_DATE = ?",eventDate);
		return answerCount;
	}

	/**
	 * 候補日の×の数をカウント
	 * @param eventDate
	 * @return 出欠集計結果
	 */
	public Map<String, Object> getAnswerCountBatu(String eventDate) {
		Map<String,Object> answerCount = jdbcTemplate.queryForMap("select count(*) AS ANSWER_COUNT from TB_TRN_ANSWER_ATTEND where ANSWER_ATTENDANCE = '3' and ANSWER_EVENT_DATE = ?",eventDate);
		return answerCount;
	}

	/**
	 * 候補日で氏名を検索
	 * @param eventDate
	 * @return
	 */
	public Map<String, Object> getAnswerUserName(String eventDate) {
		Map<String,Object> answerCount = jdbcTemplate.queryForMap("select ANSWER_USER_NAME from TB_TRN_ANSWER_ATTEND where ANSWER_EVENT_DATE = ?",eventDate);
		return answerCount;
	}

	/**
	 * 候補日と氏名を条件に回答をselect
	 * @param eventDate
	 * @param userName
	 * @return
	 */
	public Map<String, Object> getAnswerAttendance(String eventDate, String userName) {
		Map<String,Object> answerCount = jdbcTemplate.queryForMap("select ANSWER_USER_NAME from TB_TRN_ANSWER_ATTEND where ANSWER_ATTENDANCE = '3' and ANSWER_EVENT_DATE = ? and ANSWER_USER_NAME = ?",eventDate,userName);
		return answerCount;
	}

}
