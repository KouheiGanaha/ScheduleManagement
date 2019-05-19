package jp.ganaha.schedulemanagement.db;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnswerRegistAccessor {


	@Autowired JdbcTemplate jdbcTemplate;


	final static Logger logger =LoggerFactory.getLogger(AnswerRegistAccessor.class);

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
	public int insertAnswerAttend(String eventId, String answerUserName,String answerEventDate,int answerAttendance) {
		int resultCount = jdbcTemplate.update("insert into TB_TRN_ANSWER_ATTEND(ANSWER_ATTEND_NO,EVENT_ID,ANSWER_USER_NAME, ANSWER_EVENT_DATE,ANSWER_ATTENDANCE)"
				+ "values (ANSWER_ATTEND_NO.NEXTVAL,?,?,?,?)",eventId,answerUserName,answerEventDate,answerAttendance);
		return resultCount;
	}

	/**
	 * 登録する氏名が既にTB_TRN_USER_ANSWERに存在しないか確認
	 * @param eventId
	 * @param answerUserName
	 * @return
	 */
	public Map<String, Object> getAnswerRegistUserNameCount(String eventId, String answerUserName) {
		Map<String, Object> userNameCount = jdbcTemplate.queryForMap("select count(*) ANSWER_USER_COUNT from TB_TRN_USER_ANSWER where EVENT_ID = ? and ANSWER_USER_NAME = ?", eventId,answerUserName);
		return userNameCount;
	}

	/**
	 * 氏名を取得
	 * @param eventId
	 * @param answerUserName
	 * @return
	 */
	public Map<String, Object> getAnswerRegistUserName(String eventId, String answerUserName) {
		Map<String, Object> userName = jdbcTemplate.queryForMap("select ANSWER_USER from TB_TRN_USER_ANSWER where EVENT_ID = ? and ANSWER_USER_NAME = ?", eventId, answerUserName);
		return userName;
	}

	/**
	 * イベントIDと氏名をキーにしてTB_TRN_ANSWER_ATTENDから回答情報件数を取得
	 * @param eventId
	 * @param answerUserName
	 * @return
	 */
	public Map<String, Object> getAnswerAttendUserNameCount(String eventId, String answerUserName) {
		Map<String, Object> userNameCount = jdbcTemplate.queryForMap("select count(*) ANSWER_USER_COUNT from TB_TRN_ANSWER_ATTEND where EVENT_ID = ? and ANSWER_USER_NAME = ?", eventId,answerUserName);
		return userNameCount;
	}

	/**
	 * 回答を更新する
	 * @param eventId
	 * @param answerUserName
	 * @param answerEventDate
	 * @param answerAttendance
	 * @return
	 */
	public int answerAttendUpdate(String eventId, String answerUserName, String answerEventDate, int answerAttendance) {
		int updateCount = jdbcTemplate.update("update TB_TRN_ANSWER_ATTEND set ANSWER_ATTENDANCE = ? where EVENT_ID = ? and ANSWER_USER_NAME = ? and ANSWER_EVENT_DATE = ?", answerAttendance, eventId, answerUserName , answerEventDate);
		return updateCount;

	}

	/**
	 * コメントを更新する
	 * @param eventId
	 * @param answerUserName
	 * @param comment
	 * @return
	 */
	public int commentUpdate(String eventId, String answerUserName, String comment) {
		int updateCount = jdbcTemplate.update("update TB_TRN_USER_ANSWER set ANSWER_USER_COMMENT = ? where EVENT_ID = ? and ANSWER_USER_NAME = ?", comment, eventId, answerUserName);
		return updateCount;

	}



}
