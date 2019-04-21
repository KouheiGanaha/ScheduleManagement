package jp.ganaha.schedulemanagement.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ganaha.schedulemanagement.db.EventRegistAccessor;
import jp.ganaha.schedulemanagement.form.EventRegistForm;

@Service
public class EventRegistServiceImpl implements EventRegistService{

	@Autowired
	EventRegistAccessor eventRegistAccessor;

	final static Logger logger =LoggerFactory.getLogger(EventRegistServiceImpl.class);

	/**
	 * 入力された候補日を改行ごとに分割して取得する
	 */
	@Override
	public String[] getEventDate(EventRegistForm eventRegistForm) {

		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String eventDate = eventRegistForm.getEventDate();

		//入力した候補日を改行ごとに分割してeventDateArrayに格納
		String[] eventDateArray = eventDate.split("\r\n");

		for(String eventdate  : eventDateArray) {

			try {
				format.parse(eventdate);
				format.setLenient(false);
			} catch (ParseException e) {
				return null;
			}
		}

		return eventDateArray;
	}

	/**
	 * イベントURLのランダム値を生成
	 */
	@Override
	public String getRondom() {
		String eventUrl = RandomStringUtils.randomAlphanumeric(10);

		try {

			logger.info("getEventUrl start");
			String eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();
			logger.info("eventData end: {}", eventUrlCount);

			//DBに同じランダム値が存在する場合、処理をループさせる
			while(!eventUrlCount.equals("0")) {
				eventUrl = RandomStringUtils.randomAlphanumeric(10);

				logger.info("getEventUrl start");
				eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();
				logger.info("eventData end: {}", eventUrlCount);

				//ランダム値が重複しない場合、ループを抜ける
				if(eventUrlCount.equals("0")) {
					break;
				}
			}
		}catch(RuntimeException e){
			throw new RuntimeException("ランダム値の生成に失敗しました",e);
		}

		return eventUrl;
	}


	/**
	 * イベント情報を登録する
	 */
	@Override
	public Map<String, Object> create(EventRegistForm eventRegistForm, String eventDateArray[]){

		//イベントURLのランダム値を生成
		String RondomNumber = getRondom();

		//シーケンス取得
		int eventId = eventRegistAccessor.getEventId();


		//イベントをDBに登録
		try {
			logger.info("create start");
			int createCount = eventRegistAccessor.insertEvent(eventId,eventRegistForm.getEventName(),eventRegistForm.getEventMemo(),RondomNumber);
			logger.info("create end: {}", createCount);
		}catch(RuntimeException e) {
			throw new RuntimeException("イベント情報の登録に失敗しました",e);
		}

		//候補日をDBに登録
		try {
			for(String eventDate : eventDateArray) {
				logger.info("eventDateCount start");
				int eventDateCount = eventRegistAccessor.insertEventDate(eventId,eventDate);
				logger.info("eventDateCount end: {}", eventDateCount);
			}
		}catch(RuntimeException e) {
			throw new RuntimeException("候補日の登録に失敗しました",e);
		}

		Map<String, Object> row = eventRegistAccessor.getEventData(eventId);
		logger.info("create data: {}", row);

		return row;
	}


	/**
	 * イベントURLを生成する
	 */
	public String getEventUrl(Map<String, Object> result) {
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			throw new RuntimeException("URLの生成に失敗しました",e);
		}
		String eventUrl = localHost.getHostAddress() + ":8080/event/answerRegist?Url=" + result.get("EVENT_URL");

		return eventUrl;
	}

}
