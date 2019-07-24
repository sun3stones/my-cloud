package com.lei.mywechat.controller;

import com.lei.mywechat.entity.TmTask;
import com.lei.mywechat.entity.WxUser;
import com.lei.mywechat.service.ITmTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@RestController
@RequestMapping("task")
public class TaskController extends BaseController{

    @Autowired
    private ITmTaskService taskService;

    @RequestMapping("addTask")
    public String addTask(@RequestBody TmTask task, HttpServletRequest request){
        WxUser user = currentUser(request);
        task.setCreateTime(LocalDateTime.now());
        task.setUserId(user.getUserId());
        taskService.save(task);
        return "";
    }

}
