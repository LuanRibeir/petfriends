package com.luanribeiro.PetFrieds_Transporte.domain;

import java.util.Objects;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Address {

    private String street;
    private String number;
    private String city;
    private String state;
    private String cep;

    public Address(String street, String number, String city, String state, String cep) {
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("A rua não pode ser null ou vazia");
        }
        this.street = street;

        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("O número não pode ser null ou vazio");
        }
        this.number = number;

        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("A cidade não pode ser null ou vazia");
        }
        this.city = city;

        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException("O estado não pode ser null ou vazio");
        }
        this.state = state;

        if (cep == null || !cep.matches("\\d{5}-\\d{3}")) {
            throw new IllegalArgumentException("O CEP deve estar no formato 00000-000");
        }
        this.cep = cep;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCep() {
        return cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Address))
            return false;
        Address address = (Address) o;
        return street.equals(address.street) &&
                number.equals(address.number) &&
                city.equals(address.city) &&
                state.equals(address.state) &&
                cep.equals(address.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, city, state, cep);
    }

}
