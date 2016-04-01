package messagesservice.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import messagesservice.data.Notification;

@Controller
public class MyController extends BaseController {

//	@RequestMapping(value =  "/admin", method = RequestMethod.GET)
//	public ModelAndView homePage(ModelMap model, @ModelAttribute ArrayList<Notification> notifications) {
//		initModel(notifications);
//		
//		return getModel("home");
//	}
//	
//	@RequestMapping(value =  "/user", method = RequestMethod.GET)
//	public ModelAndView homePage2(ModelMap model, @ModelAttribute ArrayList<Notification> notifications) {
//		initModel(notifications);
//		
//		return getModel("home");
//	}
	
}
