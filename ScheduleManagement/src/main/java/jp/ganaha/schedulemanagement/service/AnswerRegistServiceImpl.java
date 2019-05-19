package jp.ganaha.schedulemanagement.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ganaha.schedulemanagement.db.AnswerRegistAccessor;
import jp.ganaha.schedulemanagement.form.AnswerRegistForm;

@Service
public class AnswerRegistServiceImpl implements AnswerRegistService{

	@Autowired AnswerRegistAccessor answerRegistAccessor;

	final static Logger logger =LoggerFactory.getLogger(EventRegistServiceImpl.class);

	/**
	 * イベント情報取得
	 */
	@Override
	public Map<String,Object> getEventData(String eventUrl) {

		Map<String,Object> eventData = null;

		try {
			eventData = answerRegistAccessor.getEventData(eventUrl);
			logger.info("event data: {}", eventData);
		}catch(RuntimeException e) {
			throw new RuntimeException("イベント情報が見つかりませんでした", e);
		}

		return eventData;
	}

	/**
	 * イベントの候補日取得
	 */
	@Override
	public List<Map<String,Object>> getEventDate(Map<String,Object> eventData) {
		String eventId = eventData.get("EVENT_ID").toString();
		logger.info("eventDateList start");
		List<Map<String,Object>> eventDateList = answerRegistAccessor.getEventDate(eventId);
		logger.info("eventDateList end: {}", eventDateList);
		return eventDateList;
	}

	/**
	 * 回答の選択肢取得
	 */
	public Map<String,String> getSelectItems(){
		Map<String,String> selectMap = new LinkedHashMap<String,String>();
		selectMap.put("1", "○");
		selectMap.put("2", "△");
		selectMap.put("3", "×");
		return selectMap;
	}


	/**
	 * イベント情報の登録
	 */
	@Override
	public void create(AnswerRegistForm answerRegistForm) {

		//イベントID取得
		String eventId = answerRegistForm.getEventId();

		String answerUserName = answerRegistForm.getAnswerName();

		//候補日情報取得
		List<Map<String, Object>> eventDateInfo = answerRegistAccessor.getEventDate(eventId);

		//登録するイベントのIDと氏名をキーに条件に一致する回答情報の件数を取得
		Map<String, Object> userNameMap =  answerRegistAccessor.getAnswerRegistUserNameCount(eventId, answerUserName);
		String userNameCount = userNameMap.get("ANSWER_USER_COUNT").toString();

		//件数が0件だった場合は回答者の登録をする
		if(StringUtils.equals(userNameCount, "0")) {
			//回答テーブル登録
			try {
				logger.info("userAnswerCount start");
				int userAnswerCount = answerRegistAccessor.insertUserAnswer(eventId, answerRegistForm.getAnswerName(), answerRegistForm.getComment());
				logger.info("userAnswerCount end: {}", userAnswerCount);

			}catch(RuntimeException e) {
				throw new RuntimeException("回答者の登録に失敗しました", e);
			}

		//登録する回答情報と一致するレコードが存在する場合はコメントの更新のみ
		} else {
			logger.info("commentUpdateCount start");
			int commentUpdateCount = answerRegistAccessor.commentUpdate(eventId, answerRegistForm.getAnswerName(), answerRegistForm.getComment());
			logger.info("commentUpdateCount end: {}", commentUpdateCount);
		}

		//回答取得
		int answerAttendance[] = answerRegistForm.getAnswerAttendance();

		//回答情報テーブル登録
		int i = 0;
		for(Map<String, Object> eventDateList:eventDateInfo) {
			String eventDate = eventDateList.get("EVENT_DATE").toString();

			////登録するイベントのIDと氏名をキーに条件に一致する回答情報の件数を取得
			Map<String, Object> userNameMap2 =  answerRegistAccessor.getAnswerAttendUserNameCount(eventId, answerUserName);
			String userName2 = userNameMap.get("ANSWER_USER_COUNT").toString();

			//件数が0件だった場合は回答登録をする
			if(StringUtils.equals(userName2, "0")) {
				try {
					logger.info("AnswerAttendCount start");
					int AnswerAttendCount = answerRegistAccessor.insertAnswerAttend(eventId, answerRegistForm.getAnswerName(),eventDate,answerAttendance[i]);
					logger.info("AnswerAttendCount end: {}", AnswerAttendCount);

				}catch(RuntimeException e) {
					throw new RuntimeException("回答者の登録に失敗しました", e);
				}
			} else {
				//既に回答している場合は回答情報を更新する
				logger.info("answerUpdateCount start");
				int answerUpdateCount = answerRegistAccessor.answerAttendUpdate(eventId, answerRegistForm.getAnswerName(), eventDate, answerAttendance[i]);
				logger.info("answerUpdateCount end: {}", answerUpdateCount);
			}
			i++;
		}
	}
}
