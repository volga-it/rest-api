package org.jeugenedev.simbir.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeugenedev.simbir.entity.converter.AccountRoleConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean banned;
    @Convert(converter = AccountRoleConverter.class)
    @Column(name = "role")
    private Role role = Role.ROLE_USER;

    public Account(String username, String password, boolean banned, Role role) {
        this.username = username;
        this.password = password;
        this.banned = banned;
        this.role = role;
    }

    public void matchUpdate(Map<String, String> update) {
        setUsername(update.getOrDefault("username", getUsername()));
        setPassword(update.getOrDefault("password", getPassword()));
    }

    public enum Role {
        ROLE_USER(1), ROLE_ADMIN(2);

        private final int id;

        Role(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Role byId(int id) {
            return Arrays.stream(Role.values()).filter(role -> role.id == id).findFirst().orElseThrow();
        }

        public static List<String> toList() {
            return Arrays.stream(Role.values()).map(Enum::name).toList();
        }
    }
}
