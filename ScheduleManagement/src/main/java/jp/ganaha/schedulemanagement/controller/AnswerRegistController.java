package jp.ganaha.schedulemanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@RequestMapping("/event/answerRegist")
	public String answerRegistIndex(@RequestParam("Url") String Url, Model model) {

		System.out.println(Url);
		Map<String, Object> eventData = answerRegistService.getEventData(Url);
		model.addAttribute("eventData",eventData);

		List<Map<String,Object>> eventDate = answerRegistService.getEventDate(eventData);
		model.addAttribute("eventDate",eventDate);

		Map<String,String> getSelectItems = answerRegistService.getSelectItems();
		model.addAttribute("selectItems",getSelectItems);

		model.addAttribute("answerRegistForm", new AnswerRegistForm());
		return "event/answerRegist";
	}

}
