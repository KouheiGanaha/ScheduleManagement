package jp.ganaha.schedulemanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.ganaha.schedulemanagement.service.AnswerAttendService;

@Controller
public class AnswerAttendController {
	@Autowired AnswerAttendService answerAttendService;

	@RequestMapping("/event/answerAttend")
	public String create(@ModelAttribute("eventUrl") String eventUrl, Model model) {

		//イベント情報を取得
		Map<String,Object> eventData = answerAttendService.getEventData(eventUrl);
		String eventId = eventData.get("EVENT_ID").toString();
		model.addAttribute("eventData", eventData);

		//候補日を取得
		List<Map<String,Object>> eventDateList = answerAttendService.getEventDate(eventId);

		//集計結果を取得
		Map<String, Map<String, Object>> answerAttendance = answerAttendService.getAnswerAttendance(eventId, eventDateList);
		model.addAttribute("answerAttendance",answerAttendance);

		//回答結果のヘッダーを取得
		List<String> headerList = answerAttendService.getHeaderList(eventDateList);
		model.addAttribute("header", headerList);

		//回答結果内容を取得
		List<List<String>> answerList = answerAttendService.getAnswerList(eventId, eventDateList);
		model.addAttribute("answerList", answerList);

		return "event/answerAttend";
	}

}
