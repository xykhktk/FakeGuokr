package com.xguokr.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xguokr.fragment.ArticleReplyFragment;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.xguokr.R;

public class KexuerenArticleReplyActivity extends AppCompatActivity {

    private ArticleReplyFragment articleReplyFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kexuerenarticlereply);
        toolbar = (Toolbar) findViewById(R.id.toolbar_KexuerenArticleReplyActivity);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("回复");

        String articleId = getIntent().getStringExtra(Const.IntentKey_To_ArticleReplyActivity);


        articleReplyFragment = new ArticleReplyFragment();
        Bundle b = new Bundle();
        b.putSerializable(ArticleReplyFragment.ArticleReplyFragmentBundleKey, articleId);
        articleReplyFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_kexuerenreplyactivity,articleReplyFragment).commit();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_kexuerenarticlereply, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}
