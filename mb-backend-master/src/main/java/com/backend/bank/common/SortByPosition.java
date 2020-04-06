package com.backend.bank.common;

import com.backend.bank.model.Category;

import java.util.Comparator;

public class SortByPosition implements Comparator<Category> {

    @Override
    public int compare(Category o1, Category o2) {
        return o1.getPosition()-o2.getPosition();
    }
}
