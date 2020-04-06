package com.backend.bank.common;

import com.backend.bank.model.Pages;

import java.util.Comparator;

public class PageSortByPosition implements Comparator<Pages> {

    @Override
    public int compare(Pages o1, Pages o2) {
        return o1.getPosition() - o2.getPosition();
    }
}
