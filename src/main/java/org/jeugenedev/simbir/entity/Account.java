package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long id;
    private String username;
    private String password;
    private boolean banned;
    @Column(name = "role")
    private long roleId = 1;

    public Account(String username, String password, boolean banned, long roleId) {
        this.username = username;
        this.password = password;
        this.banned = banned;
        this.roleId = roleId;
    }

    public enum Role {
        ROLE_USER(1), ROLE_ADMIN(2);

        private final long id;

        Role(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public static Role byId(long id) {
            return Arrays.stream(Role.values()).filter(role -> role.id == id).findFirst().orElseThrow();
        }

        public static List<String> toList() {
            return Arrays.stream(Role.values()).map(Enum::name).toList();
        }
    }
}
