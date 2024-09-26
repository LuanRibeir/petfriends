package com.luanribeiro.PetFrieds_Transporte.infra.message;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.luanribeiro.PetFrieds_Transporte.domain.Address;
import com.luanribeiro.PetFrieds_Transporte.domain.Status;
import com.luanribeiro.PetFrieds_Transporte.infra.events.StatusChanged;

import java.io.IOException;

public class StatusChangedDeserializer extends StdDeserializer<StatusChanged> {

    public StatusChangedDeserializer() {
        super(StatusChanged.class);
    }

    @Override
    public StatusChanged deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
        StatusChanged evento = null;
        JsonNode node = jp.getCodec().readTree(jp);

        Address address = new Address(
                node.get("address").get("street").asText(),
                node.get("address").get("number").asText(),
                node.get("address").get("city").asText(),
                node.get("address").get("state").asText(),
                node.get("address").get("cep").asText());

        evento = new StatusChanged(
                node.get("idProduct").asLong(),
                Status.valueOf(node.get("status").asText()),
                address);

        return evento;
    }
}
