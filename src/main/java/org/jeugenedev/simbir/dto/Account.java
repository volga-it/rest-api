package org.jeugenedev.simbir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeugenedev.simbir.entity.excerpt.AccountExcerpt;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements AccountExcerpt {
    private long id;
    private String username, password;
    private boolean banned;
    private BigDecimal balance;
    private org.jeugenedev.simbir.entity.Account.Role role;
}
