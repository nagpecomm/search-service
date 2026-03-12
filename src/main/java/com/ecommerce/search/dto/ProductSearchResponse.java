package com.ecommerce.search.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearchResponse {
    public List<ProductDocument> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDocument> products) {
        this.products = products;
    }

    public List<BrandFacet> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandFacet> brands) {
        this.brands = brands;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    private List<ProductDocument> products;

    private List<BrandFacet> brands;

    private long total;
}
