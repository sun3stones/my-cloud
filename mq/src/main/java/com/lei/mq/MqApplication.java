package com.lei.mq;

import com.lei.mq.handler.PhoneMessageCreator;
import com.lei.mq.handler.ThirdMessageCreator;
import com.lei.mq.handler.WxMessageCreator;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.lei.mq")
public class MqApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqApplication.class, args);
    }

    @Bean
    public Queue testQueen1() {
        return new Queue("testQueen1", true); //队列持久
    }

    @RabbitListener(queues = "testQueen1")
    public void processMessage(String content) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("消费消息："+content);
    }

    @Bean("handlerThreadPool")
    public ExecutorService handlerThreadPool(){
        ExecutorService executorService =  Executors.newFixedThreadPool(3);
        return executorService;
    }


}
