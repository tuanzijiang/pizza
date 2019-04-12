package edu.ecnu.scsse.pizza.consumer.server.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class HttpUtilsIntegrateTest {

    private String FORMAT_ADDR = "上海市普陀区华东师范大学(中北校区)";
    private String ADDR1 = "上海市普陀区中山北路3663号";
    private String ADDR2 = "华东师范大学(中北校区)";

    @Test
    public void queryGeo() throws IOException {
        AmapLocation.Geocode code1 = HttpUtils.queryGeo(ADDR1);
        AmapLocation.Geocode code2 = HttpUtils.queryGeo(ADDR2);
        Assert.assertEquals(code1.getFormattedAddress(), FORMAT_ADDR);
        Assert.assertEquals(code2.getFormattedAddress(), FORMAT_ADDR);
    }
}
