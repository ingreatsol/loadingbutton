package com.ingreatsol.loadingbutton.test;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ingreatsol.loadingbutton.test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnSicronizarRacimos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.btnSicronizarRacimos.isInProgress()){
                    binding.btnSicronizarRacimos.onStopLoading();
                }
                else{
                    binding.btnSicronizarRacimos.onStartLoading();
                }
            }
        });
    }
}
