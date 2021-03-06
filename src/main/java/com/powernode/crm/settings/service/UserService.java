package com.powernode.crm.settings.service;

import com.powernode.crm.exception.LoginException;
import com.powernode.crm.settings.domain.User;

import java.util.List;

public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
