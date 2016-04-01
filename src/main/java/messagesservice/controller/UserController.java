package messagesservice.controller;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import messagesservice.data.Notification;
import messagesservice.model.User;
import messagesservice.service.UserProfileService;
import messagesservice.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAll(@ModelAttribute ArrayList<Notification> notifications) {
		ModelAndView model = new ModelAndView("users");
		defaultModelInitialize(model, notifications, "users.header");

		Set<User> users = userService.getAllActived();
		model.addObject("users", users);
		model.addObject("user", new User());

		return model;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getUserAjax(@PathVariable int userId) {
		ModelAndView model = new ModelAndView("modal/userForm");

		User user = null;
		if (userId == 0) {
			user = new User();
			model.addObject("userHeader", "users.popup_user.header.create");
		} else {
			user = userService.findById(userId);
			user.setContacts(null);
			model.addObject("userHeader", "users.popup_user.header.edit");
		}

		model.addObject("user", user);
		model.addObject("roles", userProfileService.findAll());

		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createOrUpdateUserAjax(@ModelAttribute("user") User user) {
		if (userService.isDuplicatedSsoId(user)) {

		}

		if (user.getId() == 0) {
			userService.persist(user);
		} else {
			userService.update(user);
		}
		return "redirect:/user";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteUserAjax(@ModelAttribute("user") User user) {
		userService.delete(user.getId());
		return "redirect:/user";
	}

}
