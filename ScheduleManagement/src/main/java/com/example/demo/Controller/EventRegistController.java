package com.example.demo.Controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Form.EventRegistForm;
import com.example.demo.Service.EventRegistService;

@Controller
public class EventRegistController {

	@Autowired EventRegistService eventRegistService;

	//**イベント登録**//
	@RequestMapping("/event/input")
	public String index(Model model) {
		model.addAttribute("eventRegistForm", new EventRegistForm());
		return "event/input";
	}

	@PostMapping("event/create")
	public String create(@ModelAttribute EventRegistForm eventRegistForm, Model model) {
		Map<String, Object> result = eventRegistService.create(eventRegistForm);
		model.addAttribute("map", result);
		return "event/view";

	}

}
