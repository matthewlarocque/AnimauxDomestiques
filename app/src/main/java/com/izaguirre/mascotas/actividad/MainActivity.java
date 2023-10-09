package com.izaguirre.mascotas.actividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huantansheng.easyphotos.EasyPhotos;
import com.izaguirre.mascotas.GlideEngine;
import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.frag.BuscarFragment;
import com.izaguirre.mascotas.frag.CuentaFragment;
import com.izaguirre.mascotas.frag.MainFragment;
import com.izaguirre.mascotas.frag.MsgFragment;
import com.izaguirre.mascotas.user.UserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private BottomNavigationView bnve;
    private FloatingActionButton floatingActionButton;
    private static boolean inflated = false;
    private static boolean social_created = false;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private VpAdapter adapter;
    private Toolbar mtoolbar;

    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }

    public static boolean getInflated() { return inflated; }
    public static void setInflated(boolean inf) { inflated = inf; }
    public static boolean getSocialC() { return social_created; }
    public static void setSocialC(boolean bb) { social_created = bb; }

    public void UpdateBar(String str) {
        ((TextView) findViewById(R.id.titulo)).setText(str);
    }

    private void PrepareFrags() {
        fragments = new ArrayList<>();
        BuscarFragment bfm = new BuscarFragment();
        MainFragment mfm = new MainFragment();
        MsgFragment pfm = new MsgFragment();
        CuentaFragment cfm = new CuentaFragment();
        fragments.add(bfm);
        fragments.add(mfm);
        fragments.add(pfm);
        fragments.add(cfm);
    }

    private void InitSearchBar() {
        SearchView sv = findViewById(R.id.barra_search);
        //设置监听
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private void InitEvento() {
        viewPager.setCurrentItem(1, false);
        bnve.setSelectedItemId(bnve.getMenu().getItem(1).getItemId());

        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = 0;
                switch (item.getItemId()) {
                    case R.id.sentido_frag:
                        position = 0;
                        break;
                    case R.id.lacasa_frag:
                        position = 1;
                        break;
                    case R.id.mensaje_frag:
                        position = 2;
                        break;
                    case R.id.mis_frag:
                        position = 3;
                        break;
                    case R.id.menu_camara:
                        return false;
                    default:
                        break;
                }
                if (previousPosition != position) {
                    viewPager.setCurrentItem(position, false);
                    previousPosition = position;
                }
                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int pos = (position >= 2) ? position+1 : position;
                bnve.setSelectedItemId(bnve.getMenu().getItem(pos).getItemId());
                switch(position) {
                    case 0:
                        UpdateBar(getString(R.string.actualizacion));
                        break;
                    case 1:
                        UpdateBar(getString(R.string.homepage));
                        break;
                    case 2:
                        UpdateBar(getString(R.string.messages));
                        break;
                    case 3:
                        UpdateBar(getString(R.string.navmy));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.barra_add: {
                Toast.makeText(this, "Clicked 2!", Toast.LENGTH_LONG).show();
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        viewPager = findViewById(R.id.vp);
        bnve = findViewById(R.id.bottom_navigation);
        bnve.setItemIconTintList(null);

        mtoolbar = (Toolbar) findViewById(R.id.mytoolbar);
        mtoolbar.setOnMenuItemClickListener(this);
        setSupportActionBar(mtoolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        UpdateBar(getString(R.string.homepage));

        PrepareFrags();

        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        InitEvento();
        InitSearchBar();

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(view -> EasyPhotos.createAlbum(MainActivity.this, true, false, GlideEngine.getInstance())
            .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
            .start(101));
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
