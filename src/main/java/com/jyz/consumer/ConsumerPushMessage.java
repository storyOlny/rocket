package com.jyz.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;


/**
 * 消费者推送消息
 */
public class ConsumerPushMessage {

    public static void getConsumerMessage() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("jyzProduceGroup");
            consumer.setNamesrvAddr("120.46.222.252:9876");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.subscribe("jyzTopicTest", "*");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            System.out.println("main thread is:" + Thread.currentThread().getName());
            consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
                msg.forEach(message -> System.out.println(new String(message.getBody())));
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msg);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();

            System.out.printf("Consumer Started.%n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
