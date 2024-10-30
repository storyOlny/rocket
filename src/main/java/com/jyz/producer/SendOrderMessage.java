package com.jyz.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;


/**
 * 发送顺序消息
 */
public class SendOrderMessage {

    public static void send() {
        DefaultMQProducer producer = new DefaultMQProducer("jyzProduceGroup");
        producer.setNamesrvAddr("120.46.222.252:9876");
        try {
            producer.start();
            Message msg = new Message("jyzTopicTest", "tagA", "mes2", "helloOrder".getBytes());
            Integer hashKey = 1;
            //这个lamda表达式，是通过new MessageQueueSelector选择发送到某个队列，同步发送
            SendResult sendResult = producer.send(msg, (mqs, msg1, arg) -> {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }, hashKey);
            System.out.println(sendResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}
