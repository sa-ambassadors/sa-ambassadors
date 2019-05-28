package io.internhub.application.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserWithRoles extends User implements UserDetails {

    private Role role;
    private boolean enabled;

    public UserWithRoles(User user) {
        super(user);
        role = user.getRole();// Call the copy constructor defined in User
        enabled = user.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        String roles = "ROLE_USER"; // Since we're not using the authorization part of the component
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        String role = grantedAuthorities.get(0).toString();
        if (enabled) {
            role = role + ", ROLE_ISAPPROVED";
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setEnabled (boolean isEnabled) {
        enabled = isEnabled;
    }
}
