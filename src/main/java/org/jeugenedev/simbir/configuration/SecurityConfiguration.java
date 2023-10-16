package org.jeugenedev.simbir.configuration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PropertySource("/logic.properties")
@Configuration
public class SecurityConfiguration {
    @Value("${auth.username.param}")
    private String usernameParam;
    @Value("${auth.password.param}")
    private String passwordParam;
    private final JWTUtils jwtUtils;

    public SecurityConfiguration(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers("/accounts/me").authenticated()
                            .requestMatchers("/accounts/**").hasRole("ADMIN")
                            .anyRequest().permitAll();
                })
                .build();
    }

    public Filter jwtFilter() {
        return new OncePerRequestFilter() {
            private final Pattern TOKEN = Pattern.compile("Bearer (.*)");
            private final String ROLE_KEY = "role";

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws ServletException, IOException {
                String jwtAuth = request.getHeader("Authorization");
                Matcher token;
                if (jwtAuth != null && (token = this.TOKEN.matcher(jwtAuth)).find()) {
                    String jwt = token.group(1);
                    JWTUtils.AccountToken accountToken = jwtUtils.verifyToken(jwt);
                    if (accountToken.verified()) {
                        String username = accountToken.jwt().getClaim(usernameParam).asString();
                        String password = accountToken.jwt().getClaim(passwordParam).asString();
                        String role = accountToken.jwt().getClaim(ROLE_KEY).asString();
                        UserDetails userDetails = new User(new Account(username, password, false, Account.Role.valueOf(role).getId()));
                        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

                chain.doFilter(request, response);
            }
        };
    }

    public static class User implements UserDetails {
        private final Account account;

        public User(Account account) {
            this.account = account;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singleton(new SimpleGrantedAuthority(Account.Role.byId(account.getRoleId()).name()));
        }

        @Override
        public String getPassword() {
            return account.getPassword();
        }

        @Override
        public String getUsername() {
            return account.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return !account.isBanned();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
