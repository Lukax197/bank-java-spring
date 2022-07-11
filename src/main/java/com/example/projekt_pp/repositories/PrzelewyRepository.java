package com.example.projekt_pp.repositories;

import com.example.projekt_pp.models.Przelewy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrzelewyRepository extends JpaRepository<Przelewy, Long> {

    List<Przelewy> findAllByK(long k);

    List<Przelewy> findAllByStatus(String status);

    @Modifying
    @Query("update Przelewy p set p.status='Potwierdzone' where p.id=:id")
    void potwierdz(long id);
}
