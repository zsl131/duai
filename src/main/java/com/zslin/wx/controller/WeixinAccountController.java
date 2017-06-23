package com.zslin.wx.controller;

import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.repository.SimpleSpecificationBuilder;
import com.zslin.sms.tools.RandomTools;
import com.zslin.sms.tools.SmsConfig;
import com.zslin.sms.tools.SmsTools;
import com.zslin.web.model.Account;
import com.zslin.web.model.Feedback;
import com.zslin.web.service.IAccountService;
import com.zslin.web.service.IFeedbackService;
import com.zslin.wx.tools.SessionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 钟述林 393156105@qq.com on 2017/3/1 17:13.
 */
@Controller
@RequestMapping(value = "wx/account")
public class WeixinAccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private SmsTools smsTools;

    @Autowired
    private SmsConfig smsConfig;

    //微信用户个人中心
    @GetMapping(value = "me")
    public String me(Model model, HttpServletRequest request) {
        String openid = SessionTools.getOpenid(request);
        Account account = accountService.findByOpenid(openid);
        model.addAttribute("account", account);
        model.addAttribute("pullCount", accountService.findPullCount(account.getId()));
        model.addAttribute("feedbackCount", feedbackService.findCount(openid)); //消息数量

        return "weixin/account/me";
    }

    @GetMapping(value = "feedbackList")
    public String feedbackList(Model model, Integer page, HttpServletRequest request) {
        String openid = SessionTools.getOpenid(request);
        SimpleSpecificationBuilder builder = new SimpleSpecificationBuilder("openid", "eq", openid);
        Page<Feedback> datas = feedbackService.findAll(builder.generate(),
                SimplePageBuilder.generate(page, SimpleSortBuilder.generateSort("createDate_d")));
        model.addAttribute("datas", datas);
        return "weixin/account/feedbackList";
    }

    @GetMapping(value = "modifyPhone")
    public String modifyPhone(Model model, HttpServletRequest request) {
        String openid = SessionTools.getOpenid(request);
        Account a = accountService.findByOpenid(openid);
        model.addAttribute("account", a);
        return "weixin/account/modifyPhone";
    }

    @PostMapping(value = "sendCode")
    public @ResponseBody String sendCode(String phone, HttpServletRequest request) {
        try {
            Account a = accountService.findByPhone(phone);
            if(a!=null) {return "-1";}
            String code = RandomTools.randomNum4();
            request.getSession().setAttribute("sms_code", code);
            smsTools.sendMsg(Integer.parseInt(smsConfig.getSendCodeIid()), phone, "code", code);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }
}
