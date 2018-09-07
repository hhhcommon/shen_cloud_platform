package com.shen.cloud.web;

import com.shen.cloud.client.MessageClient;
import com.shen.cloud.entity.ZmResult;
import com.sun.istack.internal.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
public class TestController {

    private final Logger logger = Logger.getLogger(getClass());
    @Resource
    private MessageClient messageClient;

    @RequestMapping(value = "getSystemNos")
    public ZmResult getSystemNos(){
        String msg = "hello feign:"+new Date();
        System.out.println("Sender:"+msg);
        logger.info("========<记录>++++++++");
        return new ZmResult("123","321");
    }
}
