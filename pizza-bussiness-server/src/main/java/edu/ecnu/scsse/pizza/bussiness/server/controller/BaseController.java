package edu.ecnu.scsse.pizza.bussiness.server.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController{
    @Autowired
    protected HttpServletRequest request;

    public int getCurrentAdminId(){
        try {
            HttpSession session = request.getSession();
            return Integer.parseInt(session.getAttribute("adminId").toString());
        }catch (NullPointerException e){
            return -1;
        }
    }
}
