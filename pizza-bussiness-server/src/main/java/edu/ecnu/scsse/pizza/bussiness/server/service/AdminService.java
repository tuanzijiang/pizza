package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.ExceptionType;
import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.LoginResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.ResultType;
import edu.ecnu.scsse.pizza.data.domain.AdminEntity;
import edu.ecnu.scsse.pizza.data.repository.AdminJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Optional;

@Service
public class AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

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
                }
                else{
                    loginResponse.setResultType(ResultType.FAILURE);
                    loginResponse.setErrorMsg("密码错误");

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
