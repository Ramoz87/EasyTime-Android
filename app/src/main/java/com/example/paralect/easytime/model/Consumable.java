package com.example.paralect.easytime.model;

/**
 * Created by alexei on 16.01.2018.
 */

// used just to mark expenses and materials as the same substance
public interface Consumable {
    String getName();
    boolean isMaterial();
    int getStockQuantity();
    int getPricePerUnit();
}
