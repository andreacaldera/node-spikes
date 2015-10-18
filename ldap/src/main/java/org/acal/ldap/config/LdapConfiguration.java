package org.acal.ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@ComponentScan(basePackages = "org.acal.ldap")
public class LdapConfiguration {

    @Bean
    public LdapTemplate ldapTemplate() {
        final LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://localhost:1636");
        ldapContextSource.setBase("dc=acal,dc=org");
        ldapContextSource.setUserDn("cn=admin");
        ldapContextSource.setPassword("password");
        ldapContextSource.afterPropertiesSet();
        return new LdapTemplate(ldapContextSource);
    }

}
