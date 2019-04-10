package jp.ganaha.schedulemanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ganaha.schedulemanagement.db.AnswerAttendAccessor;
import jp.ganaha.schedulemanagement.form.AnswerAttendForm;

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
	@Override
	public List<Map<String, Object>> getAnswerUserName(AnswerAttendForm answerAttendForm){
		String eventId = answerAttendForm.getEventId();

		//氏名を取得
		List<Map<String,Object>> nameList = answerAttendAccessor.getAnswerUserName(eventId);

		return nameList;

	}

	/**
	 * 回答集計結果を取得
	 */
	@Override
	public Map<String, Map<String, Object>> getAnswerAttendance(AnswerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList){
		System.out.println(answerAttendForm.getEventUrl());
		String eventId = answerAttendForm.getEventId();

		Map<String,Map<String,Object>> answerAttendance = new HashMap<String,Map<String,Object>>();

		int i = 0;
		for(Map<String,Object> eventDateMap : eventDateList) {
			eventDateMap = eventDateList.get(i);
			String eventDate = eventDateMap.get("EVENT_DATE").toString();

			//各回答情報を取得
			Map<String, Object> answerAttenDanceMaru = answerAttendAccessor.getAnswerCountMaru(eventDate, eventId);
			Map<String, Object> answerAttenDanceSankaku = answerAttendAccessor.getAnswerCountSankaku(eventDate, eventId);
			Map<String, Object> answerAttenDanceBatu = answerAttendAccessor.getAnswerCountBatu(eventDate, eventId);
			String answerAttenDance1 = answerAttenDanceMaru.get("ANSWER_COUNT").toString();
			String answerAttenDance2 = answerAttenDanceSankaku.get("ANSWER_COUNT").toString();
			String answerAttenDance3 = answerAttenDanceBatu.get("ANSWER_COUNT").toString();

			answerAttendance.put(eventDate, new HashMap<String,Object>());
			answerAttendance.get(eventDate).put("1", answerAttenDance1);
			answerAttendance.get(eventDate).put("2", answerAttenDance2);
			answerAttendance.get(eventDate).put("3", answerAttenDance3);

			i++;
		}

		return answerAttendance;
	}


	/**
	 * 回答結果のヘッダーを取得する
	 * @param eventDateList
	 * @return ヘッダー項目
	 */
	@Override
	public List<String> getHeaderList(List<Map<String,Object>> eventDateList) {

		//ヘッダー項目リスト
		List<String> headerList = new ArrayList<>();

		//氏名を取得
		headerList.add("氏名");

		//候補日を取得
		for(Map<String,Object> eventDateMap : eventDateList) {
			String eventDate = eventDateMap.get("EVENT_DATE").toString();
			headerList.add(eventDate);
		}

		//コメントを取得
		headerList.add("コメント");

		return headerList;

	}

	/**
	 * 回答結果内容を取得する
	 * @param answerAttendForm
	 * @param eventDateList
	 * @return 回答情報リスト
	 */
	@Override
	public List<List<String>> getAnswerList(AnswerAttendForm answerAttendForm, List<Map<String,Object>> eventDateList){

		//回答者ごとの回答情報リスト
		List<List<String>> answerList = new ArrayList<>();

		//回答者リストを取得
		String eventId = answerAttendForm.getEventId();
		List<Map<String,Object>> nameList = answerAttendAccessor.getAnswerUserName(eventId);

		for(Map<String, Object> name : nameList) {

			//回答者ごとの回答情報を取得する
			List<String> answer = new ArrayList<>();

			//回答者を取得
			String userName = name.get("ANSWER_USER_NAME").toString();
			answer.add(userName);

			//候補日を取得
			for(Map<String,Object> eventDateMap : eventDateList) {
				String eventDate = eventDateMap.get("EVENT_DATE").toString();

				//候補日と氏名を指定して回答を取得
				Map<String,Object> Answer = answerAttendAccessor.getAnswerAttendance(eventDate, userName);
				String answerAttendance= Answer.get("ANSWER_ATTENDANCE").toString();

				//DBから取得した回答を画面に表示する値に変換
				if(StringUtils.equals(answerAttendance, "1")) {
					answer.add("○");
				} else if(StringUtils.equals(answerAttendance, "2")) {
					answer.add("△");
				} else if(StringUtils.equals(answerAttendance, "3")) {
					answer.add("×");
				}
			}

			//コメントを取得
			Map<String,Object> commentMap = answerAttendAccessor.getComment(eventId, userName);
			String comment = commentMap.get("ANSWER_USER_COMMENT").toString();
			answer.add(comment);

			//回答情報を追加
			answerList.add(answer);
		}

		return answerList;
	}
}

