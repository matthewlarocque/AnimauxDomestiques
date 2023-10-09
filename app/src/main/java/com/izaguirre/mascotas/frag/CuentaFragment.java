package com.izaguirre.mascotas.frag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huantansheng.easyphotos.setting.Setting;
import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.actividad.QRCodeActivity;
import com.izaguirre.mascotas.actividad.SettingsActivity;
import com.izaguirre.mascotas.adapter.PageListAdapter;
import com.izaguirre.mascotas.adapter.PageListModel;
import com.izaguirre.mascotas.user.UserBean;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class CuentaFragment extends BasicoFrag {

    private UserBean ub;
    private TextView tv1, tv2;
    private ImageView iv0;
    private RecyclerView ajustes;
    private ArrayList<PageListModel> plm = new ArrayList<>();

    private void UpdateList(int text_id) {
        PageListModel md = new PageListModel();
        md.setText(getString(text_id));
        plm.add(md);
    }

    private void InitLista() {
        plm = new ArrayList<>();
        UpdateList(R.string.txt_qrcode);
        UpdateList(R.string.txt_moneda);
        UpdateList(R.string.txt_order);
        UpdateList(R.string.txt_recvaddr);
        UpdateList(R.string.txt_coupon);
        UpdateList(R.string.txt_settings);
        PageListAdapter p = new PageListAdapter(plm);
        p.setmOnItemClickListener((view, position) -> {
            Bundle bundle = new Bundle();
            Intent it;
            switch(position) {
                case 0:
                    bundle.putSerializable("userinfo", ub);
                    it = new Intent(getActivity(), QRCodeActivity.class);
                    it.putExtras(bundle);
                    startActivity(it);
                    break;
                case 5:
                    bundle.putSerializable("userinfo", ub);
                    it = new Intent(getActivity(), SettingsActivity.class);
                    it.putExtras(bundle);
                    startActivity(it);
                    break;
                default:
                    break;
            }
        });
        ajustes.setAdapter(p);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View lif = inflater.inflate(R.layout.frag_cuenta, container, false);
        Intent intent = this.getActivity().getIntent();
        ub = (UserBean)intent.getSerializableExtra("userinfo");
        ajustes = lif.findViewById(R.id.ajustes);
        ajustes.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false));
        ajustes.addItemDecoration(new DividerItemDecoration(this.getActivity(),DividerItemDecoration.VERTICAL));
        InitLista();
        tv1 = lif.findViewById(R.id.ztv_nick);
        tv2 = lif.findViewById(R.id.ztv_userid);
        iv0 = lif.findViewById(R.id.tengo);
        tv1.setText(String.format(getString(R.string.txt_nickname), ub.getNickname()));
        tv2.setText(String.format(getString(R.string.txt_usuario), ub.getUserid()));
        Blob ven = ub.getAvatar();
        try {
            if(ven == null) iv0.setImageResource(R.mipmap.ic_launcher);
            else {
                byte[] data = {};
                ven.getBinaryStream().read(data);
                Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                iv0.setImageBitmap(bm);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            iv0.setImageResource(R.mipmap.ic_launcher);
        }
        return lif;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
