package jp.ganaha.schedulemanagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ganaha.schedulemanagement.form.AnswerRegistForm;
import jp.ganaha.schedulemanagement.service.AnswerRegistService;

@Controller
public class AnswerRegistController {

	@Autowired AnswerRegistService answerRegistService;

	/**
	 * 初期表示画面
	 * @param model
	 * @return 出欠回答画面
	 */
	@RequestMapping(value="event/answerRegist/{eventUrl}",method=RequestMethod.GET)
	public String index(@PathVariable("eventUrl") String eventUrl, Model model) {
		Map<String, Object> eventData = answerRegistService.getEventData(eventUrl);
		model.addAttribute("eventData",eventData);
		model.addAttribute("answerRegistForm", new AnswerRegistForm());
		return "event/answerRegist";
	}

	@PostMapping("/event/create")
	public String create(@ModelAttribute @Validated AnswerRegistForm answerRegistForm, BindingResult bindingResult, Model model) {

		return "event/AnswerAttend";
	}


}
