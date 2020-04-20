package com.act.acttivity.controller;

import com.act.acttivity.config.SecurityUtil;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ProcessRuntime processRuntime; //实现流程定义相关操作

    @Autowired
    private TaskRuntime taskRuntime; //实现任务相关操作

    @Autowired
    private SecurityUtil securityUtil;//SpringSecurity相关的工具类

    @RequestMapping("/hello")
    public String hello (){
        return "hello activity";
    }

    //查询任务，如果有任务就执行任务
    @RequestMapping("/doTask")
    public void testTask(){
        securityUtil.logInAs("ryandawsonuk");//认证
        Page<Task> taskPage=taskRuntime.tasks(Pageable.of(0,10));
        if (taskPage.getTotalItems()>0){
            for (Task task:taskPage.getContent()){
                System.out.println("任务："+task);

                //拾取任务
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());

                //执行任务
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
            }
        }

        //完成后再次查询任务
        System.out.println("再次查询任务的结果：");
        Page<Task> taskPage2=taskRuntime.tasks(Pageable.of(0,10));
        if (taskPage2.getTotalItems()>0){
            System.out.println("任务："+taskPage2.getContent());
        }
    }


}
