package edu.ecnu.scsse.pizza.bussiness.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class SessionService {
    @Autowired
    HttpServletRequest request;

    public int getAdminId(){
        try {
            HttpSession session = request.getSession();
            return Integer.parseInt(session.getAttribute("adminId").toString());
        }
        catch (NullPointerException e){
            return -1;
        }
    }
}
