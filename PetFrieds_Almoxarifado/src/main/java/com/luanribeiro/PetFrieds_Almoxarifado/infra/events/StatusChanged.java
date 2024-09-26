package com.luanribeiro.PetFrieds_Almoxarifado.infra.events;

import java.io.Serializable;
import java.util.Date;

import com.luanribeiro.PetFrieds_Almoxarifado.domain.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusChanged implements Serializable {

    private Long idProduct;
    private Status status;
    private Date date;

    public StatusChanged() {
    }

    public StatusChanged(Long idProduct, Status status) {
        this.idProduct = idProduct;
        this.status = status;
        this.date = new Date();
    }

    public StatusChanged(Long idProduct, Status status, Date date) {
        this.idProduct = idProduct;
        this.status = status;
        this.date = date;
    }

    @Override
    public String toString() {
        return "EstadoMudou{" + "idProduct=" + idProduct + ", status=" + status + ", date=" + date + '}';
    }
}
