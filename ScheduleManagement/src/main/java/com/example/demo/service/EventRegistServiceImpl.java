package com.example.demo.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.EventRegistAccessor;
import com.example.demo.form.EventRegistForm;

@Service
public class EventRegistServiceImpl implements EventRegistService{

	@Autowired
	EventRegistAccessor eventRegistAccessor;


	//改行ごとに候補日を分割して取得する
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
				e.printStackTrace();
				return null;
			}
		}

		return eventDateArray;
	}



	//イベントURLのランダム値を生成
	@Override
	public String getRondom() {
		String eventUrl = RandomStringUtils.randomAlphanumeric(10);
		String eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();

		//DBに同じランダム値が存在する場合、処理をループさせる
		while(!eventUrlCount.equals("0")) {
			eventUrl = RandomStringUtils.randomAlphanumeric(10);
			eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();

			//ランダム値が重複しない場合、ループを抜ける
			if(eventUrlCount.equals("0")) {
				break;
			}
		}
		return eventUrl;
	}


	@Override
	public Map<String, Object> create(EventRegistForm eventRegistForm, String eventUrl, String eventDateArray[]){

		String eventId = eventRegistAccessor.getEventSequence().get("EVENT_ID").toString();
		String eventIdSeq = eventRegistAccessor.getEventIdSequence().get("EVENT_ID").toString();


		//イベントをDBに登録
		try {
			eventRegistAccessor.insertEvent(eventRegistForm.getEventName(),eventRegistForm.getEventMemo(),eventUrl);
		}catch(RuntimeException e) {
			throw new RuntimeException("イベント情報の登録に失敗しました");
		}

		//候補日をDBに登録
		try {
			for(String eventDate : eventDateArray) {
				eventRegistAccessor.insertEventDate(eventIdSeq,eventDate);
				}
			}catch(RuntimeException e) {
				throw new RuntimeException("候補日の登録に失敗しました");
			}

		Map<String, Object> row = eventRegistAccessor.getEventData(eventId);
		return row;
	}


	//URLを生成する
	public String getEventUrl() {
		String eventUrl = null;
		try {
			InetAddress localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

		return eventUrl;
	}

	//	public Map<String, Object> eventCreate(EventRegistForm eventRegistForm)
	//
	//
	//	@Override
	//	public Map<String, Object> create(EventRegistForm eventRegistForm) {
	//		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	//
	//		/**
	//		 * イベントIDを取得
	//		 */
	//		String eventId = eventRegistAccessor.getEventSequence().get("EVENT_ID").toString();
	//
	//		/**イベントURLのランダム値部分を生成し、DBに重複する値が無いかチェック**/
	//		String eventUrl = RandomStringUtils.randomAlphanumeric(10);
	//		String eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();
	//
	//		/**DBに同じランダム値が存在する場合、処理をループさせる**/
	//		while(!eventUrlCount.equals("0")) {
	//  		eventUrl = RandomStringUtils.randomAlphanumeric(10);
	//			eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();
	//
	//			/**ランダム値が重複しない場合、ループを抜ける**/
	//			if(eventUrlCount.equals("0")) {
	//				break;
	//			}
	//
	//		}
	//		/**
	//		 * イベントマスタに登録
	//		 */
	//		int updateCount = eventRegistAccessor.insertEvent(eventRegistForm.getEventName()
	//				,eventRegistForm.getEventMemo(),eventUrl);
	//
	//
	//		/**
	//		 * 改行ごとに候補日を取得して登録する処理
	//		 */
	//		String resultDate = eventRegistForm.getEventDate();
	//		String[] eventDateArray = resultDate.split("\r\n");
	//		String eventIdSeq = eventRegistAccessor.getEventIdSequence().get("EVENT_ID").toString();
	//
	//
	//		for(String eventdate  : eventDateArray) {
	//			System.out.println(eventdate);
	//
	//			try {
	//				format.parse(eventdate);
	//				format.setLenient(false);
	//			} catch (ParseException e) {
	//				e.printStackTrace();
	//				return null;
	//			}	}
	//
	//		/**候補日を１つずつDBに登録していく**/
	//		for(int i = 0; i < eventDateArray.length; ++i) {
	//		String eventDate = eventDateArray[i];
	//			eventRegistAccessor.insertEventDate(eventIdSeq,eventDate);
	//		}
	//
	//		Map<String, Object> row = eventRegistAccessor.getEventData(eventId);
	//		return row;
	//
	//	}


}
