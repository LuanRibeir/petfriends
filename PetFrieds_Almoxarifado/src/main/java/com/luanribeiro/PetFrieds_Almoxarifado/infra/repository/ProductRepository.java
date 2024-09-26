package com.luanribeiro.PetFrieds_Almoxarifado.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanribeiro.PetFrieds_Almoxarifado.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
