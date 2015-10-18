package org.acal.ldap;


import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFException;
import org.acal.ldap.config.LdapConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LdapConfiguration.class)
public class UnboundidLdapServiceIntegrationTest {

    @Autowired
    @Qualifier("unboundidLdapService")
    private UnboundidLdapService ldapService;
    private InMemoryDirectoryServer inMemoryDirectoryServer;

    @Before
    public void setup() throws Exception {
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=org");
        config.setListenerConfigs(InMemoryListenerConfig.createLDAPConfig("test", 1636));
        config.setSchema(null); // do not check (attribute) schema
        config.addAdditionalBindCredentials("cn=admin", "password");

        inMemoryDirectoryServer = new InMemoryDirectoryServer(config);
        final ClassPathResource ldif = new ClassPathResource("ldap.ldif");
        inMemoryDirectoryServer.importFromLDIF(true, ldif.getURL().getPath());
        inMemoryDirectoryServer.startListening();
        ldapService = new UnboundidLdapService();
    }

    @After
    public void tearDown() {
        inMemoryDirectoryServer.shutDown(true);
    }

    @Test
    public void shouldAuthenticate() throws LDAPException {
        assertTrue(ldapService.authenticate("andrea.caldera", "monday123"));
    }

    @Test
    public void shouldFindUser() throws LDAPException {
        final Map<String, Object> userData = ldapService.findUser("andrea.caldera");
        assertEquals(userData.get("mobile"), "1234567890");
        assertEquals(userData.get("mail"), "acaldera@equalexperts.com");
    }

    @Test
    public void shouldNotAuthenticateWithInvalidPassword() throws LDAPException {
        assertFalse(ldapService.authenticate("andrea.caldera", "invalid"));
    }

    @Test
    public void shouldNotAuthenticateWithNonExistentUsername() throws LDAPException {
        assertFalse(ldapService.authenticate("non.existent", "invalid"));
    }

    @Test
    public void shouldChangePassword() throws LDAPException {
        assertTrue(ldapService.updatePassword("andrea.caldera", "monday123", "monday1234"));
        assertTrue(ldapService.authenticate("andrea.caldera", "monday1234"));
        assertTrue(ldapService.updatePassword("andrea.caldera", "monday1234", "monday123"));
    }

    @Test
    public void shouldChangeMobile() throws LDAPException, LDIFException {
        final String newMobile = "0987654321";

        final Map<String, Object> userData = ldapService.findUser("andrea.caldera");
        assertEquals(userData.get("mobile"), "1234567890");

        ldapService.updateMobile("andrea.caldera", newMobile);
        assertEquals(ldapService.findUser("andrea.caldera").get("mobile"), newMobile);
    }

}
