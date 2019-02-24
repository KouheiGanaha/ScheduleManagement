package com.example.demo.service;

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
		String eventDate = eventRegistForm.getEventDate();

		//入力した候補日を改行ごとに分割してeventDateArrayに格納
		String[] eventDateArray = eventDate.split("\r\n");

		return eventDateArray;
	}

	//イベントURL生成
	@Override
	public String getEventUrl() {
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

		int updateCount = eventRegistAccessor.insertEvent(eventRegistForm.getEventName()
		,eventRegistForm.getEventMemo(),eventUrl);

		String eventId = eventRegistAccessor.getEventSequence().get("EVENT_ID").toString();
		String eventIdSeq = eventRegistAccessor.getEventIdSequence().get("EVENT_ID").toString();

		//候補日をDBに登録
		for(String eventDate : eventDateArray) {
			eventRegistAccessor.insertEventDate(eventIdSeq,eventDate);
		}

		Map<String, Object> row = eventRegistAccessor.getEventData(eventId);
		return row;
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
