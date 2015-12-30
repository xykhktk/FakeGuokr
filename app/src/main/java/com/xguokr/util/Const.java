package com.xguokr.util;


public class Const {

	public static final String CHARSET = "utf-8";
	
	public static final String URL_kexueren = "http://www.guokr.com/apis/minisite/article.json";
	public static final String URL_lazyload = "http://m.guokr.com/sso/mobile/?suppress_prompt=1&lazy=y&success=http%3A%2F%2Fm.guokr.com%2F";
	public static final String URL_ArticleReply = "http://apis.guokr.com/minisite/article_reply.json";
	public static final String URL_QuestionTag = "http://www.guokr.com/ask/tag/category/";
	public static final String URL_GroupTag = "http://www.guokr.com/group/rank/popular/";
	public static final String URL_LOGIN = "https://account.guokr.com/sign_in/?display=mobile";
	public static final String URL_loginSUCCESS_1 = "http://m.guokr.com/";
	public static final String URL_loginSUCCESS_2 = "http://www.guokr.com/";
	public static final String URL_UserIndo = "http://apis.guokr.com/community/user/";
	public static final String URL_ReplyQuestionAnswer = "http://apis.guokr.com/ask/answer.json";
	public static final String URL_ReplyKexuerenArticle = "http://apis.guokr.com/minisite/article_reply.json";
	public static final String URL_ReplyGroupArticle = "http://apis.guokr.com/group/post_reply.json";
	
	public static final String HttpKey_retrieve_type = "retrieve_type";
	public static final String HttpKey_limit = "limit";
	public static final String HttpKey_offset = "offset";
	public static final String HttpKey_articleid = "article_id";
	public static final String HttpKey_csrf_token = "csrf_token";
	public static final String HttpKey_content   = "content";
	public static final String HttpKey_access_token   = "access_token";
	public static final String HttpKey_group_id   = "group_id";
	public static final String HttpKey_post_id   = "post_id";
	public static final String HttpKey_page   = "page";
	public static final String HttpKey_tag_name   = "tag_name";
	public static final String HttpKey_question_id   = "question_id";

	public static final String HttpValue_by_subject = "by_subject";
	public static final String HttpValue_by_group = "by_group";
	public static final String HttpValue_by_post = "by_post";
	public static final String HttpValue_by_tag = "by_tag";
	public static final String HttpValue_by_question = "by_question";

	public static final String IntentKey_To_ArticleActivity = "IntentKey_To_ArticleActivity";
	public static final String IntentKey_To_ArticleReplyActivity = "IntentKey_To_ArticleReplyActivity";
	public static final String IntentKey_To_ListUnderAGroupTagActivity = "IntentKey_To_ListUnderAGroupTagActivity";
	public static final String IntentKey_To_GroupArticleReplyActivity = "IntentKey_To_GroupArticleReplyActivity";
	public static final String IntentKey_To_QuestionArticleListActivity = "IntentKey_To_QuestionArticleListActivity";
	
	public static final String CookieKey_Token= "_32353_access_token";
    public static final String CookieKey_Ukey = "_32353_ukey";
    
    public static final String SPKey_CookieStr = "CookieStr";
    public static final String SPKey_Token = "Token";
    public static final String SPKey_Ukey = "Ukey";
	public static final String SPKey_Theme = "Theme";
	public static final String SPKey_DownloadPicMode = "DownloadPicMode";

	public static final String Theme_Day = "Day";
	public static final String Theme_Night = "Night";
	public static final String DownloadPicMode_OnlyWifi = "OnlyWifi";
	public static final String DownloadPicMode_Always = "Always";

	
    public static final int HttpResultCode_UnsupportedEncodingException = 0x10001;
    public static final int HttpResultCode_ClientProtocolException = 0x10002;
    public static final int HttpResultCode_IOException = 0x10003;
	public static final int HttpResultCode_TokenEmpty = 0x10004;

	public static final int Code_Noresult = 0x100001;
	public static final String LogiTag = "XGUOKR";
	
}
