package com.ecommerce.search.dto;

import java.util.List;

public record Facet(String name,
                    List<FacetItem> items) {
}

