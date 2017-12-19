package com.moler.technicaltask.repository;

import com.moler.technicaltask.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}
