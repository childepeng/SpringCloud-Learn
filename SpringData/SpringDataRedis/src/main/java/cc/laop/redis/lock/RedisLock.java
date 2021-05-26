package cc.laop.redis.lock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * @Auther: peng
 * @Date: create in 2021/5/25 18:11
 * @Description: Redis分布式锁实现，还存在两个缺陷：1、不可重入；2、锁超时会自动释放，没有自动续期
 */
public class RedisLock implements DistributedLock {

    private RedisTemplate redisTemplate;

    private static final String LOCK_PREFIX = "REDIS_LOCK:";
    private String lockKey;

    /**
     * 自旋间隔时间， 默认：500ms
     */
    private Duration spinInterval = Duration.ofMillis(50);

    /**
     * 获取锁的请求标识，或者线程ID
     */
    private String requestId;

    /**
     * 资源锁定的最长时间
     */
    private Duration maxLockDuration;

    public static RedisLock of(RedisTemplate redisTemplate, String resource) {
        return of(redisTemplate, resource, String.valueOf(Thread.currentThread().getId()), Duration.ofSeconds(30));
    }

    /**
     * @param redisTemplate
     * @param resource        待锁定资源
     * @param requestId       当前请求标识，或者线程ID
     * @param maxLockDuration 最长锁定时间
     * @return
     */
    public static RedisLock of(RedisTemplate redisTemplate, String resource, String requestId,
                               Duration maxLockDuration) {
        Assert.notNull(redisTemplate, "RedisTemplate must not be null!");
        Assert.hasLength(resource, "Lock Resource must not be null!");
        Assert.notNull(maxLockDuration, "Lock Expire must not be null");
        RedisLock lock = new RedisLock();
        lock.redisTemplate = redisTemplate;
        lock.lockKey = LOCK_PREFIX + resource;
        lock.requestId = requestId;
        lock.maxLockDuration = maxLockDuration;
        return lock;
    }

    /**
     * 尝试获取锁
     *
     * @return true: 获取到锁; false: 未获取到锁
     */
    @Override
    public boolean tryLock(Duration waitTimeout) {
        Assert.notNull(waitTimeout, "WaitTimeout must not be null");
        boolean acquire;
        long starttime = System.currentTimeMillis();
        while (true) {
            try {
                acquire = tryAcquire();
                if (acquire) {
                    break;
                }
                Thread.sleep(spinInterval.toMillis());
                if (waitTimeout != null && System.currentTimeMillis() - starttime > waitTimeout.toMillis()) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return acquire;
    }


    @Override
    public boolean lock() {
        return tryAcquire();
    }

    private boolean tryAcquire() {
        Boolean bool = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, maxLockDuration);
        return isTure(bool);
    }


    /**
     * 释放锁
     *
     * @return
     */
    @Override
    public boolean unlock() {
        String value = (String) redisTemplate.opsForValue().get(lockKey);
        if (requestId.equals(value)) {
            redisTemplate.delete(lockKey);
            return true;
        }
        return false;
    }

    private boolean isTure(Boolean bool) {
        return bool == null ? false : bool;
    }

}
