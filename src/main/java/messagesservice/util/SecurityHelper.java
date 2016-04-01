package messagesservice.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import messagesservice.model.User.Type;

public class SecurityHelper {

	public static final String ROLE_PREFIX = "ROLE_";
	
	public static String getSso() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null) {
			return "null";
		} else if (principal instanceof User) {
			User user = (User) principal;
			return user.getUsername();
		} else {
			return principal.toString();
		}
	}

	public static List<String> getUserRoles() {
		List<String> roles = new ArrayList<>();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal instanceof User) {
				User user = (User) principal;
				for (GrantedAuthority authority : user.getAuthorities()) {
					roles.add(authority.getAuthority());
				}
			}
		}
		return roles;
	}

	public static Type userRoleToType(String role) {
		for (Type userType : Type.values()) {
			if (role.equals(ROLE_PREFIX + userType.name()))
				return userType;
		}
		return null;
	}

	public static boolean isAdmin() {
		for (String role : getUserRoles()) {
			if (Type.ADMIN == userRoleToType(role)) {
				return true;
			}
		}
		return false;
	}

	public static String getHashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
}
