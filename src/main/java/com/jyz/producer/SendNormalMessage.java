package com.jyz.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 普通消息发送
 */
public class SendNormalMessage {

    /**
     * 异步发送消息
     */
    public static void asyncSend() {
        DefaultMQProducer producer = new DefaultMQProducer("jyzProduceGroup");
        try {
            producer.setNamesrvAddr("120.46.222.252:9876");
            producer.setRetryTimesWhenSendAsyncFailed(2);

            producer.start();
            Message mes = new Message("jyzTopicTest", "tagA", "mes1", "hello".getBytes());
            producer.send(mes, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult.toString());
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }


    }

    /**
     * 同步发送消息
     */

    public static void syncSend() {
        DefaultMQProducer producer = new DefaultMQProducer("jyzProduceGroup");
        try {
            producer.setNamesrvAddr("120.46.222.252:9876");
            producer.setRetryTimesWhenSendAsyncFailed(2);
            producer.start();
            Message mes = new Message("jyzTopicTest", "tagA", "mes1", "hello".getBytes());
            producer.send(mes, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }


    }
}
