package messagesservice.dao;

import java.util.Set;

import messagesservice.model.UserProfile;

public interface UserProfileDao {
	Set<UserProfile> getAll();
	UserProfile findById(int id);
	UserProfile findByType(String type);
}
