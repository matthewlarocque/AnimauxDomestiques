package com.izaguirre.mascotas.actividad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.izaguirre.mascotas.Herramientas;
import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.user.UserBean;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;

public class QRCodeActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private UserBean ub;
    private ImageView iv;

    public void UpdateBar(String str) {
        ((TextView) findViewById(R.id.titulo)).setText(str);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        mtoolbar = (Toolbar) findViewById(R.id.thiztoolbar);
        iv = findViewById(R.id.qrcplace);
        setSupportActionBar(mtoolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        UpdateBar(getString(R.string.txt_qrcode));
        Intent intent = getIntent();
        ub = (UserBean)intent.getSerializableExtra("userinfo");
        String cnt = "LasMascotas?username=" + ub.getUsername() + "?userid=" + ub.getUserid() + "?sex=" + (ub.getGender()?"male":"female") + "?age=" + ub.getAge();
        String fpath = Environment.getExternalStorageDirectory() + File.separator + "tmp";
        File carpeta = new File(fpath);
        if(!carpeta.exists()) carpeta.mkdir();
        String fstr = fpath + File.separator + "puerto.jpg";
        Blob ven = ub.getAvatar();
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, null);
        try {
            if(ven != null) {
                byte[] data = {};
                ven.getBinaryStream().read(data);
                bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        Herramientas.createQRImage(cnt, 680, 680, bm, fstr);
        iv.setImageBitmap(BitmapFactory.decodeFile(fstr));
    }
}
