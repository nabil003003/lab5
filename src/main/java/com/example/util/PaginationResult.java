package com.example.util;

import java.util.List;

public class PaginationResult<T> {
    private List<T> itemsP;
    private int currentPageP;
    private int pageSizeP;
    private int totalPagesP;
    private long totalItemsP;

    public PaginationResult(List<T> itemsP, int currentPageP, int pageSizeP, long totalItemsP) {
        this.itemsP = itemsP;
        this.currentPageP = currentPageP;
        this.pageSizeP = pageSizeP;
        this.totalItemsP = totalItemsP;
        this.totalPagesP = (int) Math.ceil((double) totalItemsP / pageSizeP);
    }

    // Getters
    public List<T> getItemsP() { return itemsP; }
    public int getCurrentPageP() { return currentPageP; }
    public int getPageSizeP() { return pageSizeP; }
    public int getTotalPagesP() { return totalPagesP; }
    public long getTotalItemsP() { return totalItemsP; }
}
