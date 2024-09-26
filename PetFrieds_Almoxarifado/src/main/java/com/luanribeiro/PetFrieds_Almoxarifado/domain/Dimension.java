package com.luanribeiro.PetFrieds_Almoxarifado.domain;

import java.io.Serializable;
import java.util.Objects;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Dimension implements Serializable {
    private double altura;
    private double largura;
    private double profundidade;

    public Dimension(double altura, double largura, double profundidade) {
        if (altura <= 0) {
            throw new IllegalArgumentException("A altura deve ser maior que zero.");
        }
        this.altura = altura;

        if (largura <= 0) {
            throw new IllegalArgumentException("A largura deve ser maior que zero.");
        }
        this.largura = largura;

        if (profundidade <= 0) {
            throw new IllegalArgumentException("A profundidade deve ser maior que zero.");
        }
        this.profundidade = profundidade;
    }

    public double getAltura() {
        return altura;
    }

    public double getLargura() {
        return largura;
    }

    public double getProfundidade() {
        return profundidade;
    }

    @Override
    public String toString() {
        return String.format("[altura=%.2f cm, largura=%.2f cm, profundidade=%.2f cm]", altura, largura,
                profundidade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Dimension))
            return false;
        Dimension that = (Dimension) o;
        return Double.compare(that.altura, altura) == 0 &&
                Double.compare(that.largura, largura) == 0 &&
                Double.compare(that.profundidade, profundidade) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(altura, largura, profundidade);
    }
}
