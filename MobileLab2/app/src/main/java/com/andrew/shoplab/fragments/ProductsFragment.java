package com.andrew.shoplab.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.andrew.shoplab.R;
import com.andrew.shoplab.activities.BasketActivity;
import com.andrew.shoplab.activities.ItemCreateActivity;
import com.andrew.shoplab.db.SingletonDatabase;
import com.andrew.shoplab.db.model.Product;
import com.andrew.shoplab.adapters.ProductsAdapter;

import java.util.List;

public class ProductsFragment extends Fragment {
    private static final String RV_STYLE = "style";

    public static final int RV_LINES = 1;
    public static final int RV_GRID = 2;

    private int mCurrentLayoutForItems;
    private ProductsAdapter mProductsAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products,container,false);
        int type = getArguments().getInt(RV_STYLE);

        mRecyclerView = v.findViewById(R.id.rv_products);

        List<Product> products = SingletonDatabase.getInstance(getContext()).productDao().getAll();
        if(type == RV_LINES){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mCurrentLayoutForItems = R.layout.product_list_item;
            mProductsAdapter = new ProductsAdapter(products,mCurrentLayoutForItems);
        }else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            mCurrentLayoutForItems = R.layout.product_grid_layout;
            mProductsAdapter = new ProductsAdapter(products,mCurrentLayoutForItems);
        }

        mRecyclerView.setAdapter(mProductsAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.app_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search)
                .getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                useFilter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                useFilter(s);
                return false;
            }

            private void useFilter(String s){
                        mProductsAdapter.getFilter().filter(s);
            }
        });
    }

    public static ProductsFragment newInstance(final int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(RV_STYLE, type);

        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(mProductsAdapter == null){
            mProductsAdapter = new ProductsAdapter(SingletonDatabase.getInstance(getContext()).productDao().getAll(),mCurrentLayoutForItems);
        }

        if(requestCode == ItemCreateActivity.ADD_PRODUCT_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            List<Product> products = SingletonDatabase.getInstance(getContext()).productDao().getAll();
            mProductsAdapter.setProducts(products);
            mProductsAdapter.notifyItemInserted(mProductsAdapter.getItemCount());
        }

        if(requestCode == BasketActivity.UPDATE_ITEMS && resultCode == Activity.RESULT_OK){
            List<Product> products = SingletonDatabase.getInstance(getContext()).productDao().getAll();
            mProductsAdapter.setProducts(products);
            mProductsAdapter.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
