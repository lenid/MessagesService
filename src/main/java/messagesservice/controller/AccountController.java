package messagesservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import messagesservice.data.Notification;
import messagesservice.model.User;
import messagesservice.model.UserProfile;
import messagesservice.model.User.Type;
import messagesservice.service.UserProfileService;
import messagesservice.service.UserService;
import messagesservice.util.SecurityHelper;

@Controller
public class AccountController extends BaseController {

	private static final Logger logger = Logger.getLogger(AccountController.class);

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model, HttpServletRequest request) {
		Throwable exception = (Throwable) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

		if (exception instanceof BadCredentialsException) {
			model.addAttribute("errorMessage", "login.error.bad_cred");
			logger.warn("User typed bad credential");
		}

		if (exception instanceof AuthenticationServiceException) {
			model.addAttribute("errorMessage", "login.error.not_connection");
			logger.error("Could not get JDBC Connection!", exception);
		}

		if (exception != null) {
			model.addAttribute("error", "true");
		}

		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		logout(request, response);
		return "redirect:/login?logout";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView getSignUpPage(@ModelAttribute ArrayList<Notification> notifications) {
		ModelAndView model = new ModelAndView("user");
		defaultModelInitialize(model, notifications, "user.header.create");
		
		model.addObject("user", new User());

		return model;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String createAccount(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response) {
		List<Notification> notifications = new ArrayList<Notification>();
		
		if (userService.isDuplicatedSsoId(user)) {
			notifications.add(new Notification("user.notification.warning.create.duplicate_sso_id"));
			redirectAttributes.addFlashAttribute(notifications);
			redirectAttributes.addFlashAttribute(user);
			
			return "redirect:/signup";
		}
		
		UserProfile userProfile_user = userProfileService.findByType(Type.USER.toString());
		user.setUserProfiles(new HashSet<UserProfile>(Arrays.asList(userProfile_user)));
		userService.persist(user);
		notifications.add(new Notification("user.notification.success.create"));
		redirectAttributes.addFlashAttribute(notifications);
		authenticateUserAndSetSession(user, request, response);

		return "redirect:/";
	}

	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView getAccount(@ModelAttribute ArrayList<Notification> notifications) {
		ModelAndView model = new ModelAndView("user");
		defaultModelInitialize(model, notifications, "user.header.edit");

		model.addObject("user", userService.findBySso(SecurityHelper.getSso()));
		model.addObject("roles", userProfileService.findAll());

		return model;
	}

	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public String editAccount(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response, @RequestParam String action) {
		List<Notification> notifications = new ArrayList<>();

		try {
			switch (EditType.valueOf(action.toUpperCase())) {
			case ACCOUNT:
				userService.update(user);
				notifications.add(new Notification("user.notification.success.update.account"));
				break;
			case PASSWD:
				if (checkPasswd(user)) {
					userService.update(user);
					notifications.add(new Notification("user.notification.success.update.passwd"));
				} else {
					notifications.add(new Notification("user.notification.warning.update.passwd.wrong_passwd"));
				}
				break;
			case SSO_ID:
				editSsoId(user, notifications, request, response);
				break;
			default:
				logger.error("Unimplemented \"EditType\" value case!");
				break;
			}
		} catch (IllegalArgumentException iae) {
			logger.error("Wrong \"action\" variable value!");
		}

		redirectAttributes.addFlashAttribute(notifications);

		return "redirect:/account";
	}

	@RequestMapping(value = "/accessDenied", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView accessDeniedPage() {
		ModelAndView model = new ModelAndView("accessDenied");
		model.addObject(PAGE_HEADER, "global.http_error.403");
		
//		addMessage("global.notification.warning.access_denied");
		
		return model;
	}
	
	@RequestMapping(value = "/isDuplicatedSsoId", method = RequestMethod.GET)
	public @ResponseBody String isDuplicatedSsoIdAjax(@RequestParam("id") int id, @RequestParam("ssoId") String ssoId) {
		User user = new User();
		user.setId(id);
		user.setSsoId(ssoId);
		boolean isDuplicatedSsoId = userService.isDuplicatedSsoId(user);
		
		return "{ \"valid\": " + !isDuplicatedSsoId + " }";
	}
	
	private void editSsoId(User user, List<Notification> notifications, HttpServletRequest request, HttpServletResponse response) {
		if (!checkPasswd(user)) {
			notifications.add(new Notification("user.notification.warning.update.sso_id.wrong_passwd"));
			return;
		}
		if (userService.isDuplicatedSsoId(user)) {
			notifications.add(new Notification("user.notification.warning.update.duplicate_sso_id"));
			return;
		}

		userService.update(user);
		notifications.add(new Notification("user.notification.success.update.sso_id"));
		authenticateUserAndSetSession(user, request, response);
	}

	private void authenticateUserAndSetSession(User user, HttpServletRequest request, HttpServletResponse response) {
		logout(request, response);
		String rawPasswd = user.getNewPasswd().equals("") ? user.getOldPasswd() : user.getNewPasswd();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getSsoId(), rawPasswd);
		request.getSession();
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		String securityContext = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
		request.getSession().setAttribute(securityContext, SecurityContextHolder.getContext());
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}

	private boolean checkPasswd(User user) {
		String encodedPassword = userService.findBySso(SecurityHelper.getSso()).getPasswd();
		String rawPassword = user.getOldPasswd();

		if (!new BCryptPasswordEncoder().matches(rawPassword, encodedPassword)) {
			return false;
		}

		return true;
	}

	public enum EditType {
		ACCOUNT("account"), SSO_ID("sso_id"), PASSWD("passwd");

		String type;

		private EditType(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return type;
		}

	}

	// Need test
//	@ModelAttribute("roles")
//	public List<UserProfile> initializeProfiles() {
//		return userProfileService.findAll();
//	}

}

/*
 * Examples
 * 
 * @RequestMapping(value = "/admin", method = RequestMethod.GET) public String
 * adminPage(ModelMap model) { model.addAttribute("user", getPrincipal());
 * return "admin"; }
 * 
 * @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET) public
 * String accessDeniedPage(ModelMap model) { model.addAttribute("user",
 * getPrincipal()); return "accessDenied"; }
 * 
 * This method will be called on form submission, handling POST request It also
 * validates the user input
 * 
 * @RequestMapping(value = "/newUser", method = RequestMethod.POST) public
 * String saveRegistration(@Valid User user, BindingResult result, ModelMap
 * model) {
 * 
 * if (result.hasErrors()) { System.out.println("There are errors"); return
 * "newuser"; } // userService.save(user);
 * 
 * System.out.println("First Name : " + user.getFirstName());
 * System.out.println("Last Name : " + user.getLastName()); System.out.println(
 * "SSO ID : " + user.getSsoId()); System.out.println("Password : " +
 * user.getPasswd()); System.out.println("Email : " + user.getEmail());
 * System.out.println("Checking UsrProfiles...."); if (user.getUserProfiles() !=
 * null) { for (UserProfile profile : user.getUserProfiles()) {
 * System.out.println("Profile : " + profile.getType()); } }
 * 
 * model.addAttribute("success", "User " + user.getFirstName() +
 * " has been registered successfully"); return "registrationsuccess"; }
 * 
 * private String getPrincipal() { String userName = null; Object principal =
 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * if (principal instanceof UserDetails) { userName = ((UserDetails)
 * principal).getUsername(); } else { userName = principal.toString(); } return
 * userName; }
 * 
 */
