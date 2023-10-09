package com.izaguirre.mascotas.frag;

import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.adapter.CanelAdapter;
import com.izaguirre.mascotas.adapter.SocialListModel;
import com.izaguirre.mascotas.adapter.NeufGrilles;
import com.izaguirre.mascotas.adapter.SocialListAdapter;

import java.util.ArrayList;
import java.util.Random;

public class MainFragment extends BasicoFrag {

    private RecyclerView rvlist;
    private ArrayList<SocialListModel> apps = new ArrayList<>();
    private ArrayList<NeufGrilles> neufGrilles = new ArrayList<>();
    private Point size;
    private GridView mGridView;

    private void UpdateList(int text_id, int title_id, int img_id) {
        SocialListModel md = new SocialListModel();
        md.setPicture(img_id);
        md.setTitle(getString(title_id));
        md.setText(getString(text_id));
        apps.add(md);
    }

    private void UpdateList(String text, int title_id, int img_id) {
        SocialListModel md = new SocialListModel();
        md.setPicture(img_id);
        md.setTitle(getString(title_id));
        md.setText(text);
        apps.add(md);
    }

    private void InitList() {
        apps = new ArrayList<>();
        UpdateList(R.string.comment_text, R.string.comment_title, R.drawable.comentarios);
        UpdateList(String.format(getString(R.string.adopt_text), new Random().nextInt(1000) + 500), R.string.adopt_title, R.drawable.adopt);
        UpdateList(R.string.cumbre_text, R.string.cumbre_title, R.drawable.circulo);
        UpdateList(R.string.advice_text, R.string.advice_title, R.drawable.aviso);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View lif = inflater.inflate(R.layout.frag_main, container, false);
        rvlist = lif.findViewById(R.id.sanluis);
        rvlist.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false));
        mGridView = lif.findViewById(R.id.neuf);
        InitList();
        InitNeufGrilles();
        SocialListAdapter sc = new SocialListAdapter(apps);
        sc.setmOnItemClickListener((view, position) -> {
        });
        rvlist.setAdapter(sc);
        return lif;
    }

    private void InitNeufGrilles() {
        int[] channelImg = new int[]{
                R.drawable.hospital,
                R.drawable.asistancia,
                R.drawable.drugstore,
                R.drawable.groupbuy,
                R.drawable.shopcenter,
                R.drawable.ropa,
                R.drawable.coupon,
                R.drawable.carrito,
                R.drawable.amigos
        };

        String[] channelDec = new String[]{
                getString(R.string.txt_hospital),
                getString(R.string.txt_asistancia),
                getString(R.string.txt_drugstore),
                getString(R.string.txt_groupbuy),
                getString(R.string.txt_shopcenter),
                getString(R.string.txt_ropa),
                getString(R.string.txt_coupon),
                getString(R.string.txt_carrito),
                getString(R.string.txt_amigos)
        };

        neufGrilles = new ArrayList<>();
        for(int i=0;i<channelDec.length;i++){
            NeufGrilles channel = new NeufGrilles();
            channel.setImgId(channelImg[i]);
            channel.setDec(channelDec[i]);
            neufGrilles.add(channel);
        }

        mGridView.setAdapter(new CanelAdapter(neufGrilles,this.getActivity()));
        //给九宫格设置监听器
        mGridView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(this.getActivity(), "Clicked pos " + position, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取屏幕宽高
        size = new Point();
        DisplayMetrics metric = new DisplayMetrics();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(size);
        display.getMetrics(metric);
        ViewGroup.LayoutParams params = rvlist.getLayoutParams();
        params.height = (int)((double)size.y / 2.4);
        rvlist.setLayoutParams(params);
        //float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5 / 2.0）
        //int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240 / 320）
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
