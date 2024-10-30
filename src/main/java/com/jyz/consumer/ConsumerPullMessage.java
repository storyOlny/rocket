package com.jyz.consumer;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消费者拉取消息
 */
public class ConsumerPullMessage {

    public static void getConsumerMessage() throws MQClientException {
        DefaultLitePullConsumer pullConsumer = new DefaultLitePullConsumer();
        pullConsumer.setNamesrvAddr("120.46.222.252:9876");
        pullConsumer.setConsumerGroup("jyzGroup");
        //订阅topic
        pullConsumer.subscribe("jyzTopicTest", "*");
        //保存topic和队列信息
        pullConsumer.start();
        try {
            //pullConsumer触发监听器，不停的在拉取消息，并保存到本地。
            while (true) {
                List<MessageExt> messageExts = pullConsumer.poll();
                System.out.printf("%s%n recevice", messageExts);
            }
        } finally {
            pullConsumer.shutdown();
        }
    }

    public static void main(String[] args) throws MQClientException {
        getConsumerMessage();
    }
}
