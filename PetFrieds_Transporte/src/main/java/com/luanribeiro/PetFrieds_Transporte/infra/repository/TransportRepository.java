package com.luanribeiro.PetFrieds_Transporte.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanribeiro.PetFrieds_Transporte.domain.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long> {

}
