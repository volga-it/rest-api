package org.jeugenedev.simbir.configuration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.repository.BannedTokenRepository;
import org.jeugenedev.simbir.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PropertySource("/logic.properties")
@Configuration
public class SecurityConfiguration {
    @Value("${auth.username.param}")
    private String usernameParam;
    private final JWTUtils jwtUtils;
    private final BannedTokenRepository bannedTokenRepository;

    public SecurityConfiguration(JWTUtils jwtUtils, BannedTokenRepository bannedTokenRepository) {
        this.jwtUtils = jwtUtils;
        this.bannedTokenRepository = bannedTokenRepository;
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
                            .requestMatchers("/accounts/me", "/accounts/me/update").authenticated()
                            .requestMatchers("/accounts/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/accounts").permitAll()
                            .requestMatchers("/auth/token", "/auth/deny/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/transports", "/transports/*").permitAll()
                            .requestMatchers(HttpMethod.POST, "/transports").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/transports/*").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/transports/*").authenticated()
                            .requestMatchers(HttpMethod.GET, "/rents/*", "/rents/history/me", "/rents/history/transport/*").authenticated()
                            .requestMatchers("/rents/transport/*", "/close/*").authenticated()
                            .requestMatchers("/rents/transport").permitAll()
                            .requestMatchers("/payments/hesoyam/*", "/payments/close/*").authenticated()

                            .requestMatchers("/api/Admin/Account", "/api/Admin/Account/*",
                                    "/api/Admin/Account", "/api/Admin/Account/*", "/api/Admin/Account/*",
                                    "/api/Admin/Transport", "/api/Admin/Transport/*", "/api/Admin/Transport",
                                    "/api/Admin/Transport/*", "/api/Admin/Transport/*", "/api/Admin/Rent/*",
                                    "/api/Admin/UserHistory/*", "/api/Admin/TransportHistory/*",
                                    "/api/Admin/Rent", "/api/Admin/Rent/End/*", "/api/Admin/Rent/*",
                                    "/api/Admin/Rent/*").hasRole("ADMIN")

                            .anyRequest().authenticated();
                })
                .build();
    }

    public Filter jwtFilter() {
        return new OncePerRequestFilter() {
            private final Pattern TOKEN = Pattern.compile("Bearer (.*)");

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws ServletException, IOException {
                String jwtAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
                Matcher token;
                if (jwtAuth != null && (token = this.TOKEN.matcher(jwtAuth)).find()) {
                    String jwt = token.group(1);
                    JWTUtils.AccountToken accountToken = jwtUtils.verifyToken(jwt);
                    if (accountToken.verified()) {
                        String encodedPayload = Base64.getUrlEncoder().encodeToString(accountToken.jwt().getPayload().getBytes(StandardCharsets.UTF_8));
                        boolean blocked = bannedTokenRepository.existsByTokenBase64Payload(encodedPayload);
                        if (!blocked) {
                            String username = accountToken.jwt().getClaim(usernameParam).asString();
                            String role = accountToken.jwt().getClaim(JWTUtils.KEY_ROLE).asString();
                            long id = accountToken.jwt().getClaim(JWTUtils.KEY_USER_ID).asLong();
                            UserDetails userDetails = new User(new Account(id, username, null, false, null, Account.Role.valueOf(role)));
                            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
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

        public long getId() {
            return this.account.getId();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singleton(new SimpleGrantedAuthority(account.getRole().name()));
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
