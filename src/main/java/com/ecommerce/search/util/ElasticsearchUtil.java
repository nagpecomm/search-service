package com.ecommerce.search.util;

import co.elastic.clients.elasticsearch._types.GeoLocation;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggester;
import co.elastic.clients.elasticsearch.core.search.FieldSuggester;
import co.elastic.clients.elasticsearch.core.search.SuggestFuzziness;
import co.elastic.clients.elasticsearch.core.search.Suggester;
import co.elastic.clients.json.JsonData;

import java.util.List;

public class ElasticsearchUtil {

    // ===============================
    // Completion Suggester
    // ===============================
    public static Suggester buildCompletionSuggester(
            String suggestName,
            String field,
            String prefix,
            int limit
    ) {

        SuggestFuzziness fuzziness = SuggestFuzziness.of(f -> f
                .fuzziness(Constants.Fuzzy.LEVEL)
                .prefixLength(Constants.Fuzzy.PREFIX_LENGTH)
        );

        CompletionSuggester completion = CompletionSuggester.of(c -> c
                .field(field)
                .size(limit)
                .fuzzy(fuzziness)
                .skipDuplicates(true)
        );

        FieldSuggester fieldSuggester = FieldSuggester.of(fs -> fs
                .prefix(prefix)
                .completion(completion)
        );

        return Suggester.of(s -> s
                .suggesters(suggestName, fieldSuggester)
        );
    }

    // ===============================
    // Term Query
    // ===============================
    public static Query buildTermQuery(String field, String value, float boost) {
        return Query.of(q -> q.term(t -> t
                .field(field)
                .value(value)
                .boost(boost)
                .caseInsensitive(true)
        ));
    }

    // ===============================
    // Range Query (ES 8 Correct Way)
    // ===============================
    public static Query buildRangeQuery(String field, Object from, Object to) {

        return Query.of(q -> q.range(r -> r
                .field(field)
                .gte(from != null ? JsonData.of(from) : null)
                .lte(to != null ? JsonData.of(to) : null)
        ));
    }

    // ===============================
    // Geo Distance Query
    // ===============================
    public static Query buildGeoDistanceQuery(
            String field,
            String distance,
            Double latitude,
            Double longitude
    ) {

        LatLonGeoLocation latLon = LatLonGeoLocation.of(ll -> ll
                .lat(latitude)
                .lon(longitude)
        );

        GeoLocation location = GeoLocation.of(g -> g.latlon(latLon));

        return Query.of(q -> q.geoDistance(gd -> gd
                .field(field)
                .distance(distance)
                .location(location)
        ));
    }

    // ===============================
    // Multi Match Query
    // ===============================
    public static Query buildMultiMatchQuery(List<String> fields, String searchTerm) {

        return Query.of(q -> q.multiMatch(mm -> mm
                .query(searchTerm)
                .fields(fields)
                .fuzziness(Constants.Fuzzy.LEVEL)
                .prefixLength(Constants.Fuzzy.PREFIX_LENGTH)
                .type(TextQueryType.MostFields)
                .operator(Operator.And)
        ));
    }

    // ===============================
    // Terms Aggregation
    // ===============================
    public static Aggregation buildTermsAggregation(String field) {

        return Aggregation.of(a -> a.terms(t -> t
                .field(field)
                .size(10)
        ));
    }
}