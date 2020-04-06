package com.backend.bank.common;

import com.backend.bank.model.MenuItems;

import java.util.Comparator;

public class MenuItemSortByPosition implements Comparator<MenuItems> {

    @Override
    public int compare(MenuItems t1, MenuItems t2) {
        return t1.getPosition() - t2.getPosition();
    }
}
