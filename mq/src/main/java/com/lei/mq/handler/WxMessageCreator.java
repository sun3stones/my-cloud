package com.lei.mq.handler;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
@Component
public class WxMessageCreator implements Runnable{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run() {
        String message = "微信商消息生成：thread-id:"+Thread.currentThread().getId() +",message:"+ UUID.randomUUID()+ ",time"+System.currentTimeMillis();
        rabbitTemplate.convertAndSend("testQueen1",message);
        System.out.println(message);
    }
}
