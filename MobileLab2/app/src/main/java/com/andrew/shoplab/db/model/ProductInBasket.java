package com.andrew.shoplab.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Product.class,
parentColumns = "id",
childColumns = "productId",
onDelete = ForeignKey.CASCADE,
onUpdate = ForeignKey.CASCADE))

public class ProductInBasket {

    @PrimaryKey
    public int productId;

    @ColumnInfo
    public int amount;

    public ProductInBasket() {
    }

    public ProductInBasket(int productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
