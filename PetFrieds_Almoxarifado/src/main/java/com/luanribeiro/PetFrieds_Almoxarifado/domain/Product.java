package com.luanribeiro.PetFrieds_Almoxarifado.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luanribeiro.PetFrieds_Almoxarifado.infra.converter.DimensionConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_description", nullable = false)
    private String description;

    @Column(name = "product_amount")
    private int amount;

    @Convert(converter = DimensionConverter.class)
    @Column(name = "product_dimension")
    private Dimension dimension;

    @Column(name = "product_status")
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Product(String description, int amount, Dimension dimension, Status status) {
        this.description = description;
        this.amount = amount;
        this.dimension = dimension;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    public void prepare() {
        if (this.status != Status.FECHADO) {
            throw new IllegalStateException("Não é possível preparar um produto que não está fechado");
        }

        this.status = Status.EM_PREPARACAO;
    }

    public void send() {
        if (this.status != Status.EM_PREPARACAO) {
            throw new IllegalStateException("Não é possível enviar um produto que não esteja em preparação");
        }
        this.status = Status.EM_PREPARACAO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        Product product = (Product) o;
        return amount == product.amount &&
                Objects.equals(id, product.id) &&
                Objects.equals(description, product.description) &&
                Objects.equals(dimension, product.dimension) &&
                status == product.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, amount, dimension, status);
    }
}
