package com.zslin.wx.tools;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.basic.tools.DateTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.web.model.Account;
import com.zslin.web.model.Article;
import com.zslin.web.model.Feedback;
import com.zslin.web.model.WeixinConfig;
import com.zslin.web.service.IAccountService;
import com.zslin.web.service.IArticleService;
import com.zslin.web.service.IFeedbackService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by 钟述林 393156105@qq.com on 2017/1/24 22:26.
 */
@Component
public class DatasTools {

    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ExchangeTools exchangeTools;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private ConfigTools configTools;

    @Autowired
    private AccountTools accountTools;

    @Autowired
    private EventTools eventTools;

    /** 当用户取消关注时 */
    public void onUnsubscribe(String openid) {
        accountService.updateStatus(openid, "0");
    }

    /**
     * 添加文本内容
     * @param openid 用户Openid
     * @param builderName 开发者微信号
     * @param content 具体内容
     * @return
     */
    public String onEventText(String openid, String builderName, String content) {
        WeixinConfig config = wxConfig.getConfig();
        if(content==null || "".equals(content.trim()) || "?".equals(content.trim())
                || "？".equals(content.trim()) || "1".equals(content.trim())
                || "help".equals(content.toLowerCase().trim())) { //帮助
            return WeixinXmlTools.createTextXml(openid, builderName, buildHelpStr());
        } else if("1".equals(content.trim()) || "gy".equals(content.toLowerCase().trim()) ||
                "guanyu".equals(content.toLowerCase().trim()) || "about".equals(content.toLowerCase().trim())||
                "关于".equals(content.trim())) { //关于
            Article article = articleService.findOne(1);
            return WeixinXmlTools.buildArticleStr(openid, builderName, article, config.getUrl());
        } else if("2".equals(content.trim()) || "jf".equals(content.toLowerCase().trim()) ||
                "jifen".equals(content.toLowerCase().trim()) || "积分".equals(content.trim())) { //ID为2的文章
            Article article = articleService.findOne(2);
            return WeixinXmlTools.buildArticleStr(openid, builderName, article, config.getUrl());
        } else if("hlx".equals(content.toLowerCase())) { //关注情况
            eventTools.eventRemind(openid, "查询提醒", "关注情况如下", DateTools.date2Str(new Date(), "yyyy-MM-dd"), accountTools.buildAccountStr(), "");
            return "";
        } else {
            Feedback f = new Feedback();
            f.setCreateDate(new Date());
            f.setCreateLong(System.currentTimeMillis());
            f.setCreateDay(DateTools.date2Str(new Date()));
            f.setCreateTime(DateTools.formatDate(new Date()));
            f.setOpenid(openid);
            f.setStatus("0");
            f.setType("text");
            f.setContent(content);
            Account a = accountService.findByOpenid(openid);
            if (a != null) {
                f.setAccountId(a.getId());
                f.setNickname(a.getNickname());
                f.setHeadimgurl(a.getHeadimgurl());
            }
            feedbackService.save(f);

//            List<String> adminOpenids = accountService.findOpenid(AccountTools.ADMIN);
            List<String> adminOpenids = accountTools.getOpenid(AccountTools.ADMIN);
            StringBuffer sb = new StringBuffer();
            sb.append("反馈用户：").append(f.getNickname()).append(" \\n")
                    .append("反馈内容：").append(content);
            eventTools.eventRemind(adminOpenids, "在线反馈", "收到在线反馈信息", NormalTools.curDate("yyyy-MM-dd HH:mm"), sb.toString(), "/wx/feedback/list");
            return "";
        }
    }

    private String buildHelpStr() {
        StringBuffer sb = new StringBuffer();
        sb.append("help    ").append("：").append("查看帮助").append("\n")
          .append("gy      ").append("：").append("查看昭通汉丽轩").append("\n")
          .append("jf      ").append("：").append("查询积分说明").append("\n")
          .append("hlx     ").append("：").append("查询关注情况");
        return sb.toString();
    }

    /** 添加图片内容 */
    public void onEventImage(String openid, String picPath, String mediaId) {
        Feedback f = new Feedback();
        f.setCreateDate(new Date());
        f.setCreateLong(System.currentTimeMillis());
        f.setCreateDay(DateTools.date2Str(new Date()));
        f.setCreateTime(DateTools.formatDate(new Date()));
        f.setOpenid(openid);
        f.setStatus("0");
        f.setType("image");
        f.setPicUrl(picPath);
        f.setMediaId(mediaId);
        f.setFilePath(exchangeTools.saveMedia(mediaId, configTools.getUploadPath("feedback/")+ UUID.randomUUID().toString()).replace(configTools.getUploadPath(), "\\"));
        Account a = accountService.findByOpenid(openid);
        if(a!=null) {
            f.setAccountId(a.getId());
            f.setNickname(a.getNickname());
            f.setHeadimgurl(a.getHeadimgurl());
        }
        feedbackService.save(f);

    }

    /** 当用户关注时 */
    public void onSubscribe(String openid) {
        Integer id = (Integer) accountService.queryByHql("SELECT a.id FROM Account a WHERE a.openid=?", openid);
        Account a ;
        if(id==null || id<=0) { //说明初次关注
            a = new Account();
            a.setStatus("1");
            a.setType("0");
            a.setOpenid(openid);
            a.setCreateDate(new Date());
            a.setCreateLong(System.currentTimeMillis());
            a.setCreateDay(DateTools.date2Str(new Date()));
            a.setCreateTime(DateTools.formatDate(new Date()));
        } else {
//            accountService.updateStatus(id, "1");
            a = accountService.findOne(id);
            a.setStatus("1");
        }
        a.setFollowDate(new Date());
        JSONObject jsonObj = exchangeTools.getUserInfo(openid);
        if(jsonObj!=null) {
            String jsonStr = jsonObj.toString();
            if(jsonStr.indexOf("errcode")<0 && jsonStr.indexOf("errmsg")<0) {
                String nickname = "";
                try {
                    nickname = jsonObj.getString("nickname");
                    nickname = nickname.replaceAll("[^\\u0000-\\uFFFF]", ""); //替换utf8mb4字条
                } catch (Exception e) {
                }
                a.setHeadimgurl(jsonObj.getString("headimgurl"));
                a.setNickname(nickname);
                a.setOpenid(openid);
                a.setSex(jsonObj.getInt("sex")+"");
            }
        }
        accountService.save(a);

    }

    /** 同步更新微信用户信息，主要是昵称、头像、性别 */
    public void updateAccount(Integer accountId) {
        Account a = accountService.findOne(accountId);
        JSONObject jsonObj = exchangeTools.getUserInfo(a.getOpenid());
        if(jsonObj!=null) {
            String jsonStr = jsonObj.toString();
            if(jsonStr.indexOf("errcode")<0 && jsonStr.indexOf("errmsg")<0) {
                String nickname = "";
                try {
                    nickname = jsonObj.getString("nickname");
                    nickname = nickname.replaceAll("[^\\u0000-\\uFFFF]", ""); //替换utf8mb4字符
                } catch (Exception e) {
                }
                a.setHeadimgurl(jsonObj.getString("headimgurl"));
                a.setNickname(nickname);
                a.setSex(jsonObj.getInt("sex")+"");
            }
        }
        accountService.save(a);

        updateRelation(a); //更新所有关联数据
    }

    /** 构建关注时的数据 */
    public String buildSubscribeStr(String toUser, String fromUser) {
        List<Article> articleList = articleService.findFirst();
        return WeixinXmlTools.buildSubscribeStr(toUser, fromUser, articleList, wxConfig.getConfig().getUrl());
    }

    private void updateRelation(Account a) {
        feedbackService.update(a.getNickname(), a.getHeadimgurl(), a.getOpenid());
    }
}
