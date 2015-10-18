package org.acal.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component(value = "springLdapService")
public class SpringLdapService implements LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public boolean authenticate(String username, String password) {
        return ldapTemplate.authenticate("", "(uid=" + username + ")", password);
    }

    @Override
    public Map<String, Object> findUser(String username) {
        return null;
    }

    @Override
    public boolean updatePassword(String username, String password, String newPassword) {
        return false;
    }

    @Override
    public boolean updateMobile(String username, String newMobile) {
        return false;
    }
}
