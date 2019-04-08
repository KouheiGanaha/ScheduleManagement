package jp.ganaha.schedulemanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ganaha.schedulemanagement.db.AnswerAttendAccessor;
import jp.ganaha.schedulemanagement.form.answerAttendForm;

@Service
public class AnswerAttendServiceImpl implements AnswerAttendService{

	@Autowired AnswerAttendAccessor answerAttendAccessor;

	/**
	 * イベントURLのランダム値を引数にイベント情報を取得
	 */
	@Override
	public Map<String, Object> getEventData(String eventUrl){

		//EVENT_URLを指定してイベント情報取得
		Map<String, Object> eventData = answerAttendAccessor.getEventData(eventUrl);

		return eventData;

	}

	/**
	 * イベントIDを引数に候補日を取得
	 */
	@Override
	public List<Map<String, Object>> getEventDate(String eventId){

		//EVENT_IDを指定して候補日を取得する
		List<Map<String, Object>> answerAttendList = answerAttendAccessor.getAnswerAttendEventDate(eventId);

		return answerAttendList;
	}

	/**
	 * イベントIDを引数に氏名を取得
	 */
	public List<Map<String, Object>> getAnswerUserName(answerAttendForm answerAttendForm){
		String eventId = answerAttendForm.getEventId();

		//氏名を取得
		List<Map<String,Object>> nameList = answerAttendAccessor.getAnswerUserName(eventId);

		return nameList;

	}

	/**
	 * 回答集計結果を取得
	 */
	@Override
	public Map<String, Map<String, Object>> getAnswerAttendance(answerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList){
		System.out.println(answerAttendForm.getEventUrl());
		String eventId = answerAttendForm.getEventId();

		//イベントURLのランダム値取得
		String eventUrl = answerAttendForm.getEventUrl();

		Map<String,Map<String,Object>> answerAttendance = new HashMap<String,Map<String,Object>>();

		int i = 0;
		for(Map<String,Object> eventDateMap : eventDateList) {
			eventDateMap = eventDateList.get(i);
			String eventDate = eventDateMap.get("EVENT_DATE").toString();

			//各回答情報を取得
			Map<String, Object> answerAttenDanceMaru = answerAttendAccessor.getAnswerCountMaru(eventDate);
			Map<String, Object> answerAttenDanceSankaku = answerAttendAccessor.getAnswerCountSankaku(eventDate);
			Map<String, Object> answerAttenDanceBatu = answerAttendAccessor.getAnswerCountBatu(eventDate);
			String answerAttenDance1 = answerAttenDanceMaru.get("ANSWER_COUNT").toString();
			String answerAttenDance2 = answerAttenDanceSankaku.get("ANSWER_COUNT").toString();
			String answerAttenDance3 = answerAttenDanceBatu.get("ANSWER_COUNT").toString();

			answerAttendance.put(eventDate, new HashMap<String,Object>());
			answerAttendance.get(eventDate).put("1", answerAttenDance1);
			answerAttendance.get(eventDate).put("2", answerAttenDance2);
			answerAttendance.get(eventDate).put("3", answerAttenDance3);


			System.out.println(answerAttendance.get(eventDate).values());
			i++;
		}

		return answerAttendance;
	}



	/**
	 * 回答内容取得(仮)
	 */
	public List<Map<String, Object>> getAnswer(answerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList){
		String eventId = answerAttendForm.getEventId();
		Map<String,Object> answerInfo = new HashMap<String, Object>();


		//氏名を取得
		List<Map<String,Object>> nameList = answerAttendAccessor.getAnswerUserName(eventId);
		List<Map<String,Object>> Answer = null;


		//候補日でまわす
		for(Map<String,Object> eventDateMap : eventDateList) {
			String eventDate = eventDateMap.get("EVENT_DATE").toString();

			for(Map<String, Object> name:nameList) {
				//氏名を取得
				String userName = name.get("ANSWER_USER_NAME").toString();

				//候補日と氏名を指定して回答を取得
				Answer = answerAttendAccessor.getAnswerAttendance2(eventDate, userName);

			}
		}
		return Answer;
	}


	/**
	 * 回答内容取得(仮)2
	 */
	@Override
	public Map<String,Map<String,Object>> getAnswer2(answerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList){

		String eventId = answerAttendForm.getEventId();
		//氏名を取得
		List<Map<String,Object>> nameList = answerAttendAccessor.getAnswerUserName(eventId);
		Map<String,Map<String,Object>> answerInfoMap = new HashMap<String,Map<String, Object>>();

		//候補日で繰り返し
		for(Map<String,Object> eventDateMap : eventDateList) {
			String eventDate = eventDateMap.get("EVENT_DATE").toString();

			for(Map<String, Object> name:nameList) {
				//氏名を取得
				String userName = name.get("ANSWER_USER_NAME").toString();

				//候補日をキーにしたMapを生成
				answerInfoMap.put(eventDate, new HashMap<String, Object>());

				//候補日と氏名を指定して回答を取得
				Map<String,Object> Answer = answerAttendAccessor.getAnswerAttendance(eventDate, userName);
				String answerAttendance= Answer.get("ANSWER_ATTENDANCE").toString();

				//候補日をキーにして氏名をキーにした回答を取得するMap
				answerInfoMap.get(eventDate).put(userName,answerAttendance);
				System.out.println("候補日は" + eventDate + "氏名は"+userName + "回答は" + answerAttendance);
			}

		}
		System.out.println("Keyは" + answerInfoMap.keySet());

		return answerInfoMap;
	}



	public Map<String,List<String>> getAnswer3(answerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList){

		String eventId = answerAttendForm.getEventId();

		//氏名を取得
		List<Map<String,Object>> nameList = answerAttendAccessor.getAnswerUserName(eventId);


		Map<String,List<String>> answerInfoMap = new HashMap<>();
		List<String> list = new ArrayList<>();


		//候補日で繰り返し
		for(Map<String,Object> eventDateMap : eventDateList) {
			String eventDate = eventDateMap.get("EVENT_DATE").toString();

			for(Map<String, Object> name:nameList) {
				//氏名を取得
				String userName = name.get("ANSWER_USER_NAME").toString();

				//候補日と氏名を指定して回答を取得
				Map<String,Object> Answer = answerAttendAccessor.getAnswerAttendance(eventDate, userName);
				String answerAttendance= Answer.get("ANSWER_ATTENDANCE").toString();
				list.add(answerAttendance);

				answerInfoMap.put(userName, list);

			}
		}
		return answerInfoMap;
	}
}

