package cc.laop.redis.lock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: peng
 * @Date: create in 2021/5/26 13:54
 * @Description:
 */
@SpringBootTest
public class RedisLockTest {

    @Autowired
    private RedisTemplate redisTemplate;

    private int num = 0;
    private int threadNum = 50;

    @Test
    public void test() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        CountDownLatch cdl = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            pool.execute(() -> {
                RedisLock lock = RedisLock.of(redisTemplate, "res001");
                for (int j = 0; j < 1000; j++) {
                    if (lock.tryLock(Duration.ofSeconds(30))) {
                        num++;
                        lock.unlock();
                    }
                    // try {
                    //     Thread.sleep(10);
                    // } catch (InterruptedException e) {
                    //     e.printStackTrace();
                    // }
                }
                cdl.countDown();
            });
        }

        cdl.await();
        System.out.println(num);
    }

    @Test
    public void reentrantLockTest() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start();
        ExecutorService pool = Executors.newFixedThreadPool(50);
        CountDownLatch cdl = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            pool.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    RedisReetrantLock lock = new RedisReetrantLock(redisTemplate, "res001", Duration.ofSeconds(30));
                    if (lock.tryLock(Duration.ofSeconds(30))) {
                        // if (lock.lock()) {
                        // num++;
                        add();
                        lock.unlock();
                    }
                }
                cdl.countDown();
            });
        }
        cdl.await();
        sw.stop();
        System.out.println(sw.getLastTaskTimeMillis());
        System.out.println(num);
    }


    private void add() {
        RedisReetrantLock lock = new RedisReetrantLock(redisTemplate, "res001", Duration.ofSeconds(30));
        if (lock.tryLock(Duration.ofSeconds(30))) {
            num++;
            lock.unlock();
        }
    }

}
