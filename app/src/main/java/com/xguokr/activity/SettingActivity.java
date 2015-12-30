package com.xguokr.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.xguokr.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private RadioButton dayTheme;
    private RadioButton nightTheme;
    private RadioButton download_always;
    private RadioButton download_in_wifi;
    private Button cancel;
    private Button ok;

    private String setTheme;
    private String currentThemem;
    private String currentMode;
    private String setMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar_settingactivity);
        dayTheme = (RadioButton) findViewById(R.id.rb_daytheme_settingactivity);
        nightTheme = (RadioButton) findViewById(R.id.rb_nighttheme_settingactivity);
        download_always = (RadioButton) findViewById(R.id.rb_download_always_settingactivity);
        download_in_wifi = (RadioButton) findViewById(R.id.rb_download_in_wifi_settingactivity);
        cancel = (Button) findViewById(R.id.btn_cancel_settingactivity);
        ok = (Button) findViewById(R.id.btn_ok_settingactivity);

        setSupportActionBar(toolbar);
        dayTheme.setOnClickListener(this);
        nightTheme.setOnClickListener(this);
        download_always.setOnClickListener(this);
        download_in_wifi.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);

        currentThemem = XGUtil.SPGetStringUtil(SettingActivity.this,Const.SPKey_Theme);
        if(currentThemem.equals(Const.Theme_Day)){
            dayTheme.setChecked(true);
            setTheme = Const.Theme_Day;
        }else{
            nightTheme.setChecked(true);
            setTheme = Const.Theme_Night;
        }

        currentMode = XGUtil.SPGetStringUtil(SettingActivity.this,Const.SPKey_DownloadPicMode);
        if(currentMode.equals(Const.DownloadPicMode_Always)){
            download_always.setChecked(true);
            setMode = Const.DownloadPicMode_Always;
        }else{
            download_in_wifi.setChecked(true);
            setMode = Const.DownloadPicMode_OnlyWifi;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about_settingactiviyt) {
            new AlertDialog.Builder(SettingActivity.this).setTitle("关于").setMessage("一个习作，学习对象是果壳非官方客户端https://github.com/NashLegend/SourceWall.").create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setTheme(int resid) {
        if(XGUtil.SPGetStringUtil(this, Const.SPKey_Theme).equals(Const.Theme_Day)){
            resid = R.style.AppThemeDay;
        }else{
            resid = R.style.AppThemeNight;
        }
        super.setTheme(resid);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rb_daytheme_settingactivity:
                setTheme = Const.Theme_Day;
                break;
            case R.id.rb_nighttheme_settingactivity:
                setTheme = Const.Theme_Night;
                break;
            case R.id.rb_download_always_settingactivity:
                setMode = Const.DownloadPicMode_Always;
                break;
            case R.id.rb_download_in_wifi_settingactivity:
                setMode = Const.DownloadPicMode_OnlyWifi;
                break;
            case R.id.btn_cancel_settingactivity:
                setResult(MainActivityNavigation.ResultCode_cancel);
                finish();
                break;
            case R.id.btn_ok_settingactivity:
                if (!currentMode.equals(setMode)){
                    XGUtil.SPSaveUtil(SettingActivity.this,Const.SPKey_DownloadPicMode,setMode);
                }

                if (currentThemem.equals(setTheme)){
                    setResult(MainActivityNavigation.ResultCode_NotResetTheme);
                }else{
                    XGUtil.SPSaveUtil(SettingActivity.this,Const.SPKey_Theme,setTheme );
                    setResult(MainActivityNavigation.ResultCode_ResetTheme);
                }
                finish();
                break;

        }

    }
}
