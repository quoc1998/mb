package com.backend.bank.common;

import com.backend.bank.model.NewsBlocks;

import java.util.Comparator;

public class NewBlockSortPosition implements Comparator<NewsBlocks> {
    @Override
    public int compare(NewsBlocks t1, NewsBlocks t2) {
        return t1.getPosition()- t2.getPosition();
    }
}
