package org.acal.ldap;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import org.acal.ldap.config.LdapConfiguration;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LdapConfiguration.class)
public class SpringLdapServiceIntegrationTest {

    private InMemoryDirectoryServer inMemoryDirectoryServer;

    @Autowired
    @Qualifier("springLdapService")
    private LdapService ldapService;

    @Before
    public  void setup() throws Exception {
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=org");
        config.setListenerConfigs(InMemoryListenerConfig.createLDAPConfig("test", 1636));
        config.setSchema(null); // do not check (attribute) schema
        config.addAdditionalBindCredentials("cn=admin", "password");

        inMemoryDirectoryServer = new InMemoryDirectoryServer(config);
        final ClassPathResource ldif = new ClassPathResource("ldap.ldif");
        inMemoryDirectoryServer.importFromLDIF(true, ldif.getURL().getPath());
        inMemoryDirectoryServer.startListening();
    }

    @After
    public void tearDown() {
        inMemoryDirectoryServer.shutDown(true);
    }

    @Test
    public void shouldAuthenticate() throws Exception {
        assertTrue(ldapService.authenticate("andrea.caldera", "monday123"));
    }

    @Test
    public void shouldNotAuthenticateWithInvalidPassword() throws Exception {
        assertFalse(ldapService.authenticate("andrea.caldera", "invalid"));
    }

    @Test
    public void shouldNotAuthenticateWithNonExistentUsername() throws Exception {
        assertFalse(ldapService.authenticate("non.existent", "password"));
    }

}
