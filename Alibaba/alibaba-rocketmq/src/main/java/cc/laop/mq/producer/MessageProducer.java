package cc.laop.mq.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Auther: peng
 * @Date: create in 2021/6/3 18:33
 * @Description:
 */
@Component
public class MessageProducer {

    @Autowired
    private RocketMQTemplate template;

    /**
     * 同步发送
     *
     * @param topic
     * @param msg
     */
    public void send(String topic, String msg) {
        Message<String> message = MessageBuilder.withPayload(msg).build();
        // 同步发送消息
        template.send(topic, message);
    }

    /**
     * 异步发送
     *
     * @param topic
     * @param msg
     */
    public void asyncSend(String topic, String msg) {
        Message<String> message = MessageBuilder.withPayload(msg).build();
        // 异步发送
        template.asyncSend(topic, message, sendCallback());
    }


    /**
     * mid作为消息的key，可以保证消息始终都被插入到固定队列
     * 同一个队列中可以保证消息的有序性
     *
     * @param topic
     * @param mkey
     * @param msg
     */
    public void sendOrderly(String topic, String mkey, String msg) {
        final Message<String> message = MessageBuilder.withPayload(msg).build();

        // 同步有序消息
        template.syncSendOrderly(topic, message, mkey);

        // 异步有序消息
        // template.asyncSendOrderly(topic, message, mid, sendCallback());
    }


    /**
     * 事务消息
     *
     * @param topic
     * @param tid
     * @param msg
     */
    public void sendWithTransaction(String topic, String tid, String msg) {
        final Message<String> message = MessageBuilder
                .withPayload(msg)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, tid)
                .build();
        TransactionSendResult sendResult = template.sendMessageInTransaction(topic, message, null);
    }


    private SendCallback sendCallback() {
        return new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable e) {

            }
        };
    }

}
