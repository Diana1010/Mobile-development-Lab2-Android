package com.andrew.shoplab.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.andrew.shoplab.util.Constants;
import com.andrew.shoplab.activities.ItemDetailActivity;
import com.andrew.shoplab.db.model.Product;
import com.andrew.shoplab.view_holders.ProductViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductViewHolder>
implements Filterable {

    /**
     * @param mProducts holds All Products in adapter
     * @param mProductsFiltered need to binding and to show Products
     */
    private boolean mIsClickable = true;
    List<Product> mProducts;
    private int mLayoutId;

    private List<Product> mProductsFiltered;


    /**
     *
     * @param products Items to adapter
     * @param layout_id id to layout resource to be placed in viewholder
     */
    public ProductsAdapter(List<Product> products,int layout_id) {
        mProducts =  products;
        mProductsFiltered = products;
        mLayoutId = layout_id;
    }

    public List<Product> getProducts() {
        return mProducts;
    }


    public void setProducts(List<Product> products) {
        mProducts = products;
        mProductsFiltered = products;
    }


    /**
     *
     * @param clickable are items in recyclerview clickable
     */
    public void setClickable(boolean clickable) {
        mIsClickable = clickable;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mLayoutId,viewGroup,false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        final Product product = mProductsFiltered.get(i);
        productViewHolder.bind(product);
        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsClickable){
                    Intent i = new Intent(v.getContext(), ItemDetailActivity.class);
                    i.putExtra(Constants.ID_KEY, product.id);
                    v.getContext().startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence searchCharSequence) {
                String charString = searchCharSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mProductsFiltered = mProducts;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product product : mProducts) {
                        if(product.title.toLowerCase().contains(searchCharSequence)
                                || product.description.toLowerCase().contains(searchCharSequence)){
                            filteredList.add(product);
                        }
                    }

                    mProductsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mProductsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mProductsFiltered = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

