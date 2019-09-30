package com.andrew.shoplab.view_holders;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.shoplab.util.BitmapUtil;
import com.andrew.shoplab.R;
import com.andrew.shoplab.db.model.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mAmount;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mImageView = itemView.findViewById(R.id.imageView);
        mTitle = itemView.findViewById(R.id.tv_title);
        mDescription = itemView.findViewById(R.id.tv_description);
        mAmount = itemView.findViewById(R.id.tv_amount);
    }

    public void bind(Product product){
        final Uri imageUri = Uri.parse(product.image_uri);
        BitmapUtil.setImage(itemView.getContext(),mImageView,imageUri,
                Math.round(itemView.getContext().getResources().getDimension(R.dimen.list_item_image_width)),
                Math.round(itemView.getContext().getResources().getDimension(R.dimen.list_item_image_height)));

        mTitle.setText(product.title);
        mDescription.setText(product.description);
        mAmount.setText(itemView.getResources().getString(R.string.amount) + product.amount);
    }
}
