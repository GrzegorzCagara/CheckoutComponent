package com.moler.technicaltask.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private Double price;
    private Integer unit;
    private Double specialPrice;

    public Item(){}

    public Item(Builder builder){
        this.name = builder.name;
        this.price = builder.price;
        this.unit = builder.unit;
        this.specialPrice = builder.specialPrice;
    }

    public static class Builder {
        private String name;
        private Double price;
        private Integer unit;
        private Double specialPrice;

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withPrice(Double price){
            this.price = price;
            return this;
        }

        public Builder withUnit(Integer unit){
            this.unit = unit;
            return this;
        }

        public Builder withSpecialPrice(Double specialPrice){
            this.specialPrice = specialPrice;
            return this;
        }

        public Item build(){
            return new Item(this);
        }
    }







}
