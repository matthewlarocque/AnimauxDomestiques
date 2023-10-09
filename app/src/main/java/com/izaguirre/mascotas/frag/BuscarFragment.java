package com.izaguirre.mascotas.frag;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.izaguirre.mascotas.R;

public class BuscarFragment extends BasicoFrag {

    private WebView web_view;
    private ProgressBar mProgressBar;
    private WebSettings webSettings;
    private SwipeRefreshLayout swipeLayout;

    public int getHeight() {
        Point size = new Point();
        DisplayMetrics metric = new DisplayMetrics();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(size);
        display.getMetrics(metric);
        return size.y;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View lif = inflater.inflate(R.layout.frag_buscar, container, false);
        web_view = lif.findViewById(R.id.creepview);
        swipeLayout = lif.findViewById(R.id.swipe_container);
        swipeLayout.setDistanceToTriggerSync(getHeight()/4);
        // 设置刷新监听器
        swipeLayout.setOnRefreshListener(() -> {
            web_view.reload();
            swipeLayout.setRefreshing(false);
        });

        //设置进度条渐变的颜色
        /*swipeLayout.setColorScheme(R.color.holo_blue_bright,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);*/
        webSettings = web_view.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        //web_view.loadUrl("file:///android_asset/Demo.html");
        web_view.setWebViewClient(new WebViewClient());
        mProgressBar = lif.findViewById(R.id.cargar_bar);
        web_view.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress != 100) {
                    mProgressBar.setProgress(newProgress);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        web_view.loadUrl("http://cn.bing.com/search?q=" + getActivity().getString(R.string.keyword));
        return lif;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
