package com.moler.technicaltask.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@ToString
public class BasketAndItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Basket basket;

    @ManyToOne
    private Item item;

    private Integer quantity;

    public BasketAndItem(){}

    public BasketAndItem(Basket basket, Item item, Integer quantity) {
        this.basket = basket;
        this.item = item;
        this.quantity = quantity;
    }
}
