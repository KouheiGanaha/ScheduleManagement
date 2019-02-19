package com.example.demo.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		/**
		 * イベントIDを取得
		 */
		String eventId = eventRegistAccessor.getEventSequence().get("EVENT_ID").toString();

		/**イベントURLのランダム値部分を生成し、DBに重複する値が無いかチェック**/
		String eventUrl = RandomStringUtils.randomAlphanumeric(10);
		String eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();

		/**DBに同じランダム値が存在する場合、処理をループさせる**/
		while(!eventUrlCount.equals("0")) {
			eventUrl = RandomStringUtils.randomAlphanumeric(10);
			eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();

			/**ランダム値が重複しない場合、ループを抜ける**/
			if(eventUrlCount.equals("0")) {
				break;
			}

		}
		/**
		 * イベントマスタに登録
		 */
		int updateCount = eventRegistAccessor.insertEvent(eventRegistForm.getEventName()
				,eventRegistForm.getEventMemo(),eventUrl);


		/**
		 * 改行ごとに候補日を取得して登録する処理
		 */
		String resultDate = eventRegistForm.getEventDate();
		String[] eventDateArray = resultDate.split("\r\n");
		String eventIdSeq = eventRegistAccessor.getEventIdSequence().get("EVENT_ID").toString();

		for(int i = 0; i < eventDateArray.length; ++i) {
			String eventDate = eventDateArray[i];

			/**候補日の形式チェック**/
			try {
				format.parse(eventDate);
				format.setLenient(false);
			} catch (ParseException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				/**候補日の形式が不正の場合、nullを返す**/
				Map<String, Object> result = null;
				return result;
			}
		}

		/**候補日を１つずつDBに登録していく**/
		for(int i = 0; i < eventDateArray.length; ++i) {
		String eventDate = eventDateArray[i];
		eventRegistAccessor.insertEventDate(eventIdSeq,eventDate);
		}

		Map<String, Object> row = eventRegistAccessor.getEventData(eventId);
		return row;

	}

	public Map<String, Object> getResult(String eventId){
		Map<String, Object> row = null;
		row = eventRegistAccessor.getEventData(eventId);
		return row;
	}

	/**
	 *候補日を分割するメソッド
	 */
	public String[] eventDate(EventRegistForm eventRegistForm) {
		//日時形式を定義
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		//入力された候補日を取得
		String resultDate = eventRegistForm.getEventDate();

		//取得した候補日を改行ごとに分割して配列に格納
		String[] eventDateArray = resultDate.split("\r\n");


		//eventDateArrayに格納した候補日が正しい形式か一つずつチェック
		for(int i = 0; i < eventDateArray.length; ++i) {
			String eventDate = eventDateArray[i];
			try {
				format.parse(eventDate);
				format.setLenient(false);
			} catch (ParseException e) {
				e.printStackTrace();
				/**候補日の形式が不正の場合、nullを返す**/
				String[] result = null;
				return result;
			}
		}
		return eventDateArray;

	};

	//イベントURLのランダム部分を生成
	public String getEventUrl() {

		/**イベントURLのランダム値部分を生成し、DBに重複する値が無いかチェック**/
		String eventUrl = RandomStringUtils.randomAlphanumeric(10);
		String eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();

		/**DBに同じランダム値が存在する場合、処理をループさせる**/
		while(!eventUrlCount.equals("0")) {
			eventUrl = RandomStringUtils.randomAlphanumeric(10);
			eventUrlCount =  eventRegistAccessor.getEventUrl(eventUrl).get("EVENT_URL").toString();

			/**ランダム値が重複しない場合、ループを抜ける**/
			if(eventUrlCount.equals("0")) {
				break;
			}
		}
		return eventUrl;
	}
}
