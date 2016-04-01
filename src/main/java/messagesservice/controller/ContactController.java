package messagesservice.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import messagesservice.data.Notification;
import messagesservice.model.ShortUser;
import messagesservice.service.ContactService;
import messagesservice.service.UserService;

@Controller
@RequestMapping(value = "/contact")
public class ContactController extends BaseController {

	@Autowired
	ContactService contactService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/outOfContactList", method = RequestMethod.GET)
	public @ResponseBody Set<String> getOutOfContactList(@RequestParam(value = "token") String token) {
		Set<ShortUser> contactList = contactService.getOutOfContactList(token);
		Set<String> ssoIds = new HashSet<String>();

		for (ShortUser user : contactList) {
			ssoIds.add(user.getSsoId());
		}
		
		return ssoIds;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContact(@RequestParam(value = "ssoId") String ssoId, RedirectAttributes redirectAttributes) {
		List<Notification> notifications = new ArrayList<>();
		
		if (!checkExistContact(ssoId)) {
			notifications.add(new Notification("contacts.notification.warning.contact_not_exist"));
		} else if (checkAddedContact(ssoId)) {
			notifications.add(new Notification("contacts.notification.warning.contact_already_added"));
		} else {
			contactService.addBySsoId(ssoId); // wc ssoId
			notifications.add(new Notification("contacts.notification.success.contact_add"));
		}

		redirectAttributes.addFlashAttribute(notifications);
		
		return "redirect:/";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String removeContacts(@RequestParam(value = "contacts") Set<Integer> contactIdSet, RedirectAttributes redirectAttributes) {
		contactService.deletebyIds(contactIdSet);

		List<Notification> notifications = new ArrayList<>();
		notifications.add(new Notification("contacts.notification.success.contacts_delete"));
		redirectAttributes.addFlashAttribute(notifications);
		
		return "redirect:/";
	}
	
	private boolean checkExistContact(String ssoId) {
		if (userService.findBySso(ssoId) == null) {
			return false;
		}
		
		return true;
	}

	private boolean checkAddedContact(String ssoId) {
		Set<ShortUser> contacts = contactService.getContactList();
		for (ShortUser shortUser : contacts) {
			if (shortUser.getSsoId().equals(ssoId)) {
				return true;
			}
		}
		
		return false;
	}
	
}
