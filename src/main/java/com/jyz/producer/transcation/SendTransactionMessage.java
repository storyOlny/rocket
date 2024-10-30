package com.jyz.producer.transcation;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 发送事务消息
 */
public class SendTransactionMessage {

    public static void send() {
        TransactionListener transactionListener = new TranscationListionImpl();
        TransactionMQProducer producer = new TransactionMQProducer("", Arrays.asList(""));

        // Uncomment the following line while debugging, namesrvAddr should be set to your local address
        producer.setNamesrvAddr("");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000), r -> {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        });
        try {
            producer.setExecutorService(executorService);
            producer.setTransactionListener(transactionListener);
            producer.start();

            String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
            for (int i = 0; i < 10; i++) {
                Message msg =
                        new Message("", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);
                Thread.sleep(10);
            }

            for (int i = 0; i < 100000; i++) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
        }

        producer.shutdown();
    }
}
