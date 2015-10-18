package org.acal.ldap;

import java.util.Map;

public interface LdapService {

    public boolean authenticate(final String username, final String password);

    public Map<String, Object> findUser(final String username);

    public boolean updatePassword(final String username, final String password, final String newPassword);

    public boolean updateMobile(final String username, final String newMobile);

}
