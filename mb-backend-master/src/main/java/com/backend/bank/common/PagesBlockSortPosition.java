package com.backend.bank.common;

import com.backend.bank.model.PageBlocks;

public class PagesBlockSortPosition extends PageBlocks implements Comparable<PageBlocks> {
    @Override
    public int compareTo(PageBlocks o) {
        return this.getPosition().compareTo(o.getPosition());
    }
}
