package com.luanribeiro.PetFrieds_Transporte.infra.events;

import java.io.Serializable;
import java.util.Date;

import com.luanribeiro.PetFrieds_Transporte.domain.Address;
import com.luanribeiro.PetFrieds_Transporte.domain.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusChanged implements Serializable {

    private Long idProduct;
    private Status status;
    private Address address;

    public StatusChanged() {
    }

    public StatusChanged(Long idProduct, Status status, Address address) {
        this.idProduct = idProduct;
        this.status = status;
        this.address = address;
    }

    @Override
    public String toString() {
        return "StatusChanged{" +
                "idProduct=" + idProduct +
                ", status=" + status +
                ", address=" + address +
                '}';
    }
}
