package cc.laop.mq.consumer;

import cc.laop.mq.Topics;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @Auther: peng
 * @Date: create in 2021/6/3 14:22
 * @Description:
 */
@RocketMQMessageListener(topic = Topics.TOPIC_001, consumerGroup = "${rocketmq.consumer.group}")
public class MessageConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("Receive: " + s);
    }
}
