package com.ecommerce.search.dto;

public record Pagination(int page,
                         int size,
                         long totalElements,
                         int totalPages) {
}
