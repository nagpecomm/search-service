package com.ecommerce.search.controller;

import com.ecommerce.search.dto.ProductSearchResponse;
import com.ecommerce.search.service.ProductSearchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/products")
@RestController
@AllArgsConstructor
public class SearchController {

    @Autowired
    private ProductSearchService service;
    

    @GetMapping("/search")
    public ProductSearchResponse search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String brand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return service.search(
                keyword,
                minPrice,
                maxPrice,
                brand,
                page,
                size
        );
    }

    @GetMapping("/suggest")
    public List<String> suggest(
            @RequestParam String prefix
    ) {

        return service.suggest(prefix);

    }



}