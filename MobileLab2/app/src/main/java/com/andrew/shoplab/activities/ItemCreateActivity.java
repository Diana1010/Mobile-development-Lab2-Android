package com.andrew.shoplab.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.andrew.shoplab.util.BitmapUtil;
import com.andrew.shoplab.util.Constants;
import com.andrew.shoplab.R;

public class ItemCreateActivity extends AppCompatActivity {

    public static final int ADD_PRODUCT_REQUEST_CODE = 123;
    private static final int GET_PHOTO_REQUEST_CODE = 200;

    private ImageView mImageView;
    private EditText mEditTitle;
    private EditText mEditDescription;
    private EditText mEditAmount;
    private Button mCreateButton;

    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        mImageView = findViewById(R.id.iv_product_photo);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, GET_PHOTO_REQUEST_CODE);
            }
        });

        mEditTitle = findViewById(R.id.et_product_title);
        mEditDescription = findViewById(R.id.et_edit_details);
        mEditAmount = findViewById(R.id.et_products_count);

        mCreateButton = findViewById(R.id.btn_create_product);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                String title = mEditTitle.getText().toString();
                String description = mEditDescription.getText().toString();
                String strAmount = mEditAmount.getText().toString();
                if(title.isEmpty() || description.isEmpty() || strAmount.isEmpty() || mImageUri == null){
                    Snackbar.make(v,getString(R.string.not_full_info_string),Snackbar.LENGTH_LONG).show();
                }else {
                    int amount = Integer.parseInt(mEditAmount.getText().toString());
                    i.putExtra(Constants.TITLE_KEY, title);
                    i.putExtra(Constants.DESCRIPTION_KEY, description);
                    i.putExtra(Constants.AMOUNT_KEY, amount);
                    i.putExtra(Constants.IMAGE_URI_KEY,mImageUri.toString());
                    setResult(Activity.RESULT_OK,i);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data==null)return;

        if(requestCode == GET_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            BitmapUtil.setImage(this,mImageView,imageUri,mImageView.getWidth(),mImageView.getHeight());
            mImageUri = imageUri;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
