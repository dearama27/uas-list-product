package com.dea.productlist;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.dea.productlist.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar t = (Toolbar)findViewById(R.id.toolbar);
        final CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        final ImageView iv = (ImageView) findViewById(R.id.toolbarImage);

        final TextView txName = (TextView) findViewById(R.id.txtName);
        final TextView txdescription = (TextView) findViewById(R.id.txtDescription);
        final TextView txrating = (TextView) findViewById(R.id.txtRating);
        final TextView txharga = (TextView) findViewById(R.id.txtHarga);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        txharga.setText(b.getString("b_harga").trim());
        txName.setText(b.getString("b_title").trim());
        txdescription.setText(b.getString("b_overview").trim());

        ctl.setTitle(b.getString("b_title").trim());


        Glide.with(getBaseContext())
                .load(b.getString("b_poster_path").trim())
                .into(iv);

        // menambahkan tombol back pada toolbar
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}