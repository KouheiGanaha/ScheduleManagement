package jp.ganaha.schedulemanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ganaha.schedulemanagement.form.AnswerRegistForm;
import jp.ganaha.schedulemanagement.form.answerAttendForm;
import jp.ganaha.schedulemanagement.service.AnswerAttendService;
import jp.ganaha.schedulemanagement.service.AnswerRegistService;

@Controller
public class AnswerRegistController {

	@Autowired AnswerRegistService answerRegistService;
	@Autowired AnswerAttendService answerAttendService;

	/**
	 * 初期表示画面
	 * @param model
	 * @return 出欠回答画面
	 */
	@RequestMapping("/event/answerRegist")
	public String answerRegistIndex(@RequestParam("Url") String Url, Model model) {
		model.addAttribute("answerRegistForm", new AnswerRegistForm());
		model.addAttribute("Url", Url);

		/**
		 * イベント情報取得
		 */
		Map<String, Object> eventData = answerRegistService.getEventData(Url);
		if(eventData.equals(null)) {
			//ページはありませんに直す
			return "error/404";
		}
		model.addAttribute("eventData",eventData);

		/**
		 * イベントの候補日取得
		 */
		List<Map<String,Object>> eventDate = answerRegistService.getEventDate(eventData);
		model.addAttribute("eventDate",eventDate);

		/**
		 * 回答の選択肢取得
		 */
		Map<String,String> getSelectItems = answerRegistService.getSelectItems();
		model.addAttribute("selectItems",getSelectItems);

		return "event/answerRegist";
	}

	/**
	 * 回答を登録
	 * @param answerRegistForm
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping("/event/answerAttend")
	public String create(@ModelAttribute @Validated AnswerRegistForm answerRegistForm, answerAttendForm answerAttendForm,BindingResult bindingResult, Model model) {

		FieldError answerNameError = bindingResult.getFieldError("answerName");
		FieldError commentError = bindingResult.getFieldError("comment");

		if(answerNameError != null || commentError != null) {
			if(answerNameError != null) {
				model.addAttribute("answerNameError", "氏名は255字以内で入力して下さい");
			}

			if(commentError != null) {
				model.addAttribute("commentError", "コメントは255字以内で入力して下さい");
			}

			return "error";
		}
		answerRegistService.create(answerRegistForm);


		//-------------------以下、出欠参照画面の処理---------------------//


		//イベント情報取得メソッドの引数に渡す為のイベントURLランダム値を取得
		String eventUrl = answerAttendForm.getEventUrl();

		//イベント情報を取得
		Map<String,Object> eventData = answerAttendService.getEventData(eventUrl);
		model.addAttribute("eventData", eventData);
		String eventId = eventData.get("EVENT_ID").toString();

		//候補日を取得
		List<Map<String,Object>> eventDateList = answerAttendService.getEventDate(eventId);
		model.addAttribute("list",eventDateList);

		//集計結果を取得
		Map<String, Map<String, Object>> answerAttendance = answerAttendService.getAnswerAttendance(answerAttendForm, eventDateList);
		model.addAttribute("answerAttendance",answerAttendance);


		//回答結果のヘッダーを取得
		List<String> headerList = answerAttendService.getHeaderList(eventDateList);
		model.addAttribute("header", headerList);

		//回答結果内容を取得
		List<List<String>> answerList = answerAttendService.getAnswerList(answerAttendForm, eventDateList);
		model.addAttribute("answerList", answerList);

		return "event/answerAttend";
	}
}
