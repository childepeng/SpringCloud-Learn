package cc.laop.redis.lock;

import java.time.Duration;

/**
 * @Auther: peng
 * @Date: create in 2021/5/26 15:32
 * @Description:
 */
public interface DistributedLock {

    boolean lock();

    boolean tryLock(Duration timeout);

    boolean unlock();

}
