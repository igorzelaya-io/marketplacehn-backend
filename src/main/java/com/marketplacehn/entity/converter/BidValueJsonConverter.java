package com.marketplacehn.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplacehn.entity.dto.BidValueJson;

import jakarta.persistence.Converter;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class BidValueJsonConverter implements AttributeConverter<BidValueJson, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(BidValueJson attribute) {
        String dbData = null;
        try {
            dbData = mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return dbData;
    }

    @Override
    public BidValueJson convertToEntityAttribute(String dbData) {
        BidValueJson bidValue = null;
        try {
            bidValue = mapper.readValue(dbData, BidValueJson.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bidValue;
    }
}
