package org.acal.ldap;


import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.extensions.PasswordModifyExtendedRequest;
import com.unboundid.ldap.sdk.extensions.PasswordModifyExtendedResult;
import com.unboundid.ldif.LDIFException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component(value = "unboundidLdapService")
public class UnboundidLdapService implements LdapService {

    private static final String LDAP_DC = "dc=acal,dc=org";
    public static final int PORT = 1636;

    public boolean authenticate(final String username, final String password) {
        try {
            final LDAPConnection ldapConnection = new LDAPConnection("localhost", PORT, "cn=admin", "password");
            BindResult result;
            result = ldapConnection.bind("uid=" + username + ",ou=People," + LDAP_DC, password);
            return result.getResultCode().isConnectionUsable();
        } catch (LDAPException e) {
            return false;
        }
    }

    public Map<String, Object> findUser(final String username) {
        final Filter filter = Filter.createEqualityFilter("ou", "People");
        final SearchRequest searchRequest = new SearchRequest("uid=" + username + ",ou=People," + LDAP_DC, SearchScope.SUB, filter, "mobile", "mail");
        final Map<String, Object> result = new HashMap<String, Object>();
        try {
            final LDAPConnection ldapConnection = new LDAPConnection("localhost", PORT, "cn=admin", "password");
            SearchResult searchResult = ldapConnection.search(searchRequest);
            if (searchResult.getSearchEntries().isEmpty()) {
                throw new RuntimeException("Unable to find user with username " + username);
            }
            if (searchResult.getSearchEntries().size() > 1) {
                throw new RuntimeException("Too many users with username " + username);
            }
            result.put("mail", searchResult.getSearchEntries().get(0).getAttributeValue("mail"));
            result.put("mobile", searchResult.getSearchEntries().get(0).getAttributeValue("mobile"));
        } catch (LDAPException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updatePassword(final String username, final String password, final String newPassword) {
        PasswordModifyExtendedRequest passwordModifyRequest = new PasswordModifyExtendedRequest("uid=" + username + ",ou=People," + LDAP_DC, password, newPassword);
        try {
            final LDAPConnection ldapConnection = new LDAPConnection("localhost", PORT, "cn=admin", "password");
            final PasswordModifyExtendedResult passwordModifyResult = (PasswordModifyExtendedResult) ldapConnection.processExtendedOperation(passwordModifyRequest);
            return passwordModifyResult.getResultCode().equals(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMobile(final String username, final String newMobile) {
        ModifyRequest modifyRequest = null;
        try {
            modifyRequest = new ModifyRequest(
                    "dn: uid=" + username + ",ou=People," + LDAP_DC,
                    "changetype: modify",
                    "replace: mobile",
                    "mobile: " + newMobile);
            final LDAPConnection ldapConnection = new LDAPConnection("localhost", PORT, "cn=admin", "password");
            return ldapConnection.processOperation(modifyRequest).getResultCode().equals(ResultCode.SUCCESS);
        } catch (LDIFException e) {
            e.printStackTrace();
            return false;
        } catch (LDAPException e) {
            e.printStackTrace();
            return false;
        }
    }

}
