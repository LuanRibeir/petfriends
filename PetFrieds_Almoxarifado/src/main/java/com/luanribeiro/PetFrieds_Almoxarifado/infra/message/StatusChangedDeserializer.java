package com.luanribeiro.PetFrieds_Almoxarifado.infra.message;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.luanribeiro.PetFrieds_Almoxarifado.domain.Status;
import com.luanribeiro.PetFrieds_Almoxarifado.infra.events.StatusChanged;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StatusChangedDeserializer extends StdDeserializer<StatusChanged> {

    public StatusChangedDeserializer() {
        super(StatusChanged.class);
    }

    @Override
    public StatusChanged deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
        StatusChanged evento = null;
        JsonNode node = jp.getCodec().readTree(jp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        try {
            evento = new StatusChanged(
                    node.get("idProduct").asLong(),
                    Status.valueOf(node.get("status").asText()),
                    sdf.parse(node.get("data").asText()));
        } catch (ParseException e) {
            throw new IOException("Time Data Error");
        }
        return evento;
    }
}
