package com.lei.mq.schedule;

import com.lei.mq.handler.PhoneMessageCreator;
import com.lei.mq.handler.ThirdMessageCreator;
import com.lei.mq.handler.WxMessageCreator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
@Configuration
@EnableScheduling
public class MessageCreatorTask {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private PhoneMessageCreator phoneMessageCreator;
    @Autowired
    private WxMessageCreator wxMessageCreator;
    @Autowired
    private ThirdMessageCreator thirdMessageCreator;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Scheduled(fixedDelay = 10000)
    public void messageCreator(){
        executorService.execute(phoneMessageCreator);
        executorService.execute(wxMessageCreator);
        executorService.execute(thirdMessageCreator);
    }
}
