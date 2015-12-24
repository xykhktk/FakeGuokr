package com.xguokr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xguokr.bean.GroupArtcleListItem;
import com.xguokr.bean.GroupTag;
import com.xguokr.fragment.GroupArticleFragment;
import com.xguokr.fragment.GroupArticlesListFragment;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.xguokr.R;

public class GroupArticlesListActivity extends AppCompatActivity implements
        GroupArticlesListFragment.OnGroupArticlesListClickListener ,GroupArticleFragment.OnReplyButtonClickListener{

    private Toolbar toolbar;
    private GroupTag groupTag;
    private GroupArticlesListFragment groupArticlesListFragment;
    private FragmentTransaction ft;
    private GroupArticleFragment groupArticleFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouparticlelist);
        groupTag = (GroupTag) getIntent().getSerializableExtra(Const.IntentKey_To_ListUnderAGroupTagActivity);
        toolbar = (Toolbar) findViewById(R.id.toolbar_grouparticlelist_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(groupTag.getGroupName());

        groupArticlesListFragment = new GroupArticlesListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GroupArticlesListFragment.GroupArticlesListFragmentFragmentArgumentKey, groupTag);
        groupArticlesListFragment.setArguments(bundle);

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelayout_grouparticlelist_activity, groupArticlesListFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_grouparticle, menu);
        return true;
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
       public boolean onOptionsItemSelected(MenuItem item) {
        /*int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGroupArticlesListClick(GroupArtcleListItem item) {
        groupArticleFragment = new GroupArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GroupArticleFragment.GroupArticleFragmentArgumentKey,item);
        groupArticleFragment.setArguments(bundle);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelayout_grouparticlelist_activity,groupArticleFragment);
        ft.addToBackStack(null);
        ft.commit();

    }


    @Override
    public void OnReplyButtonClick(GroupArtcleListItem item) {
        Intent i = new Intent(this,GroupArticlesReplyActivity.class);
        i.putExtra(Const.IntentKey_To_GroupArticleReplyActivity,item);
        startActivity(i);

    }
}
