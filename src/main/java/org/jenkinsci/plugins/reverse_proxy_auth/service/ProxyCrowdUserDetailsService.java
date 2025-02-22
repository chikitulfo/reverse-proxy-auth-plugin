package org.jenkinsci.plugins.reverse_proxy_auth.service;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.jenkinsci.plugins.reverse_proxy_auth.auth.ReverseProxyAuthoritiesPopulator;
import org.jenkinsci.plugins.reverse_proxy_auth.model.ReverseProxyUserDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.jenkinsci.plugins.reverse_proxy_auth.ReverseProxySecurityRealm;

import java.util.logging.Logger;

public class ProxyCrowdUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(ProxyCrowdUserDetailsService.class.getName());

    private ReverseProxyAuthoritiesPopulator authoritiesPopulator;

//    public ProxyCrowdUserDetailsService(ReverseProxySecurityRealm securityRealm, WebApplicationContext appContext) {
//        this.authoritiesPopulator = securityRealm.extractBean(ProxyCrowdAuthoritiesPopulator.class, appContext);
//    }

    public ProxyCrowdUserDetailsService(ProxyCrowdAuthoritiesPopulator authoritiesPopulator) {
        this.authoritiesPopulator = authoritiesPopulator;
    }


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
        ReverseProxyUserDetails proxyUserDetails = new ReverseProxyUserDetails();
        proxyUserDetails.setUsername(username);

        proxyUserDetails.setAuthorities(this.authoritiesPopulator.getGrantedAuthorities(proxyUserDetails));

        return proxyUserDetails;
    }
}
