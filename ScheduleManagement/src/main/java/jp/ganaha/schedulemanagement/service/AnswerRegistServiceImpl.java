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

	@Autowired
	AnswerRegistAccessor answerRegistAccessor;

	/**
	 * イベント情報取得
	 */
	@Override
	public Map<String,Object> getEventData(String eventUrl){

		Map<String,Object> eventData = null;

		try {
			eventData = answerRegistAccessor.getEventData(eventUrl);
		}catch(RuntimeException e) {
			return null;
		}

	return eventData;
	}

	/**
	 * イベントの候補日取得
	 */
	@Override
	public List<Map<String,Object>> getEventDate(Map<String,Object> eventData){
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
	 * イベント情報を登録する
	 */
	@Override
	public void create(AnswerRegistForm answerRegistForm){

		/**
		 * イベントID
		 */
		String eventId ="389";

		//回答をDBに登録
		try {
			answerRegistAccessor.insertUserAnswer(eventId, answerRegistForm.getAnswer(), answerRegistForm.getComment());
		}catch(RuntimeException e) {
			throw new RuntimeException("イベント情報の登録に失敗しました",e);
		}


	}

}
