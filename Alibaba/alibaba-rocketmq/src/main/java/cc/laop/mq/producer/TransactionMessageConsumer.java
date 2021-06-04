package cc.laop.mq.producer;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: peng
 * @Date: create in 2021/6/3 18:30
 * @Description:
 */
@RocketMQTransactionListener
public class TransactionMessageConsumer implements RocketMQLocalTransactionListener {

    private Map localTransactionCache = new ConcurrentHashMap();


    /**
     * 消息发送成功之后，自行本地事务，之后将事务执行结果发送给broker
     *
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        localTransactionCache.put(message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID), 1);
        // 事务成功，提交
        // return RocketMQLocalTransactionState.COMMIT;
        // 未知
        // return RocketMQLocalTransactionState.UNKNOWN;
        // 回滚
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    /**
     * 如果broker等待事务结果超时，会主动询问事务执行结果
     *
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        Integer result = (Integer) localTransactionCache.get(message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID));
        if (result == null) {
            return RocketMQLocalTransactionState.UNKNOWN;
        } else if (result == 1) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
