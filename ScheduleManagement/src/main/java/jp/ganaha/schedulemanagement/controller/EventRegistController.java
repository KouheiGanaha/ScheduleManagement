package jp.ganaha.schedulemanagement.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.ganaha.schedulemanagement.form.EventRegistForm;
import jp.ganaha.schedulemanagement.service.EventRegistService;

@Controller
public class EventRegistController {

	@Autowired EventRegistService eventRegistService;

	/**
	 * 初期表示画面
	 * @param model
	 * @return イベント登録画面
	 */
	@RequestMapping({"/","/event/eventRegist"})
	public String index(Model model) {
		model.addAttribute("eventRegistForm", new EventRegistForm());
		return "event/eventRegist";
	}


	/**
	 * イベント登録処理
	 * @param eventRegistForm
	 * @param bindingResult
	 * @param model
	 * @return イベントURL
	 */
	@PostMapping("/event/eventResult")
	public String create(@ModelAttribute @Validated EventRegistForm eventRegistForm, BindingResult bindingResult, Model model) {
		FieldError eventNameError = bindingResult.getFieldError("eventName");
		FieldError eventMemoError = bindingResult.getFieldError("eventMemo");
		FieldError eventDateError = bindingResult.getFieldError("eventDate");

		if(eventNameError != null || eventMemoError != null || eventDateError != null) {
			if(eventNameError != null) {
				model.addAttribute("eventNameError", "イベント名は255字以内で入力して下さい");
			}

			if(eventMemoError != null) {
				model.addAttribute("eventMemoError", "イベントメモは1024字以内で入力して下さい");
			}

			if(eventDateError != null) {
				model.addAttribute("eventDateError", "候補日は1024字以内で入力してください");
			}

			return "event/eventRegist";

		}

		//候補日の分割
		String[] eventDate = eventRegistService.getEventDate(eventRegistForm);

		if(eventDate == null) {
			model.addAttribute("eventDateError", "候補日はyyyy/MM/dd HH:mm形式で入力してください");
			return "event/eventRegist";
		}

		//イベント情報登録
		Map<String, Object> result = eventRegistService.create(eventRegistForm,eventDate);
		model.addAttribute("map", result);

		//イベントURL生成
		String eventUrl = eventRegistService.getEventUrl(result);
		eventRegistForm.setEventUrl(eventUrl);
		model.addAttribute("eventUrl", eventUrl);

		return "event/eventResult";

	}

}

