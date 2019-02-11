package com.example.demo.Service;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Form.EventRegistForm;
import com.example.demo.db.EventRegistAccessor;

@Service
public class EventRegistServiceImpl implements EventRegistService{

	@Autowired
	EventRegistAccessor eventRegistAccessor;


	@Override
	public Map<String, Object> create(EventRegistForm eventRegistForm) {
		/**
		 * イベントIDを取得
		 */
		String eventId = eventRegistAccessor.getEventSequence().get("EVENT_ID").toString();

		/**
		 * イベントURL生成
		 */
		String eventUrl = RandomStringUtils.randomAlphanumeric(10);

		/**イベントURLの重複チェック**/
		String eventUrlCheck =eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();
		while(eventUrlCheck != "0") {
			eventUrl = RandomStringUtils.randomAlphanumeric(10);
		}

		/**
		 * イベントマスタに登録
		 */
		int updateCount = eventRegistAccessor.insertEvent(eventId,eventRegistForm.getEventName()
				,eventRegistForm.getEventMemo(),eventUrl);

		/**
		 * 改行ごとに候補日を取得して登録する処理
		 */
		String resultDate = eventRegistForm.getEventDate();
		String[] eventDateArray = resultDate.split("\r\n");

		for(int i = 0; i < eventDateArray.length; ++i) {
			String eventDate = eventDateArray[i];
			String eventDateNo = eventRegistAccessor.getEventDateSequence().get("EVENT_DATE_NO").toString();

			eventRegistAccessor.insertEventDate(eventId, eventDateNo, eventDate);
		}

		Map<String, Object> row = eventRegistAccessor.getEventData(eventId);

		return row;

	}


	public Map<String, Object> getResult(String eventId){
		Map<String, Object> row = null;
		row = eventRegistAccessor.getEventData(eventId);
		return row;
	}

}
