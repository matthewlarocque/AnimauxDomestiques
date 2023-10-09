package com.izaguirre.mascotas.actividad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.user.UserBean;
import com.izaguirre.mascotas.user.UserOpt;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private long exitTime;
    private int LENGTH = 3, recLen = LENGTH;
    private TextView tv;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(() -> {
                recLen--;
                tv.setText(getString(R.string.skip_bv) + " " + recLen);
                if (recLen < 0) {
                    timer.cancel();
                    tv.setVisibility(View.GONE);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenue);
        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted) {
                        finish();
                        System.exit(0);
                        return;
                    }
                });
        tv = findViewById(R.id.saltar);
        tv.setText(getString(R.string.skip_bv) + " " + new Integer(LENGTH).toString());
        tv.setOnClickListener(view -> {
            gotoLogin();
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        });
        timer.schedule(task, 1000, 1000);
        handler.postDelayed(() -> gotoLogin(), LENGTH * 1000);
    }

    private void gotoLogin() {
        UserOpt uo = UserOpt.getInstance();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this );
        String usern = sharedPreferences.getString("usern", "");
        String contrasena = sharedPreferences.getString("contrasena", "");
        if(!usern.isEmpty() && !contrasena.isEmpty() && uo.Login(usern, contrasena) && uo.findUser(usern)) {
            UserBean ub = uo.getUser();
            Bundle bundle = new Bundle();
            bundle.putSerializable("userinfo", ub);
            Intent it = new Intent(this, MainActivity.class);
            it.putExtras(bundle);
            startActivity(it);
            finish();
        } else {
            if(!usern.isEmpty() && !contrasena.isEmpty()) Toast.makeText(this, R.string.logfail_preload, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(WelcomeActivity.this, R.string.twice_press, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                WelcomeActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}