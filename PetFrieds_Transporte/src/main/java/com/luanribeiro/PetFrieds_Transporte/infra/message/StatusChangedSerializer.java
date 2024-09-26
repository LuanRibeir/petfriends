package com.luanribeiro.PetFrieds_Transporte.infra.message;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.luanribeiro.PetFrieds_Transporte.domain.Address;
import com.luanribeiro.PetFrieds_Transporte.infra.events.StatusChanged;

import java.io.IOException;

public class StatusChangedSerializer extends StdSerializer<StatusChanged> {

    public StatusChangedSerializer() {
        super(StatusChanged.class);
    }

    @Override
    public void serialize(StatusChanged evento, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();

        jgen.writeNumberField("idProduct", evento.getIdProduct());

        jgen.writeStringField("status", evento.getStatus().toString());

        Address address = evento.getAddress();
        if (address != null) {
            jgen.writeObjectFieldStart("address");
            jgen.writeStringField("street", address.getStreet());
            jgen.writeStringField("number", address.getNumber());
            jgen.writeStringField("city", address.getCity());
            jgen.writeStringField("state", address.getState());
            jgen.writeStringField("cep", address.getCep());
            jgen.writeEndObject();
        }

        jgen.writeEndObject();
    }
}
