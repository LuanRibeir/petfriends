package com.luanribeiro.PetFrieds_Almoxarifado.infra.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luanribeiro.PetFrieds_Almoxarifado.domain.Dimension;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DimensionConverter implements AttributeConverter<Dimension, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(DimensionConverter.class);

    @Override
    public String convertToDatabaseColumn(Dimension dimension) {
        if (dimension == null) {
            return null;
        }
        try {
            String string = objectMapper.writeValueAsString(dimension);

            // LOG.info("Object: ", dimension);
            // LOG.info("Converted to Json String: ", string);

            return string;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting Dimension to JSON", e);
        }
    }

    @Override
    public Dimension convertToEntityAttribute(String dimensionString) {
        if (dimensionString == null) {
            return null;
        }
        try {
            Dimension dimension = objectMapper.readValue(dimensionString, Dimension.class);
            LOG.info("Converted JSON String to Dimension: {}", dimension);
            return dimension;
        } catch (Exception e) {
            LOG.error("Error converting JSON to Dimension: {}", e.getMessage());
            throw new IllegalArgumentException("Error converting JSON to Dimension", e);
        }
    }

}
