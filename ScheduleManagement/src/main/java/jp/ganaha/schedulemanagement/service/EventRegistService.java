package jp.ganaha.schedulemanagement.service;

import java.util.Map;

import jp.ganaha.schedulemanagement.form.EventRegistForm;

public interface EventRegistService {

	/**
	 * イベント情報を登録する
	 * @param eventRegistForm
	 * @param eventDate
	 * @return 登録結果
	 */
	public Map<String, Object> create(EventRegistForm eventRegistForm ,String eventDate[]);

	/**
	 * イベントURLのランダム値を生成
	 * @return ランダム値
	 */
	public String getRondom();

	/**
	 * 入力された候補日を改行ごとに分割して取得する
	 * @param eventRegistForm
	 * @return 候補日
	 */
	public String[] getEventDate(EventRegistForm eventRegistForm);

	/**
	 * イベントURLを生成する
	 * @param result
	 * @return イベントURL
	 */
	public String getEventUrl(Map<String, Object> result);
}
