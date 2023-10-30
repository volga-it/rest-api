package org.jeugenedev.simbir.entity.excerpt;

import org.jeugenedev.simbir.entity.Account;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = Account.class)
public interface AccountExcerpt {
    long getId();
    String getUsername();
    boolean isBanned();
    BigDecimal getBalance();
    Account.Role getRole();
}
