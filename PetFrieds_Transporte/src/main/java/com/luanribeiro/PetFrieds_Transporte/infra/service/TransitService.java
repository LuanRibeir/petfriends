package com.luanribeiro.PetFrieds_Transporte.infra.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.google.cloud.spring.pubsub.support.converter.ConvertedBasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.luanribeiro.PetFrieds_Transporte.domain.Transport;
import com.luanribeiro.PetFrieds_Transporte.domain.Address;
import com.luanribeiro.PetFrieds_Transporte.domain.Status;
import com.luanribeiro.PetFrieds_Transporte.infra.events.StatusChanged;
import com.luanribeiro.PetFrieds_Transporte.infra.repository.TransportRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class TransitService {

    private static final Logger LOG = LoggerFactory.getLogger(TransitService.class);

    @Autowired
    private PubSubTemplate pubSubTemplate;
    @Autowired
    JacksonPubSubMessageConverter converter;
    @Autowired
    private TransportRepository repository;

    public Transport getById(long id) {
        return repository.getReferenceById(id);
    }

    public Transport transit(StatusChanged evento) {

        LOG.info("Criando novo transporte para o produto ID: {}, status: {}, endereço: {}",
                evento.getIdProduct(), evento.getStatus(), evento.getAddress());

        Address address = new Address(
                evento.getAddress().getStreet(),
                evento.getAddress().getNumber(),
                evento.getAddress().getCity(),
                evento.getAddress().getState(),
                evento.getAddress().getCep());

        Transport transport = new Transport();
        transport.setProductId(evento.getIdProduct());
        transport.setDestinationAdress(address);
        transport.setStatus(evento.getStatus());

        transport.transit();

        return repository.save(transport);
    }

    public Transport deliver(Long id) {
        Transport transport = getById(id);

        if (transport == null) {
            return null;
        }

        transport.deliver();
        transport = repository.save(transport);

        publish(new StatusChanged(transport.getId(), Status.ENTREGUE, transport.getDestinationAdress()));

        return transport;
    }

    public void processEvent(StatusChanged evento) {
        switch (evento.getStatus().toString()) {
            case "EM_PREPARACAO":
                transit(evento);
                break;
            case "EM_TRANSITO":
                deliver(evento.getIdProduct());
                break;
        }
    }

    private void publish(StatusChanged estado) {
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("pedido_topic", estado);
        LOG.info("***** Message Published --> " + estado);
    }

    @ServiceActivator(inputChannel = "inputMessageChannel")
    private void receber(StatusChanged payload,
            @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) ConvertedBasicAcknowledgeablePubsubMessage<StatusChanged> message) {

        try {
            LOG.info("***** Message Received --> " + payload);
            this.processEvent(payload);
            message.ack();
        } catch (Exception e) {
            LOG.error("Error processing message: {}", e.getMessage());
            message.nack();
        }
    }
}