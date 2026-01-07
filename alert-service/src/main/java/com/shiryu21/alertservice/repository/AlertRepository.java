package com.shiryu21.alertservice.repository;

import com.shiryu21.alertservice.entity.CryptoAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<CryptoAlert, Long> {

    List<CryptoAlert> findBySymbol(String symbol);

}
