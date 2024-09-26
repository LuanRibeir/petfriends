package com.luanribeiro.PetFrieds_Transporte.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luanribeiro.PetFrieds_Transporte.infra.converter.AddressConverter;

@Data
@Entity
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = AddressConverter.class)
    @Column(name = "destination_address", nullable = false)
    private Address destinationAdress;

    @Column(name = "transport_status", nullable = false)
    private Status status;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "shipping_date")
    private LocalDateTime shippingDate;

    public Transport(Long id, Address destinationAdress, Long productId,
            Status status,
            LocalDateTime shippingDate) {
        this.id = id;
        this.destinationAdress = destinationAdress;
        this.productId = productId;
        this.status = status;
        this.shippingDate = LocalDateTime.now();
    }

    public Transport() {
        this.shippingDate = LocalDateTime.now();
    }

    public void transit() {
        if (this.status != Status.EM_PREPARACAO) {
            throw new IllegalStateException("Não é possível transportar um produto que não está em preparação");
        }

        this.status = Status.EM_TRANSITO;
    }

    public void deliver() {
        if (this.status != Status.EM_TRANSITO) {
            throw new IllegalStateException("Não é possível entregar um produto que não esteja em transito");
        }
        this.status = Status.ENTREGUE;
    }
}
