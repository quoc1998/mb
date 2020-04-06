package com.backend.bank.common;

import org.springframework.data.domain.Sort;

public class Common {
    public static Sort getSort(String field){
        return Sort.by(field).descending();
    }
}
