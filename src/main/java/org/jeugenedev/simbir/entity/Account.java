package org.jeugenedev.simbir.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.jeugenedev.simbir.configuration.SecurityConfiguration;
import org.jeugenedev.simbir.entity.converter.AccountRoleConverter;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean banned;
    private BigDecimal balance = BigDecimal.ZERO;
    @Convert(converter = AccountRoleConverter.class)
    private Role role = Role.ROLE_USER;
    @JsonIgnore
    @RestResource(exported = false)
    @OneToMany(mappedBy = "owner")
    private List<Transport> transports;
    @JsonIgnore
    @OneToMany(mappedBy = "renter")
    private List<Rent> rents;
    @JsonIgnore
    @OneToMany(mappedBy = "payer")
    private List<Payment> payments;

    public Account(long id) {
        this.id = id;
    }

    public Account(long id, String username, String password, boolean banned, BigDecimal balance, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.banned = banned;
        this.balance = balance;
        this.role = role;
    }

    public Account(String username, String password, boolean banned, BigDecimal balance, Role role) {
        this.username = username;
        this.password = password;
        this.banned = banned;
        this.balance = balance;
        this.role = role;
    }

    public Account(SecurityConfiguration.User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
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
