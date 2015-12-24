package com.xguokr.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xguokr.bean.QuestionArticleListItem;
import com.xguokr.bean.QuestionTag;
import com.xguokr.fragment.QuestionArticleAndReplyFragment;
import com.xguokr.fragment.QuestionArticlesListFragment;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.xguokr.R;

public class QuestionArticleListActivity extends AppCompatActivity
        implements  QuestionArticlesListFragment.OnQuestionArticlesListClickListener{

    private Toolbar toolbar;
    private QuestionTag questionTag;
    private FragmentTransaction ft;
    private QuestionArticlesListFragment questionArticlesListFragment;
    private QuestionArticleAndReplyFragment questionArticleAndReplyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionarticlelist);

        questionTag = (QuestionTag) getIntent().getSerializableExtra(Const.IntentKey_To_QuestionArticleListActivity);
        toolbar = (Toolbar) findViewById(R.id.toolbar_questionarticlelistactivity);
        setSupportActionBar(toolbar);

        questionArticlesListFragment = new QuestionArticlesListFragment();
        Bundle b = new Bundle();
        b.putSerializable(QuestionArticlesListFragment.FragmentArgumentKey, questionTag);
        questionArticlesListFragment.setArguments(b);

        ft = getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_questionarticlelist,questionArticlesListFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_questionarticlelist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
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
    public void onQuestionArticlesListClick(QuestionArticleListItem item) {
        questionArticleAndReplyFragment = new QuestionArticleAndReplyFragment();
        Bundle b = new Bundle();
        b.putSerializable(QuestionArticleAndReplyFragment.FragmentArgumentKey,item);
        questionArticleAndReplyFragment.setArguments(b);
        ft = getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_questionarticlelist,questionArticleAndReplyFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
