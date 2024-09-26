package com.luanribeiro.PetFrieds_Transporte.infra.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luanribeiro.PetFrieds_Transporte.domain.Address;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AddressConverter implements AttributeConverter<Address, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(AddressConverter.class);

    @Override
    public String convertToDatabaseColumn(Address address) {
        if (address == null) {
            return null;
        }
        try {
            String string = objectMapper.writeValueAsString(address);

            // LOG.info("Object: ", dimension);
            // LOG.info("Converted to Json String: ", string);

            return string;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting Address to JSON", e);
        }
    }

    @Override
    public Address convertToEntityAttribute(String addressString) {
        if (addressString == null) {
            return null;
        }
        try {
            Address address = objectMapper.readValue(addressString, Address.class);
            LOG.info("Converted JSON String to Address: {}", address);
            return address;
        } catch (Exception e) {
            LOG.error("Error converting JSON to Address: {}", e.getMessage());
            throw new IllegalArgumentException("Error converting JSON to Address", e);
        }
    }

}
