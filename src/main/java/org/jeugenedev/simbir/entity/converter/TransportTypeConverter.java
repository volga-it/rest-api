package org.jeugenedev.simbir.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jeugenedev.simbir.entity.Transport;

@Converter
public class TransportTypeConverter implements AttributeConverter<Transport.Type, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Transport.Type type) {
        return type.getId();
    }

    @Override
    public Transport.Type convertToEntityAttribute(Integer integer) {
        return Transport.Type.byId(integer);
    }
}
