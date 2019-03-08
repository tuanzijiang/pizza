package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelieveService {
    private static final Logger log = LoggerFactory.getLogger(DelieveService.class);

    @Autowired
    private PizzaShopJpaRepository pizzaShopJpaRepository;

}
