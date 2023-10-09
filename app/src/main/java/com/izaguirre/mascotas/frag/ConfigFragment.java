package com.izaguirre.mascotas.frag;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.izaguirre.mascotas.Herramientas;
import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.actividad.LoginActivity;
import com.izaguirre.mascotas.user.UserBean;
import com.izaguirre.mascotas.user.UserOpt;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class ConfigFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener  {

    private EditTextPreference nick, ano;
    private SwitchPreference gen;
    private ListPreference idioma;
    private Preference pfcache, pfexit;
    private UserBean ub;
    private EditText et1,et2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        ub = (UserBean)intent.getSerializableExtra("userinfo");
        if(ub == null) {
            Log.d("Errrr", "No UserBean");
            getActivity().finish();
            return;
        }
        addPreferencesFromResource(R.xml.settings_xml);
        nick = findPreference("prefCambiaNombre");
        ano = findPreference("prefCambiaAno");
        gen = findPreference("prefGender");
        idioma = findPreference("prefLanguage");
        pfcache = findPreference("prefClCache");
        pfexit = findPreference("prefLogout");
        nick.setOnPreferenceChangeListener(this);
        nick.setOnBindEditTextListener(editText -> et1 = editText);
        ano.setOnPreferenceChangeListener(this);
        ano.setOnBindEditTextListener(editText -> et2 = editText);
        gen.setChecked(ub.getGender());
        gen.setOnPreferenceChangeListener(this);
        idioma.setOnPreferenceChangeListener(this);
        pfcache.setOnPreferenceClickListener(this);
        pfexit.setOnPreferenceClickListener(this);
        nick.setSummary(ub.getNickname());
        ano.setSummary(new Integer(ub.getAge()).toString());
        pfcache.setSummary(Herramientas.getTotalCacheSize(getActivity()));
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
        if(preference == nick) {
            String nv = et1.getText().toString();
            if(!nv.trim().isEmpty()) {
                UserOpt uo = UserOpt.getInstance();
                if(uo.UpdateNick(nv) && uo.findUser(ub.getUsername())) {
                    ub = uo.getUser();
                    nick.setSummary(ub.getNickname());
                }
            }
        } else if (preference == ano) {
            String num = et2.getText().toString();
            if(!num.trim().isEmpty()) {
                try {
                    int val = Integer.parseInt(num);
                    UserOpt uo = UserOpt.getInstance();
                    if(uo.UpdateAge(val) && uo.findUser(ub.getUsername())) {
                        ub = uo.getUser();
                        ano.setSummary(new Integer(val).toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (preference == gen) {
            boolean nuevovalor = gen.isChecked() ? false : true;
            UserOpt uo = UserOpt.getInstance();
            if(uo.UpdateGender(nuevovalor) && uo.findUser(ub.getUsername())) {
                ub = uo.getUser();
                gen.setChecked(nuevovalor);
            }
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        if(preference == pfcache) {
            MaterialDialog mDialog = new MaterialDialog.Builder(this.getActivity())
                    .setTitle(getString(R.string.title_del))
                    .setMessage(getString(R.string.confirm_del))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.btn_del), android.R.drawable.ic_delete, (dialogInterface, which) -> {
                        Herramientas.clearAllCache(ConfigFragment.this.getActivity());
                        Toast.makeText(ConfigFragment.this.getActivity(), R.string.suc_del, Toast.LENGTH_LONG).show();
                        pfcache.setSummary(Herramientas.getTotalCacheSize(ConfigFragment.this.getActivity()));
                        dialogInterface.dismiss();
                    })
                    .setNegativeButton(getString(R.string.btn_cancel), android.R.drawable.ic_menu_close_clear_cancel, (dialogInterface, which) -> dialogInterface.dismiss())
                    .build();
            mDialog.show();
        } else if(preference == pfexit) {
            MaterialDialog mDialog = new MaterialDialog.Builder(this.getActivity())
                    .setTitle(getString(R.string.txt_logout))
                    .setMessage(getString(R.string.cfm_logout))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.btn_ok), R.drawable.ic_action_accept_dark, (dialogInterface, which) -> {
                        SharedPreferences.Editor edt = PreferenceManager.getDefaultSharedPreferences(this.getActivity() ).edit();
                        edt.remove("usern");
                        edt.remove("contrasena");
                        edt.commit();
                        Activity u = this.getActivity(), v = u.getParent();
                        Intent it = new Intent(u, LoginActivity.class);
                        startActivity(it);
                        u.finish();
                        v.finish();
                        dialogInterface.dismiss();
                    })
                    .setNegativeButton(getString(R.string.btn_cancel), android.R.drawable.ic_menu_close_clear_cancel, (dialogInterface, which) -> dialogInterface.dismiss())
                    .build();
            mDialog.show();
        }
        return false;
    }
}
