package com.lh.controller;

import com.alibaba.fastjson.JSONObject;
import com.lh.entity.User;
import com.lh.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);

    @Resource(name = "userServiceImpl")
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/checkUser.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkUser(HttpServletRequest request, HttpServletResponse response, User userParam) {
        User user = null;
        JSONObject result = new JSONObject();
        try {
            logger.info("User login : username is :" + userParam.getUsername());
            user = userService.getUser(userParam);
            if (user != null) {
                logger.info("User login success: name is :" + user.getName());
                result.put("data", user);
                return result;
            } else {
                logger.error("User login failue: username is "  + userParam.getUsername());
                //result.put("success", "false");
                result.put("message", "User is not exist!");
                response.setStatus(401);
                return result;
            }
        } catch (Exception e) {
            logger.error("checkuser error." + e.getMessage(), e);
            //result.put("success", "false");
            result.put("message", "System error!");
            response.setStatus(401);
            return result;
        }
    }


}

