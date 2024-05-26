package love.pangteen.utils.factory;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.constant.ThreadProperties;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:30
 **/
@Slf4j
public class ThreadPoolFactory {

    /**
     * 通过 threadNamePrefix 来区分不同线程池（我们可以把相同 threadNamePrefix 的线程池看作是为同一业务场景服务）。
     */
    private static final Map<String, ExecutorService> THREAD_POOLS = new ConcurrentHashMap<>();

    public static ExecutorService createCustomThreadPoolIfAbsent(String threadNamePrefix) {
        ThreadProperties properties = new ThreadProperties();
        return createCustomThreadPoolIfAbsent(properties, threadNamePrefix, false);
    }

    public static ExecutorService createCustomThreadPoolIfAbsent(String threadNamePrefix, ThreadProperties properties) {
        return createCustomThreadPoolIfAbsent(properties, threadNamePrefix, false);
    }

    public static ExecutorService createCustomThreadPoolIfAbsent(ThreadProperties properties, String threadNamePrefix, Boolean daemon) {
        ExecutorService threadPool = THREAD_POOLS.computeIfAbsent(threadNamePrefix, k -> createThreadPool(properties, threadNamePrefix, daemon));
        // 如果 threadPool 被 shutdown 的话就重新创建一个
        if (threadPool.isShutdown() || threadPool.isTerminated()) {
            THREAD_POOLS.remove(threadNamePrefix);
            threadPool = createThreadPool(properties, threadNamePrefix, daemon);
            THREAD_POOLS.put(threadNamePrefix, threadPool);
        }
        return threadPool;
    }

    private static ExecutorService createThreadPool(ThreadProperties properties, String threadNamePrefix, Boolean daemon) {
        ThreadFactory threadFactory = createThreadFactory(threadNamePrefix, daemon);
        return new ThreadPoolExecutor(properties.getCorePoolSize(), properties.getMaximumPoolSize(),
                properties.getKeepAliveTime(), properties.getUnit(), properties.getWorkQueue(),
                threadFactory);
    }

    /**
     * 创建 ThreadFactory 。如果threadNamePrefix不为空则使用自建ThreadFactory，否则使用defaultThreadFactory
     *
     * @param threadNamePrefix 作为创建的线程名字的前缀
     * @param daemon           指定是否为 Daemon Thread(守护线程)
     * @return ThreadFactory
     */
    public static ThreadFactory createThreadFactory(String threadNamePrefix, Boolean daemon) {
        if (threadNamePrefix != null) {
            if (daemon != null) {
                return new ThreadFactoryBuilder()
                        .setNamePrefix(threadNamePrefix + "-%d")
                        .setDaemon(daemon).build();
            } else {
                return new ThreadFactoryBuilder().setNamePrefix(threadNamePrefix + "-%d").build();
            }
        }
        return Executors.defaultThreadFactory();
    }

    /**
     * 打印线程池的状态。
     *
     * @param threadPool 线程池对象。
     */
    public static void printThreadPoolStatus(ThreadPoolExecutor threadPool) {
        try (ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, createThreadFactory("print-thread-pool-status", false))) {
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                log.info("============ThreadPool Status=============");
                log.info("ThreadPool Size: [{}]", threadPool.getPoolSize());
                log.info("Active Threads: [{}]", threadPool.getActiveCount());
                log.info("Number of Tasks : [{}]", threadPool.getCompletedTaskCount());
                log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());
                log.info("===========================================");
            }, 0, 1, TimeUnit.SECONDS);
        }
    }
}
