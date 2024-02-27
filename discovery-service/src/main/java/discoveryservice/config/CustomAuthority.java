package discoveryservice.config;

import org.springframework.security.core.GrantedAuthority;

public class CustomAuthority implements GrantedAuthority {

    private String authority;

    public CustomAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
