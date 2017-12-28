package com.moler.technicaltask.repository;

import com.moler.technicaltask.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BasketRepository extends CrudRepository<Basket, Long> {

    Optional<Basket> findBasketById(long id);
}
