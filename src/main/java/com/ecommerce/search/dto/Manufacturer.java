package com.ecommerce.search.dto;

import lombok.Data;

@Data
public class Manufacturer {
    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Long manufacturerId;

    private String name;
}