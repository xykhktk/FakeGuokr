package com.xguokr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.xguokr.bean.GroupTag;
import com.xguokr.bean.QuestionTag;
import com.xguokr.fragment.GroupTagListFragment;
import com.xguokr.fragment.KexuerenListFragment;
import com.xguokr.fragment.LoginFragment;
import com.xguokr.fragment.QuestionTagFragment;
import com.xguokr.fragment.UserInfoFragment;
import com.xguokr.net.TestToken;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.view.PProgressDialog;
import com.xguokr.xguokr.R;

public class MainActivityNavigation extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks ,KexuerenListFragment.onKexuerenListItemClickListener
        ,LoginFragment.LoginSuccessCallBack,GroupTagListFragment.OnGroupTagListClickListener
        ,QuestionTagFragment.OnGroupTagListClickListener{

    private KexuerenListFragment kexuerenListFragment;
    private LoginFragment loginFragment;
    private QuestionTagFragment questionTagFragment;
    private UserInfoFragment userInfoFragment;
    private GroupTagListFragment groupTagListFragment;
    private Fragment curentFregment;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar toolbar;

    public final static int ResultCode_ResetTheme = 1;
    public final static int ResultCode_NotResetTheme = 2;
    public final static int ResultCode_cancel = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar_mainactivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("科学人");

        setDefaultDownloadPicMode();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),toolbar);

        kexuerenListFragment = new KexuerenListFragment();
        loginFragment = new LoginFragment();
        questionTagFragment = new QuestionTagFragment();
        groupTagListFragment = new GroupTagListFragment();
        curentFregment = kexuerenListFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container,kexuerenListFragment).commit();

    }

    private void setDefaultDownloadPicMode(){
        String currentMode = XGUtil.SPGetStringUtil(MainActivityNavigation.this,Const.SPKey_DownloadPicMode);
        if (TextUtils.isEmpty(currentMode)){
            XGUtil.SPSaveUtil(MainActivityNavigation.this,Const.SPKey_DownloadPicMode,Const.DownloadPicMode_OnlyWifi);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position){
            case 0:
                if(curentFregment != kexuerenListFragment){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,kexuerenListFragment).commit();
                    curentFregment = kexuerenListFragment;
                    getSupportActionBar().setTitle(R.string.title_section1);
                }
                break;
            case 1:
                if(curentFregment != groupTagListFragment){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,groupTagListFragment).commit();
                    curentFregment = groupTagListFragment;
                    getSupportActionBar().setTitle(R.string.title_section2);
                }
                break;
            case 2:
                if(curentFregment != questionTagFragment){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,questionTagFragment).commit();
                    curentFregment = questionTagFragment;
                    getSupportActionBar().setTitle(R.string.title_section3);
                }
                break;
            case 3:
                final PProgressDialog pd = new PProgressDialog(this).createDialog("正在验证");
                pd.showPd();
                new TestToken().postRelationship(this, new TestToken.SuccessCallback() {
                    @Override
                    public void onSuccess(int result) {
                        if (curentFregment != userInfoFragment){
                            if(userInfoFragment == null) userInfoFragment = new UserInfoFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,userInfoFragment).commit();
                            curentFregment = userInfoFragment;
                            getSupportActionBar().setTitle(R.string.title_section5);
                            pd.dismissPd();
                            XGUtil.ToastUtil(getApplication(), "你已经登陆");
                        }
                    }
                }, new TestToken.FaileCallback() {
                    @Override
                    public void onFalie() {
                        if(curentFregment != loginFragment){
                            pd.dismissPd();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,loginFragment).commit();
                            curentFregment = loginFragment;
                            getSupportActionBar().setTitle(R.string.title_section4);
                        }
                    }
                });

                break;
        }
    }

    @Override
    public void setTheme(int resid) {
        String currentThemem = XGUtil.SPGetStringUtil(MainActivityNavigation.this,Const.SPKey_Theme);
        if (TextUtils.isEmpty(currentThemem)){
            currentThemem = Const.Theme_Day;
            XGUtil.SPSaveUtil(MainActivityNavigation.this,Const.SPKey_Theme,Const.Theme_Day);
        }

        if(currentThemem.equals(Const.Theme_Day)){
            resid = R.style.AppThemeDay;
        }else{
            resid = R.style.AppThemeNight;
        }

        super.setTheme(resid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main_activity_navigation, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mainactivity_setting) {
            Intent i = new Intent(this,SettingActivity.class);
            startActivityForResult(i,0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode){
            case ResultCode_ResetTheme:
                recreate();
                break;
            default:
        }
        //super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onKexuerenListItemClick(String url) {
        Intent i = new Intent(this,KexuerenArtcitcleActivity.class);
        i.putExtra(Const.IntentKey_To_ArticleActivity,url);
        startActivity(i);
    }

    @Override
    public void loginSuccess() {
        if (userInfoFragment == null){
            userInfoFragment = new UserInfoFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,userInfoFragment).commit();
            curentFregment = userInfoFragment;
        }

    }

    @Override
    public void onGroupTagListClick(GroupTag tag) {
        Intent i = new Intent(this,GroupArticlesListActivity.class);
        i.putExtra(Const.IntentKey_To_ListUnderAGroupTagActivity,tag);
        startActivity(i);
    }

    @Override
    public void onGroupTagListClick(QuestionTag tag) {
        Intent i = new Intent(this,QuestionArticleListActivity.class);
        i.putExtra(Const.IntentKey_To_QuestionArticleListActivity,tag);
        startActivity(i);
    }

}
