package com.andrew.shoplab.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.andrew.shoplab.R;
import com.andrew.shoplab.db.SingletonDatabase;
import com.andrew.shoplab.db.model.Product;
import com.andrew.shoplab.adapters.ProductsAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class BasketActivity extends AppCompatActivity {

    public static final int UPDATE_ITEMS = 2222;

    private ProductsAdapter mProductsAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_products);

        mRecyclerView = findViewById(R.id.rv_products);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<Product> products = SingletonDatabase.getInstance(this)
                .productInBasketDao().getAllProducts();
        mProductsAdapter = new ProductsAdapter(products,R.layout.product_list_item);
        mProductsAdapter.setClickable(false);

        mRecyclerView.setAdapter(mProductsAdapter);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.basket_name);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basket_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.app_bar_clear_basket:
                clearBasket();
                return true;
            case R.id.app_bar_basket_buy:
                //здесь следовало бы вынести этот код в отдельный метод, но мне лень
                List<Product> products = SingletonDatabase.getInstance(this).productDao().getAll();
                List<Product> basketProducts = SingletonDatabase.getInstance(this).productInBasketDao().getAllProducts();

                //filter products
                products = products.stream()
                        .filter(prod -> basketProducts.stream()
                            .anyMatch(bProd -> prod.id == bProd.id)
                        )
                        .collect(Collectors.toList());

                //Delete amount from list
                for(Product p:products){
                    for (Product bp:basketProducts){
                        if(p.id == bp.id){
                            p.amount = p.amount - bp.amount;
                        }
                    }
                }

                //update products
                for(Product p:products){
                    SingletonDatabase.getInstance(this).productDao().update(p);
                }

                clearBasket();

                setResult(Activity.RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearBasket(){
        SingletonDatabase.getInstance(this).productInBasketDao().deleteAll();
        mProductsAdapter.getProducts().clear();
        mProductsAdapter.notifyDataSetChanged();
    }


}
