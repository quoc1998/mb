package com.backend.bank.common;

import com.backend.bank.model.PageBlocks;

import java.util.Comparator;

public class PageBlockSortPosition implements Comparator<PageBlocks> {
    @Override
    public int compare(PageBlocks t1, PageBlocks t2) {
        return t1.getPosition() -t2.getPosition();
    }
}
