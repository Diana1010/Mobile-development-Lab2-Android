package com.andrew.shoplab.activities;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andrew.shoplab.util.BitmapUtil;
import com.andrew.shoplab.util.Constants;
import com.andrew.shoplab.R;
import com.andrew.shoplab.db.SingletonDatabase;
import com.andrew.shoplab.db.model.Product;
import com.andrew.shoplab.db.model.ProductInBasket;

public class ItemDetailActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mAmount;
    private TextView mChosenAmount;

    private SeekBar mSeekBar;

    private Button mAddToBasketBtn;

    private Product mProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        int id = getIntent().getIntExtra(Constants.ID_KEY,1);
        mProduct = SingletonDatabase.getInstance(this).productDao().getById(id);

        mImageView = findViewById(R.id.iv_product_photo);

        mTitle = findViewById(R.id.tv_detail_title);
        mTitle.setText(mProduct.title);

        mDescription = findViewById(R.id.tv_details);
        mDescription.setText(mProduct.description);

        mAmount = findViewById(R.id.tv_amount);
        mAmount.setText(getString(R.string.amount) + mProduct.amount);

        mChosenAmount = findViewById(R.id.tv_chosen_amount);
        mChosenAmount.setText(getString(R.string.chosen_amount) + 0);

        mSeekBar = findViewById(R.id.sb_amount);
        mSeekBar.setMax(mProduct.amount);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mChosenAmount.setText(getString(R.string.chosen_amount) + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mAddToBasketBtn = findViewById(R.id.btn_add_to_bucket);
        mAddToBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSeekBar.getProgress() == 0){
                    Snackbar.make(v,getString(R.string.message_add_zero_in_basket),Snackbar.LENGTH_SHORT).show();
                }else {
                    ProductInBasket productInBasket = new ProductInBasket(mProduct.id,mSeekBar.getProgress());
                    SingletonDatabase.getInstance(v.getContext()).productInBasketDao().upsert(productInBasket);
                    Toast.makeText(v.getContext(),getString(R.string.item_added),Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        BitmapUtil.setImage(
                this,
                mImageView,
                Uri.parse(mProduct.image_uri),
                width,
                Math.round(getResources().getDimension(R.dimen.list_item_image_height))
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
