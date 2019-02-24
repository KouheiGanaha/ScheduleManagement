package com.example.demo.controller;
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

import com.example.demo.form.EventRegistForm;
import com.example.demo.service.EventRegistService;

@Controller
public class EventRegistController {

	@Autowired EventRegistService eventRegistService;



	//初期表示画面
	@RequestMapping({"/","/event/eventRegist"})
	public String index(Model model) {
		model.addAttribute("eventRegistForm", new EventRegistForm());
		return "event/eventRegist";
	}


	//イベント登録処理 修正中
	@PostMapping("/event/create")
	public String create(@ModelAttribute @Validated EventRegistForm eventRegistForm, BindingResult bindingResult, Model model) {
		FieldError eventNameError = bindingResult.getFieldError("eventName");
		FieldError eventDateError = bindingResult.getFieldError("eventDate");

		if(eventNameError != null || eventDateError != null) {
			if(eventNameError != null) {
				model.addAttribute("eventNameError", "イベント名を入力して下さい");
			}

			if(eventDateError != null) {
				model.addAttribute("eventDateError", "候補日を入力してください");
			}

			return "event/eventRegist";

		}
		//候補日の分割
		String[] eventDate = eventRegistService.getEventDate(eventRegistForm);

		//イベントURL生成
		String eventUrl = eventRegistService.getEventUrl();

		Map<String, Object> result = eventRegistService.create(eventRegistForm, eventUrl, eventDate);
		if(result == null) {
			model.addAttribute("eventDateError", "候補日はyyyy/MM//dd HH:mm形式で入力してください");
			return "event/eventRegist";
		}

		model.addAttribute("map", result);
		return "event/eventResult";

	}


}

