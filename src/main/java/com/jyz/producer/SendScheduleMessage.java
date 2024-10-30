package com.jyz.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

/**
 * @author jyz
 * @date 2021/7/27
 * @Description 发送延迟消息和定时消息
 */
public class SendScheduleMessage {


    /**
     * 发送延迟消息，setDelayTimeLevel:1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public static void sendDelayMessage() {
        System.out.println("发送延迟消息");
        DefaultMQProducer producer = new DefaultMQProducer();
        try {
            producer.setNamesrvAddr("120.46.222.252:9876");
            producer.setProducerGroup("jyzProduceGroup");
            producer.start();
            Message message = new Message("jyzTopicTest", "tagA", "mes3", "scheduleMessage".getBytes(StandardCharsets.UTF_8));
            message.setDelayTimeLevel(3);//10s延时 默认的延迟级别
            //自定义的延时消息
//            message.setDeliverTimeMs(System.currentTimeMillis() + 10_000L);
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            producer.shutdown();
        }

    }

    /**
     * 发送定时消息： setUserProperty("__STARTDELIVERTIME", String.valueOf(timeStamp));
     */
    public static void sendScheduleMessage() {
        System.out.println("发送定时消息");
        DefaultMQProducer producer = new DefaultMQProducer();
        try {
            long timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-10-01 00:00:00").getTime();
            producer.setNamesrvAddr("120.46.222.252:9876");
            producer.setProducerGroup("jyzProduceGroup");
            producer.start();
            Message message = new Message("jyzTopicTest", "tagA", "mes2", "scheduleMessage".getBytes(StandardCharsets.UTF_8));
            message.setDeliverTimeMs(timeStamp);
            producer.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            producer.shutdown();
        }
    }
}
