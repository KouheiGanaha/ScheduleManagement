package jp.ganaha.schedulemanagement.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver{

	/**
	 * エラー情報を取得
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
		logger.error(ex.getMessage(),ex);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("errorMessage", ex.getMessage());
		modelAndView.setViewName("/error");

		return modelAndView;
	}

}
