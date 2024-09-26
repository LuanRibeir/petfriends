package com.luanribeiro.PetFrieds_Almoxarifado.infra.message;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.luanribeiro.PetFrieds_Almoxarifado.infra.events.StatusChanged;

import java.io.IOException;
import java.text.SimpleDateFormat;

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        String data = sdf.format(evento.getDate());
        jgen.writeStringField("data", data);
    }
}
