package com.powernode.crm.settings.web.controller;

import com.powernode.crm.exception.LoginException;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.settings.service.impl.UserServiceImpl;
import com.powernode.crm.utils.MD5Util;
import com.powernode.crm.utils.PrintJson;
import com.powernode.crm.utils.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController extends HttpServlet {

    @Resource
    private UserService us;

    @RequestMapping("/settings/user/login.do")
    @ResponseBody
    private Map<String,Object> login(HttpServletRequest request, HttpServletResponse response, HttpSession session, User user) throws LoginException {

        System.out.println("进入到验证登录操作");

        String loginAct = user.getLoginAct();
        String loginPwd = user.getLoginPwd();
        //将密码的明文形式转换为MD5的密文形式
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收浏览器端的ip地址
        String ip = request.getRemoteAddr();
        System.out.println("--------------ip:"+ip);

        // 未来的业务层开发,统一使用代理类形态的接口对象
        user = us.login(loginAct,loginPwd,ip);
        System.out.println(user);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", true);

        session.setAttribute("user", user);

        return map;
        /*try {

            // 未来的业务层开发,统一使用代理类形态的接口对象
            user = us.login(loginAct,loginPwd,ip);

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success", true);

            session.setAttribute("user", user);

            return map;

            //如果程序执行到此处，说明业务层没有为controller抛出任何的异常
            //表示登录成功
            *//*

                {"success":true}

             *//*
            //PrintJson.printJsonFlag(response, true);

        }catch(Exception e){
            e.printStackTrace();

            //一旦程序执行了catch块的信息，说明业务层为我们验证登录失败，为controller抛出了异常
            //表示登录失败
            *//*

                {"success":true,"msg":?}

             *//*
            System.out.println("失败 了");
            String msg = e.getMessage();
            *//*

                我们现在作为contoller，需要为ajax请求提供多项信息

                可以有两种手段来处理：
                    （1）将多项信息打包成为map，将map解析为json串
                    （2）创建一个Vo
                            private boolean success;
                            private String msg;


                    如果对于展现的信息将来还会大量的使用，我们创建一个vo类，使用方便
                    如果对于展现的信息只有在这个需求中能够使用，我们使用map就可以了


             *//*
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success", false);
            map.put("msg", msg);
            //PrintJson.printJsonObj(response, map);

            return map;
        }*/


    }
}

