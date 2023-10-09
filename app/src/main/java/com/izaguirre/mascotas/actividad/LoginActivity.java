package com.izaguirre.mascotas.actividad;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.izaguirre.mascotas.Herramientas;
import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.user.UserBean;
import com.izaguirre.mascotas.user.UserOpt;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener, TextWatcher {

    private ImageButton mIbNavigationBack;
    private EditText mEtLoginUsername;
    private EditText mEtLoginPwd;
    private LinearLayout mLlLoginUsername;
    private ImageView mIvLoginUsernameDel;
    private Button mBtLoginSubmit;
    private LinearLayout mLlLoginPwd;
    private ImageView mIvLoginPwdDel;
    private ImageView mIvLoginLogo;
    private LinearLayout mLayBackBar;
    private Button mBtLoginRegister;
    private CheckBox mCbRmLogin;
    private SharedPreferences sharedPreferences;
    private String usern, contrasena;
    private UserOpt uo = UserOpt.getInstance();

    public static final int SHOWPROGRESS = 1;
    public static final int DISMISSPROGRESS = 2;

    private int mLogoHeight;
    private int mLogoWidth;
    private ProgressDialog dialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOWPROGRESS:
                    LoginActivity.this.MonstrarDlg();
                    break;
                case DISMISSPROGRESS:
                    LoginActivity.this.EliminarDlg();
                    break;
                default:
                    break;
            }
        }
    };

    private void MonstrarDlg() {
        dialog.setMessage(getString(R.string.wait));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void EliminarDlg() {
        dialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this );
        FrameLayout lay = findViewById(R.id.lay_main_log);
        lay.setBackground(Herramientas.rsBlur(this, R.drawable.bg_login, 25));
        dialog = new ProgressDialog(this);
        initView();
    }

    private boolean checkPasswordExist() {
        usern = sharedPreferences.getString("usern", "");
        contrasena = sharedPreferences.getString("contrasena", "");
        if(usern.isEmpty() || contrasena.isEmpty()) return false;
        return true;
    }

    //初始化视图
    private void initView() {
        boolean pexi = checkPasswordExist();

        //导航栏+返回按钮
        mLayBackBar = findViewById(R.id.ly_retrieve_bar);
        mIbNavigationBack = findViewById(R.id.ib_navigation_back);

        //logo
        mIvLoginLogo = findViewById(R.id.iv_login_logo);

        //username
        mLlLoginUsername = findViewById(R.id.ll_login_username);
        mEtLoginUsername = findViewById(R.id.et_login_username);
        mIvLoginUsernameDel = findViewById(R.id.iv_login_username_del);

        //passwd
        mLlLoginPwd = findViewById(R.id.ll_login_pwd);
        mEtLoginPwd = findViewById(R.id.et_login_pwd);
        mIvLoginPwdDel = findViewById(R.id.iv_login_pwd_del);
        mCbRmLogin = findViewById(R.id.cb_remember_login);

        if(pexi) {
            mEtLoginUsername.setText(usern);
            mEtLoginPwd.setText(contrasena);
        }

        //提交、注册
        mBtLoginSubmit = findViewById(R.id.bt_login_submit);
        mBtLoginRegister = findViewById(R.id.bt_login_register);

        //注册点击事件
        mIbNavigationBack.setOnClickListener(this);
        mEtLoginUsername.setOnClickListener(this);
        mIvLoginUsernameDel.setOnClickListener(this);
        mBtLoginSubmit.setOnClickListener(this);
        mBtLoginRegister.setOnClickListener(this);
        mEtLoginPwd.setOnClickListener(this);
        mIvLoginPwdDel.setOnClickListener(this);

        //注册其它事件
        mCbRmLogin.setChecked(pexi);
        mCbRmLogin.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SharedPreferences.Editor edt = sharedPreferences.edit();
            if(isChecked) {
                edt.putString("usern", mEtLoginUsername.getText().toString());
                edt.putString("contrasena", mEtLoginPwd.getText().toString());
                edt.commit();
            } else {
                edt.remove("usern");
                edt.remove("contrasena");
                edt.commit();
            }
        });
        mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(this);
        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                //返回
                finish();
                break;
            case R.id.et_login_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.et_login_pwd:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;
            case R.id.iv_login_username_del:
                //清空用户名
                mEtLoginUsername.setText(null);
                break;
            case R.id.iv_login_pwd_del:
                //清空密码
                mEtLoginPwd.setText(null);
                break;
            case R.id.bt_login_submit:
                //登录
                loginRequest();
                break;
            case R.id.bt_login_register:
                //注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    private void loginRequest() {
        AtomicBoolean res = new AtomicBoolean();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(SHOWPROGRESS);
                res.set(uo.Login(mEtLoginUsername.getText().toString(), mEtLoginPwd.getText().toString()));
                res.set(uo.findUser(mEtLoginUsername.getText().toString()));
                mHandler.sendEmptyMessage(DISMISSPROGRESS);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(res.get()) {
            //... success!
            UserBean ub = uo.getUser();
            Bundle bundle = new Bundle();
            bundle.putSerializable("userinfo", ub);
            Intent it = new Intent(this, MainActivity.class);
            it.putExtras(bundle);
            startActivity(it);
            Toast.makeText(this, R.string.logsuc, Toast.LENGTH_LONG).show();
            finish();
        } else Toast.makeText(this, R.string.logfail, Toast.LENGTH_LONG).show();
    }

    //用户名密码焦点改变
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();

        if (id == R.id.et_login_username) {
            if (hasFocus) {
                mLlLoginUsername.setActivated(true);
                mLlLoginPwd.setActivated(false);
            }
        } else {
            if (hasFocus) {
                mLlLoginPwd.setActivated(true);
                mLlLoginUsername.setActivated(false);
            }
        }
    }

    //显示或隐藏logo
    @Override
    public void onGlobalLayout() {
        final ImageView ivLogo = this.mIvLoginLogo;
        Rect KeypadRect = new Rect();

        mLayBackBar.getWindowVisibleDisplayFrame(KeypadRect);

        int screenHeight = mLayBackBar.getRootView().getHeight();
        int keypadHeight = screenHeight - KeypadRect.bottom;

        //隐藏logo
        if (keypadHeight > 300 && ivLogo.getTag() == null) {
            final int height = ivLogo.getHeight();
            final int width = ivLogo.getWidth();
            this.mLogoHeight = height;
            this.mLogoWidth = width;

            ivLogo.setTag(true);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                layoutParams.height = (int) (height * animatedValue);
                layoutParams.width = (int) (width * animatedValue);
                ivLogo.requestLayout();
                ivLogo.setAlpha(animatedValue);
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
        //显示logo
        else if (keypadHeight < 300 && ivLogo.getTag() != null) {
            final int height = mLogoHeight;
            final int width = mLogoWidth;

            ivLogo.setTag(null);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                layoutParams.height = (int) (height * animatedValue);
                layoutParams.width = (int) (width * animatedValue);
                ivLogo.requestLayout();
                ivLogo.setAlpha(animatedValue);
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //用户名密码输入事件
    @Override
    public void afterTextChanged(Editable s) {
        String username = mEtLoginUsername.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();

        //是否显示清除按钮
        if (username.length() > 0) {
            mIvLoginUsernameDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
        }
        if (pwd.length() > 0) {
            mIvLoginPwdDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginPwdDel.setVisibility(View.INVISIBLE);
        }

        //根据设置确定是否保存密码
        SharedPreferences.Editor edt = sharedPreferences.edit();
        if(mCbRmLogin.isChecked()) {
            edt.putString("usern", mEtLoginUsername.getText().toString());
            edt.putString("contrasena", mEtLoginPwd.getText().toString());
            edt.commit();
        } else {
            edt.remove("usern");
            edt.remove("contrasena");
            edt.commit();
        }

        //登录按钮是否可用
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

}
