package org.jeugenedev.simbir.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jeugenedev.simbir.entity.Rent;

@Converter
public class RentTypeConverter implements AttributeConverter<Rent.Type, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Rent.Type type) {
        return type.getId();
    }

    @Override
    public Rent.Type convertToEntityAttribute(Integer integer) {
        return Rent.Type.byId(integer);
    }
}
