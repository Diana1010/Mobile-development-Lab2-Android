package com.andrew.shoplab.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.andrew.shoplab.db.dao.ProductDao;
import com.andrew.shoplab.db.dao.ProductInBasketDao;
import com.andrew.shoplab.db.model.Product;
import com.andrew.shoplab.db.model.ProductInBasket;


@Database(entities = {Product.class, ProductInBasket.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract ProductInBasketDao productInBasketDao();


}
