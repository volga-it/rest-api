package org.jeugenedev.simbir.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jeugenedev.simbir.entity.Account;

@Converter
public class AccountRoleConverter implements AttributeConverter<Account.Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Account.Role role) {
        return role.getId();
    }

    @Override
    public Account.Role convertToEntityAttribute(Integer integer) {
        return Account.Role.byId(integer);
    }
}
