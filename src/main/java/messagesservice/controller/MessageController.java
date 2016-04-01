package messagesservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import messagesservice.data.Notification;
import messagesservice.model.Message;
import messagesservice.service.MessageService;
import messagesservice.service.UserService;
import messagesservice.util.SecurityHelper;

@Controller
@RequestMapping(value = "/message")
public class MessageController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	MessageService messageService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Message getMessageAjax(@RequestParam int id) {
		return messageService.findById(id);
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String SendMessage(@ModelAttribute Message message, @RequestParam(value = "recipients") int[] recipientIds, RedirectAttributes redirectAttributes) {
		message.setFrom(userService.findBySso(SecurityHelper.getSso()));
		
		for (int id : recipientIds) {
			message.setId(0);
			message.setTo(userService.findById(id));
			messageService.persist(message);
		}
		
		List<Notification> notifications = new ArrayList<>();
		notifications.add(new Notification("contacts.notification.success.message_send"));
		redirectAttributes.addFlashAttribute(notifications);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String removeMasseges(@RequestParam(value = "messageIds") int[] messageIds, RedirectAttributes redirectAttributes) {
		for (int i = 0; i < messageIds.length; i++) {
			messageService.delete(messageIds[i]);
		}
		
		List<Notification> notifications = new ArrayList<Notification>();
		notifications.add(new Notification("main.notification.success.delete"));
		redirectAttributes.addFlashAttribute(notifications);
		
		return "redirect:/";
	}

}
