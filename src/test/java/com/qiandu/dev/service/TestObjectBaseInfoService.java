package com.qiandu.dev.service;

import com.qiandu.dev.bean.ObjectDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * Created by LTN on 2016/9/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-test.xml")
public class TestObjectBaseInfoService {
    @Autowired
    ObjectBaseInfoService objectBaseInfoService;
    @Test
    public void testGetById() {
//        ObjectDetail objectDetail=objectBaseInfoService.getObjectById(2975);
//        Assert.assertNotNull(objectDetail);

    }
}
