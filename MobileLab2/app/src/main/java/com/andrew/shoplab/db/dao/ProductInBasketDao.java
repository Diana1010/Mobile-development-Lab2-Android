package com.andrew.shoplab.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.sqlite.SQLiteConstraintException;

import com.andrew.shoplab.db.model.Product;
import com.andrew.shoplab.db.model.ProductInBasket;

import java.util.List;

@Dao
public interface ProductInBasketDao {

    @Query("SELECT * FROM productinbasket WHERE productId=:id")
    ProductInBasket getById(int id);

//    @Query("SELECT * FROM productinbasket")
//    List<ProductInBasket> getAll();

    @Query("SELECT * FROM product JOIN productinbasket ON productId = id")
    List<Product> getAllProducts();

    @Delete
    void delete(ProductInBasket productInBasket);

    @Query("DELETE FROM productinbasket")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(ProductInBasket productInBasket);

    @Update(onConflict = OnConflictStrategy.FAIL)
    void update(ProductInBasket productInBasket);

    default void upsert(ProductInBasket productInBasket) {
        try {
            insert(productInBasket);
        } catch (SQLiteConstraintException exception) {
            update(productInBasket);
        }
    }
}
