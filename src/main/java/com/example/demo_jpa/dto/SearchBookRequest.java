package com.example.demo_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchBookRequest {

    @NotBlank
    private String searchKey;

    @NotBlank
    private String searchValue;

    @NotBlank
    private String operator;

    private boolean isAvailable;

    private static Set<String> allowedKeys = new HashSet<>();

    private static Map<String, List<String>> allowedOperatorsMap = new HashMap<>();

    public SearchBookRequest() {
        if(allowedKeys.isEmpty()) {
            allowedKeys.addAll(Arrays.asList("id", "name", "genre", "author"));
        }

        if(allowedOperatorsMap.isEmpty()) {
            allowedOperatorsMap.put("id", List.of("="));
            allowedOperatorsMap.put("name", List.of("=", "like"));
            allowedOperatorsMap.put("genre", List.of("="));
            allowedOperatorsMap.put("author", List.of("=", "like"));
            //allowedOperatorsMap.put("pages", List.of("=", "<=", ">=", "<", ">"));
        }
    }

    public boolean validate() {
        if(allowedKeys.contains(searchKey)) {
            return allowedOperatorsMap.get(searchKey).contains(operator);
        }
        return false;
    }

}
