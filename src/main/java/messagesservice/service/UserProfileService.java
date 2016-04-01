package messagesservice.service;

import java.util.Set;

import messagesservice.model.UserProfile;

public interface UserProfileService {
	Set<UserProfile> findAll();
	UserProfile findById(int id);
	UserProfile findByType(String type);
}
