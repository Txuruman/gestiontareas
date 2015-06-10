package pkts1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class arranque implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest hsr,
			HttpServletResponse hsr1) throws Exception {
		
		String tecla1 = "active";
		String tecla2 = "inactive";
		String tecla3 = "inactive";
		
		ModelAndView mv = new ModelAndView("01-attack");
		mv.addObject("tecla1", tecla1);	
		mv.addObject("tecla2", tecla2);	
		mv.addObject("tecla3", tecla3);	
		return mv;
	}

}
