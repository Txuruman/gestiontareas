package navegacion;



import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class bloque1 extends MultiActionController {

	public ModelAndView tarea(HttpServletRequest hsr, HttpServletResponse hsr1)
			throws Exception, ServletException {
		
		String tecla1 = "active";
		String tecla2 = "inactive";
		String tecla3 = "inactive";
		
		ModelAndView mv = new ModelAndView("01-attack");
		mv.addObject("tecla1", tecla1);	
		mv.addObject("tecla2", tecla2);	
		mv.addObject("tecla3", tecla3);	
		
		return mv;

	}

	public ModelAndView buscar(HttpServletRequest hsr, HttpServletResponse hsr1)
			throws Exception, ServletException {
		ModelAndView mv = new ModelAndView("02-search");
		String tecla1 = "inactive";
		String tecla2 = "active";
		String tecla3 = "inactive";
		
	
		mv.addObject("tecla1", tecla1);	
		mv.addObject("tecla2", tecla2);	
		mv.addObject("tecla3", tecla3);	

		return mv;

	}

	public ModelAndView crear(HttpServletRequest hsr, HttpServletResponse hsr1)
			throws Exception, ServletException {
		ModelAndView mv = new ModelAndView("03-create");
		
		String tecla1 = "inactive";
		String tecla2 = "inactive";
		String tecla3 = "active";
		
	
		mv.addObject("tecla1", tecla1);	
		mv.addObject("tecla2", tecla2);	
		mv.addObject("tecla3", tecla3);	

		return mv;

	}
}
