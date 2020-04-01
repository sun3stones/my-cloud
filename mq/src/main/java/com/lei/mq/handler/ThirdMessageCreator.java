package com.lei.mq.handler;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class ThirdMessageCreator implements Runnable{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run() {
        String message = "合作商消息生成：thread-id:"+Thread.currentThread().getId() +",message:"+ UUID.randomUUID()+ ",time"+System.currentTimeMillis();
        rabbitTemplate.convertAndSend("testQueen1",message);
        System.out.println(message);
    }

}
