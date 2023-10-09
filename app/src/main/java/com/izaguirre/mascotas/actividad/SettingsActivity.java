package com.izaguirre.mascotas.actividad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.frag.ConfigFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private List<Fragment> fragments;
    private SettingsActivity.VpAdapter adapter;
    private ViewPager viewPager;

    private static class VpAdapter extends FragmentPagerAdapter {
        private List<androidx.fragment.app.Fragment> data;

        public VpAdapter(FragmentManager fm, List<androidx.fragment.app.Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public androidx.fragment.app.Fragment getItem(int position) {
            return data.get(position);
        }
    }

    public void UpdateBar(String str) {
        ((TextView) findViewById(R.id.titulo)).setText(str);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_settings);
        mtoolbar = findViewById(R.id.esatoolbar);
        setSupportActionBar(mtoolbar);
        viewPager = findViewById(R.id.parameterspager);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        UpdateBar(getString(R.string.txt_settings));
        fragments = new ArrayList<>();
        ConfigFragment cfm = new ConfigFragment();
        fragments.add(cfm);
        adapter = new SettingsActivity.VpAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
