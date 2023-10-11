package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
    @Column(name = "account_id")
    private long id;
    private String username, password;
    private boolean banned;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Account(String username, String password, boolean banned, Role role) {
        this.username = username;
        this.password = password;
        this.banned = banned;
        this.role = role;
    }

    public enum Role {
        User, Admin;

        public List<String> toList() {
            return Arrays.stream(Role.values()).map(Enum::name).toList();
        }
    }
}
