package jp.ganaha.schedulemanagement.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ganaha.schedulemanagement.db.EventRegistAccessor;
import jp.ganaha.schedulemanagement.form.EventRegistForm;

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

		try {

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
		}catch(RuntimeException e){
			throw new RuntimeException("ランダム値の生成に失敗しました",e);
		}

		return eventUrl;
	}


	@Override
	public Map<String, Object> create(EventRegistForm eventRegistForm, String eventRondomNumber, String eventDateArray[]){

		//シーケンス取得
		int eventId = eventRegistAccessor.getEventId();

		//イベントをDBに登録
		try {
			eventRegistAccessor.insertEvent(eventId,eventRegistForm.getEventName(),eventRegistForm.getEventMemo(),eventRondomNumber);
		}catch(RuntimeException e) {
			throw new RuntimeException("イベント情報の登録に失敗しました",e);
		}

		//候補日をDBに登録
		try {
			for(String eventDate : eventDateArray) {
				eventRegistAccessor.insertEventDate(eventId,eventDate);
			}
		}catch(RuntimeException e) {
			throw new RuntimeException("候補日の登録に失敗しました",e);
		}

		Map<String, Object> row = eventRegistAccessor.getEventData(eventId);
		return row;
	}


	//URLを生成する
	public String getEventUrl(String eventRondomNumber) {
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String eventUrl = localHost.getHostAddress() + ":8080/event/answerRegist?Url=" + eventRondomNumber ;

		return eventUrl;
	}

}
