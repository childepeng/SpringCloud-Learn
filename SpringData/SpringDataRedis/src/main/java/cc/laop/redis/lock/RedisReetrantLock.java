package cc.laop.redis.lock;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: peng
 * @Date: create in 2021/5/26 15:40
 * @Description: Redis分布式可重入锁
 */
public class RedisReetrantLock implements DistributedLock {

    /**
     * 用于存储当前线程获取锁的次数
     */
    private static final ThreadLocal<AtomicInteger> LOCAL_REETRANT = new ThreadLocal<>();

    private RedisLock redisLock;

    public RedisReetrantLock(RedisTemplate redisTemplate, String resource, Duration lockExpire) {
        String threadid = String.valueOf(Thread.currentThread().getId());
        redisLock = RedisLock.of(redisTemplate, resource, threadid, lockExpire);
    }

    /**
     * @return
     */
    @Override
    public boolean lock() {
        AtomicInteger reentrantNum = LOCAL_REETRANT.get();
        if (reentrantNum == null) {
            boolean acquire = redisLock.lock();
            if (acquire) {
                reentrantNum = new AtomicInteger(1);
                LOCAL_REETRANT.set(reentrantNum);
            }
            return acquire;
        } else {
            reentrantNum.incrementAndGet();
            return true;
        }
    }

    @Override
    public boolean tryLock(Duration timeout) {
        AtomicInteger reentrantNum = LOCAL_REETRANT.get();
        if (reentrantNum == null) {
            boolean acquire = redisLock.tryLock(timeout);
            if (acquire) {
                reentrantNum = new AtomicInteger(1);
                LOCAL_REETRANT.set(reentrantNum);
            }
            return acquire;
        } else {
            reentrantNum.incrementAndGet();
            return true;
        }
    }

    @Override
    public boolean unlock() {
        AtomicInteger reentrantNum = LOCAL_REETRANT.get();
        if (reentrantNum != null) {
            if (reentrantNum.getAndDecrement() == 1) {
                redisLock.unlock();
                LOCAL_REETRANT.remove();
            }
        }
        return false;
    }
}
