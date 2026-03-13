package com.ecommerce.search.service;

import
        co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.ecommerce.search.dto.ProductDocument;
import com.ecommerce.search.dto.ProductSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Slf4j
@Service
public class ProductSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public ProductSearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public ProductSearchResponse search(
            String keyword,
            Double minPrice,
            Double maxPrice,
            String brand,
            int page,
            int size
    ) {
        log.info("******** ProductSearchService: calling elastic cloud *********");

        Query query = Query.of(q -> q.bool(b -> {

            if (StringUtils.hasText(keyword)) {
                b.must(m -> m.multiMatch(mm -> mm
                        .query(keyword)
                        .fields(
                                "name",
                                "manufacturer.name",
                                "categories.name"
                        )
                ));
            }

            if (StringUtils.hasText(brand)) {
                b.filter(f -> f.term(t -> t
                        .field("manufacturer.name")
                        .value(brand)
                ));
            }

            if (minPrice != null || maxPrice != null) {

                b.filter(f -> f.range(r -> r
                        .field("price")
                        .gte(JsonData.of(minPrice != null ? minPrice : 0))
                        .lte(JsonData.of(maxPrice != null ? maxPrice : 999999))
                ));
            }

            return b;
        }));

        Aggregation brandAgg = Aggregation.of(a -> a
                .terms(t -> t.field("manufacturer.name"))
        );

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(query)
                .withAggregation("brands", brandAgg)
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<ProductDocument> hits =
                elasticsearchOperations.search(
                        searchQuery,
                        ProductDocument.class
                );

        List<ProductDocument> products =
                hits.stream()
                        .map(SearchHit::getContent)
                        .toList();

        ProductSearchResponse response = new ProductSearchResponse();
        response.setProducts(products);
        response.setTotal(hits.getTotalHits());

        return response;
    }

    public List<String> suggest(String prefix) {

        Query query = Query.of(q -> q
                .prefix(p -> p
                        .field("name")
                        .value(prefix)
                )
        );

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(query)
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<ProductDocument> hits =
                elasticsearchOperations.search(
                        searchQuery,
                        ProductDocument.class
                );

        return hits.stream()
                .map(hit -> hit.getContent().getName())
                .toList();
    }
}