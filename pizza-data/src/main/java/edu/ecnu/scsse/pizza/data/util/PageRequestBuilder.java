package edu.ecnu.scsse.pizza.data.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageRequestBuilder {

    private PageRequest pageRequest;

    private int page = 0;

    private int count = 1;

    private List<Sort.Order> orders = new ArrayList<>();

    public PageRequestBuilder page(int page) {
        if (page > 0) {
            this.page = page;
        }
        return this;
    }

    public PageRequestBuilder count(int count) {
        if (count > 0) {
            this.count = count;
        }
        return this;
    }

    public PageRequestBuilder asc(String fieldName) {
        orders.add(Sort.Order.asc(fieldName));
        return this;
    }

    public PageRequestBuilder desc(String fieldName) {
        orders.add(Sort.Order.desc(fieldName));
        return this;
    }

    public PageRequest build() {
        if (!orders.isEmpty()) {
            return PageRequest.of(page, count, Sort.by(orders));
        }
        return PageRequest.of(page, count);
    }

    public PageRequest queryFirst() {
        page = 0;
        count = 1;
        return this.build();
    }
}
