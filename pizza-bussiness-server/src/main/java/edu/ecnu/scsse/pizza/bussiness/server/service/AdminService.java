package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.data.domain.AdminEntity;
import edu.ecnu.scsse.pizza.data.repository.AdminJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AdminService extends SessionService {
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    private AdminJpaRepository adminJpaRepository;

    public LoginResponse adminLogin(LoginRequest loginRequest){
        String adminName = loginRequest.getAdminName();
        Optional<AdminEntity> adminEntity = adminJpaRepository.findByUsername(adminName);
        LoginResponse loginResponse;
        if (adminEntity.isPresent()){
            //try {
                loginResponse = new LoginResponse();
                AdminEntity admin = adminEntity.get();
                if(admin.getPassword().equals(loginRequest.getPassword())){
                    loginResponse.setResultType(ResultType.SUCCESS);
                    loginResponse.setAdminId(admin.getId());
                    log.info("Admin {} success to login.",admin.getUsername());
                    HttpSession session = request.getSession();
                    session.setAttribute("adminId", admin.getId());
                }
                else{
                    loginResponse.setResultType(ResultType.FAILURE);
                    loginResponse.setErrorMsg("密码错误");
                    log.info("Wrong password for admin {}.",admin.getUsername());

                }
            /*} catch (BusinessServerException e) {
                loginResponse = new LoginResponse(e);
                log.error("Fail to find the admin with username {}.", adminName, e);
            }*/
        } else {
            NotFoundException e = new NotFoundException(String.format("Admin with username %s is not found.", adminName));
            loginResponse = new LoginResponse(e);
            log.warn("Fail to find the admin with username {}.", adminName, e);
        }
        return loginResponse;
    }
}
