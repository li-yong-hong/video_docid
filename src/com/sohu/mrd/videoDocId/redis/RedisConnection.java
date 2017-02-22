package com.sohu.mrd.videoDocId.redis;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPoolConfig;
import com.sohu.mrd.framework.redis.client.client.CodisLocalClient;
/**
 * @author Jin Guopan
   @creation 2016年12月22日
 */
public class RedisConnection {
	private static final Logger LOG = Logger.getLogger(RedisConnection.class);
    static CodisLocalClient codisLocalClient = null;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinEvictableIdleTimeMillis(60000);
        config.setTimeBetweenEvictionRunsMillis(30000);
        codisLocalClient = CodisLocalClient
                .create()
                .build();         // 以默认方式创建测试环境下的codis客户端
    }
}
