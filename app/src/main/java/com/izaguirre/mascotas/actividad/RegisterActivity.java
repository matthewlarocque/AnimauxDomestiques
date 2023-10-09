package com.izaguirre.mascotas.actividad;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.izaguirre.mascotas.Herramientas;
import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.user.UserOpt;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private Button regbtn;
    private EditText mEtRegUsername;
    private EditText mEtRegPwd;
    private UserOpt uo = UserOpt.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_step_one);
        LinearLayout lay = findViewById(R.id.lay_register_one_container);
        lay.setBackground(Herramientas.rsBlur(this, R.drawable.bg_login, 25));
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        mEtRegUsername = findViewById(R.id.et_register_username);
        mEtRegPwd = findViewById(R.id.et_register_pwd);
        mEtRegUsername.addTextChangedListener(this);
        mEtRegPwd.addTextChangedListener(this);
        regbtn = findViewById(R.id.bt_register_submit);
        regbtn.setOnClickListener(v -> {
            boolean reged = uo.findUser(mEtRegUsername.getText().toString());
            if(!reged) {
                boolean suc = uo.Register(mEtRegUsername.getText().toString(), mEtRegPwd.getText().toString());
                if(suc) {
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                Toast.makeText(this, R.string.already_registered, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String username = mEtRegUsername.getText().toString().trim();
        String pwd = mEtRegPwd.getText().toString().trim();

        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
            regbtn.setBackgroundResource(R.drawable.bg_login_submit);
            regbtn.setTextColor(getResources().getColor(R.color.white));
        } else {
            regbtn.setBackgroundResource(R.drawable.bg_login_submit_lock);
            regbtn.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }
}