package jp.ganaha.schedulemanagement.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ganaha.schedulemanagement.db.AnswerRegistAccessor;
import jp.ganaha.schedulemanagement.form.AnswerRegistForm;

@Service
public class AnswerRegistServiceImpl implements AnswerRegistService{

	@Autowired AnswerRegistAccessor answerRegistAccessor;

	/**
	 * イベント情報取得
	 */
	@Override
	public Map<String,Object> getEventData(String eventUrl) {

		Map<String,Object> eventData = null;

		try {
			eventData = answerRegistAccessor.getEventData(eventUrl);
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
		List<Map<String,Object>> eventDate = answerRegistAccessor.getEventDate(eventId);
		return eventDate;
	}

	/**
	 * 回答の選択肢取得
	 */
	public Map<String,String> getSelectItems(){
		Map<String,String> selectMap = new LinkedHashMap<String,String>();
		selectMap.put("1","○");
		selectMap.put("2","△");
		selectMap.put("3","×");
		return selectMap;
	}


	/**
	 * イベント情報の登録
	 */
	@Override
	public void create(AnswerRegistForm answerRegistForm) {

		//イベントID取得
		String eventId = answerRegistForm.getEventId();

		//候補日情報取得
		List<Map<String, Object>> eventDateInfo = answerRegistAccessor.getEventDate(eventId);

		//回答テーブル登録
		try {
			answerRegistAccessor.insertUserAnswer(eventId, answerRegistForm.getAnswerName(), answerRegistForm.getComment());
		}catch(RuntimeException e) {
			throw new RuntimeException("回答者の登録に失敗しました",e);
		}

		//回答取得
		String answerAttendance[] = answerRegistForm.getAnswerAttendance();

		//回答情報テーブル登録
		int i = 0;
		for(Map<String, Object> eventDateList:eventDateInfo) {
			String eventDate = eventDateList.get("EVENT_DATE").toString();

			try {
				answerRegistAccessor.insertAnswerAttend(eventId, answerRegistForm.getAnswerName(),eventDate,answerAttendance[i]);
			}catch(RuntimeException e) {
				throw new RuntimeException("回答情報の登録に失敗しました",e);
			}
			i++;
		}
	}

}
