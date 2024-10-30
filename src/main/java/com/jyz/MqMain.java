package com.jyz;

import com.jyz.consumer.ConsumerPushMessage;
import com.jyz.producer.SendNormalMessage;
import com.jyz.producer.SendOrderMessage;
import com.jyz.producer.SendScheduleMessage;

/**
 * mq测试类
 */
public class MqMain {
    public static void main(String[] args) {
        //send normal message
        SendNormalMessage.syncSend();
        //send order message
//        SendOrderMessage.send();

//        ConsumerPushMessage.getConsumerMessage();

//        SendScheduleMessage.sendDelayMessage();
    }
}
