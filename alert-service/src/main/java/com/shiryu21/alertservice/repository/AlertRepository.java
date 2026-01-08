package com.shiryu21.alertservice.repository;

import com.shiryu21.alertservice.entity.CryptoAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<CryptoAlert, Long> {

    @Query("SELECT a FROM CryptoAlert a JOIN FETCH a.user WHERE a.symbol = :symbol")
    List<CryptoAlert> findBySymbol(String symbol);

}
