package messagesservice.dbinit;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import messagesservice.dbinit.DbInitConfiguration.MessageServiceDbInit;
import messagesservice.dbinit.DbInitConfiguration.UserProfileServiceDbInit;
import messagesservice.model.Message;
import messagesservice.model.ShortUser;
import messagesservice.model.User;
import messagesservice.model.User.Type;
import messagesservice.model.UserProfile;
import messagesservice.service.UserProfileService;
import messagesservice.service.UserService;

public class DbDataGen {
	private static final String LOGIN = "admin";
	private static final String PASSWD = "pass";

	private UserService userService;
	private UserProfileService userProfileService;
	private UserProfileServiceDbInit userProfileDaoInit;
	private MessageServiceDbInit messageService;
//	private ShortUserService shortUserService;
	private ApplicationContext ctx;

	private DbDataGen() {
		ctx = new AnnotationConfigApplicationContext(DbInitConfiguration.class);

		userService = ctx.getBean(UserService.class);
		userProfileService = ctx.getBean(UserProfileService.class);
		userProfileDaoInit = ctx.getBean(UserProfileServiceDbInit.class);
		messageService = ctx.getBean(MessageServiceDbInit.class);
		
	}

	public static void main(String[] args) throws Throwable {
//		new DbDataGen().dbInit();
		new DbDataGen().test5();
	}

	void test5() {
//		Set<ShortUser> x = userService.getOutOfContactListAsString(userService.findBySso(LOGIN), "%er%", 5);
	}
	
	void test4() {
//		Formatter formatter = new Formatter();
		int a = 5;
		String x = "Successfully deleted %d post(s)";
//		formatter.format(x, a);
		String str = String.format(x, a);
		System.out.println(str);
		
	}
	
	void test3() throws Throwable {
//		ShortUser u = shortUserService.findById(4);
		ObjectMapper mapper = new ObjectMapper();
//		String json = mapper.writeValueAsString(u);
//		System.out.println(json);
	}
	
	void test2() throws Throwable {
		ShortUser u = new ShortUser();
		
//		u.setEmail("email");
//		u.setFirstName("firstName");
//		u.setLastName("lastName");
//		u.setSsoId("ssoId");
//		shortUserService.persist(u);

//		u = shortUserService.findById(4);
		System.out.println();
		
	}
	
	void test() throws Throwable {
		User user = userService.findBySso("user_9");

		ObjectMapper mapper = new ObjectMapper();

		System.out.println("================================================================");
		String json = mapper.writeValueAsString(user);
		System.out.println(json);

		User u = mapper.readValue(json, User.class);

		u.setLastName(new Date().toString());
		
		Message message = new Message();
		message.setFrom(userService.findBySso("user_8"));
		message.setTo(userService.findBySso("user_9"));
		message.setSubject("subject");
		message.setBody("BODY2");
		
//		u.setMessagesIn(new HashSet<Message>(Arrays.asList(message)));

		userService.update(u);
		
		System.out.println();
	}

	public void dbInit() {
		messageService.deleteAll();

		createUserProfile();

		createUser(LOGIN, PASSWD, "Andriy", Type.ADMIN);
		createUser("user", PASSWD, "Ivan", Type.USER);
		for (int i = 3; i < 10; i++) {
			createUser("user_" + i, PASSWD, "name_" + i, Type.USER);
		}

		createAddressList(LOGIN, "user_3", "user_4");

		String bodyText = "Hello! \n Hoew Are you? \n gj j jklej bktjg jkgj bkgej knkfldvklej jklfejg kejg kljklf;j kl;j kef kl";
		
		createMessages(LOGIN, "user", "from admin to user", bodyText);
		createMessages(LOGIN, "user", "from admin to user second", bodyText);
		createMessages(LOGIN, "user_3", "from admin to user_3", bodyText);
		createMessages("user", LOGIN, "from user to admin", bodyText);

		for (int i = 4; i < 25; i++) {
			createMessages("user", LOGIN, "from admin to user_" + i, bodyText);
		}
		
		((ConfigurableApplicationContext) ctx).close();
	}

	private void createUserProfile() {
		Type[] types = Type.values();

		for (Type userProfileType : types) {
			UserProfile userProfile = userProfileService.findByType(userProfileType.toString());

			if (userProfile == null) {
				userProfile = new UserProfile();
				userProfile.setType(userProfileType.toString());
				userProfileDaoInit.save(userProfile);
			}
		}
	}

	private void createUser(String login, String passwd, String firstName, Type userType) {
		User user = userService.findBySso(login);

		if (user != null) {
			userService.delete(user.getId());
		}

		user = new User();
		user.setSsoId(login);
		user.setNewPasswd(passwd);
		user.setFirstName(firstName);
		user.setLastName("Losoviy");
		user.setEmail(login + "@mail.ru");

		UserProfile userProfile = userProfileService.findByType(userType.toString());
		user.setUserProfiles(new HashSet<UserProfile>(Arrays.asList(userProfile)));

		userService.persist(user);
	}

	private void createAddressList(String login, String login1, String login2) {
//		User user = userService.findBySso(login);
//		User user1 = userService.findBySso(login1);
//		User user2 = userService.findBySso(login2);
//
//		user.setContacts(new HashSet<ShortUser>(Arrays.asList(user1, user2)));
//		userService.update(user);
//
//		user1.setContacts(new HashSet<ShortUser>(Arrays.asList(user)));
//		userService.update(user1);
	}

	private void createMessages(String from, String to, String subject, String body) {
		Message message = new Message();
		message.setFrom(userService.findBySso(from));
		message.setTo(userService.findBySso(to));
		message.setSubject(subject);
		message.setBody(body);

		messageService.persist(message);
//		sleep(1000);
	}
	
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
