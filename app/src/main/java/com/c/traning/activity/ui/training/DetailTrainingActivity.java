package com.c.traning.activity.ui.training;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.c.traning.R;
import com.c.traning.activity.HomeActivity;
import com.c.traning.helper.Config;
import com.c.traning.model.ResponseErrorModel;
import com.c.traning.rest.ApiConfig;
import com.c.traning.rest.ApiService;

import java.util.Objects;

public class DetailTrainingActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_training);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageView ivImageTrainingDetail = findViewById(R.id.iv_image_training_detail);
        TextView tvNamaTrainingDetail = findViewById(R.id.tv_nama_training_detail);
        TextView tvTipeTrainingDetail = findViewById(R.id.tv_tipe_training_detail);
        TextView tvKuotaTrainingDetail = findViewById(R.id.tv_kuota_training_detail);
        TextView tvHargaTrainingDetail = findViewById(R.id.tv_harga_training_detail);
        TextView tvTanggalTrainingDetail = findViewById(R.id.tv_tanggal_training_detail);
        TextView tvStatusTrainingDetail = findViewById(R.id.tv_status_training_detail);
        Button btnDeleteTrainingDetail = findViewById(R.id.btn_delete_training_detail);

        final String idTraining = getIntent().getStringExtra(Config.BUNDLE_ID);
        String nama = getIntent().getStringExtra(Config.BUNDLE_NAMA);
        String tipe = getIntent().getStringExtra(Config.BUNDLE_TIPE);
        String kuota = getIntent().getStringExtra(Config.BUNDLE_KUOTA);
        String harga = getIntent().getStringExtra(Config.BUNDLE_HARGA);
        String tanggal = getIntent().getStringExtra(Config.BUNDLE_TANGGAL);
        String status = getIntent().getStringExtra(Config.BUNDLE_STATUS);
        String image = getIntent().getStringExtra(Config.BUNDLE_IMAGE);

        tvNamaTrainingDetail.setText(nama);
        tvTipeTrainingDetail.setText(tipe);
        tvKuotaTrainingDetail.setText(kuota);
        tvHargaTrainingDetail.setText(harga);
        tvTanggalTrainingDetail.setText(tanggal);
        tvStatusTrainingDetail.setText(status);
        Glide.with(DetailTrainingActivity.this)
                .load(image)
                .error(R.drawable.ic_menu_gallery)
                .into(ivImageTrainingDetail);

        btnDeleteTrainingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = ApiConfig.getApiService();
                apiService.postDeleteTraining(idTraining)
                        .enqueue(new Callback<ResponseErrorModel>() {
                            @Override
                            public void onResponse(Call<ResponseErrorModel> call,
                                                   Response<ResponseErrorModel> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(DetailTrainingActivity.this, "Sukses Delete", Toast.LENGTH_SHORT).show();
                                    finishAffinity();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseErrorModel> call, Throwable t) {
                                Toast.makeText(DetailTrainingActivity.this, "Gagal Delete"
                                        + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
