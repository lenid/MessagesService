package messagesservice.service;

import java.util.Set;

import messagesservice.model.ShortUser;

public interface ContactService {
	public void addBySsoId(String ssoId);
	public Set<ShortUser> getContactList();
	public Set<ShortUser> getOutOfContactList(String token);
	public void deletebyIds(Set<Integer> contactIdSet);
}
