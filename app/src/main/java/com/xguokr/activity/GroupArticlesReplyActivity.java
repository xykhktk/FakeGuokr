package com.xguokr.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.xguokr.adapter.GroupArticleReplyAdapter;
import com.xguokr.bean.GroupArtcleListItem;
import com.xguokr.bean.ReplyItem;
import com.xguokr.net.ArticleReplyPost;
import com.xguokr.net.GroupArticleReplyGet;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.view.PProgressDialog;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

public class GroupArticlesReplyActivity extends AppCompatActivity implements View.OnClickListener{

    private GroupArtcleListItem item;
    private Toolbar toolbar;
    private EditText editText;
    private ImageView replyImageView;
    private RecyclerView recyclerView;
    private GroupArticleReplyAdapter adapter;
    private ArrayList<ReplyItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouparticlesreply);
        toolbar = (Toolbar) findViewById(R.id.toolbar_GroupArticlesReplyActivity);
        editText = (EditText) findViewById(R.id.edittext_grouparticlereply);
        replyImageView = (ImageView) findViewById(R.id.imageview_grouparticlereply);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_grouparticlereply);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        replyImageView.setOnClickListener(this);

        setSupportActionBar(toolbar);
        editText.setHint("回复文章");
        item = (GroupArtcleListItem) getIntent().getSerializableExtra(Const.IntentKey_To_GroupArticleReplyActivity);
        String title = item.getTitle();
        if (title.length() > 16)  title = title.substring(0,15) + "...";
        getSupportActionBar().setTitle("回复 " + title);

        data = new ArrayList<>();
        adapter = new GroupArticleReplyAdapter(getApplicationContext(),data, new GroupArticleReplyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        getGroupArticlesReply();
    }

    private void getGroupArticlesReply(){

        final PProgressDialog pd = new PProgressDialog(this).createDialog(getResources().getString(R.string.progressdialog_default_msg));
        pd.showPd();

        new GroupArticleReplyGet().getGroupArticleRplyByOKhttp(this, item.getId(), "999",
                new GroupArticleReplyGet.SuccessCallback() {
                    @Override
                    public void onSuccess(ArrayList<ReplyItem> result) {
                        data = result;
                        //XGUtil.LogUitl(getClass().getName() + "  result " + result.get(2).getAuthor_Nickename());
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                        pd.dismissPd();
                    }
                }, new GroupArticleReplyGet.FaileCallback() {
                    @Override
                    public void onFaile(int resultCode) {
                        pd.dismissPd();
                        XGUtil.ToastUtil(getApplicationContext(),getResources().getString(R.string.toast_get_faile));
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_grouparticlesreply, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
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
        switch (v.getId()){
            case R.id.imageview_grouparticlereply:
                String reply = editText.getText().toString();
                if(TextUtils.isEmpty(reply)){
                    return;
                }

                editText.setText("");

                String ukey = XGUtil.SPGetStringUtil(this, Const.SPKey_Ukey);
                String token = XGUtil.SPGetStringUtil(this, Const.SPKey_Token);
                XGUtil.LogUitl(getClass().getName() + "read done : parseCookie  ukey:" + ukey + "  token:"+token);
                if (TextUtils.isEmpty(token) || TextUtils.isEmpty(ukey)){
                    XGUtil.ToastUtil(this,"token或token为空");
                    return;
                }

                new ArticleReplyPost().postReply(this, ArticleReplyPost.PostType.ReplyGroupAricicle, reply, item.getId(), token, ukey,
                        new ArticleReplyPost.SuccessCallback() {
                            @Override
                            public void onSuccess(ReplyItem newReply) {
                                data.add(newReply);
                                adapter.setData(data);
                                adapter.notifyDataSetChanged();
                            }
                        }, new ArticleReplyPost.FaileCallback() {
                            @Override
                            public void onFalie() {

                            }
                        });
                break;
            default:
        }

    }
}
