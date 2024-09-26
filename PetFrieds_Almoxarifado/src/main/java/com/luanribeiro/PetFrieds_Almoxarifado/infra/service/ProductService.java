package com.luanribeiro.PetFrieds_Almoxarifado.infra.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.google.cloud.spring.pubsub.support.converter.ConvertedBasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.luanribeiro.PetFrieds_Almoxarifado.domain.Product;
import com.luanribeiro.PetFrieds_Almoxarifado.domain.Status;
import com.luanribeiro.PetFrieds_Almoxarifado.infra.events.StatusChanged;
import com.luanribeiro.PetFrieds_Almoxarifado.infra.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private PubSubTemplate pubSubTemplate;
    @Autowired
    JacksonPubSubMessageConverter converter;
    @Autowired
    private ProductRepository repository;

    public Product getById(long id) {
        return repository.getReferenceById(id);
    }

    public Product prepareProduct(long id) {
        if (getById(id) == null) {
            return null;
        }

        Product product = repository.getReferenceById(id);
        product.prepare();
        product = repository.save(product);
        publish(new StatusChanged(product.getId(), Status.EM_PREPARACAO));

        return product;
    }

    public Product createProduct(Product product) {
        LOG.info("Creating product: {}", product);

        Product retorno = null;
        if (product != null) {
            retorno = repository.save(product);
        }
        return retorno;
    }

    public void processEvent(StatusChanged evento) {
        switch (evento.getStatus().toString()) {
            case "FECHADO":
                prepareProduct(evento.getIdProduct());
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