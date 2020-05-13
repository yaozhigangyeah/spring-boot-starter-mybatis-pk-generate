package com.pk.generater;

import com.pk.generater.algorithm.SnowFlakeIdWorker;
import com.pk.generater.factory.PkGenerateFactory;
import com.pk.generater.generate.impl.LongSnowFlakeGenerate;
import com.pk.generater.generate.impl.StringSnowFlakeGenerate;
import com.pk.generater.generate.impl.StringUUIDGenerate;
import com.pk.generater.interceptor.GeneratePkInterceptor;
import com.pk.generater.properties.PkProperties;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * 自定义主键赋值插件
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(PkProperties.class)
@AutoConfigureAfter({MybatisAutoConfiguration.class})
@ConditionalOnProperty(prefix = "pk.auto.generate", name = "enabled", havingValue = "true")
public class GeneratePkAutoConfiguration {
    private static final Log log = LogFactory.getLog(GeneratePkAutoConfiguration.class);

    private static final String ID_TYPE = "idType";

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Autowired
    private PkGenerateFactory pkGenerateFactory;

    private final PkProperties pkProperties;

    public GeneratePkAutoConfiguration(PkProperties pkProperties) {
        this.pkProperties = pkProperties;
    }

    @PostConstruct
    public void addPageInterceptor() {
        log.debug("Init keyPlugin");
        log.debug(pkProperties.toString());
        GeneratePkInterceptor generateKeyPlugin = new GeneratePkInterceptor(pkGenerateFactory);
        Properties properties = new Properties();
        // TODO 全局主键（id）策略
        properties.put(ID_TYPE, pkProperties.getPkType());
        generateKeyPlugin.setProperties(properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(generateKeyPlugin);
        }
    }

    @Bean
    public SnowFlakeIdWorker snowflakeIdWorker() {
        return new SnowFlakeIdWorker(pkProperties.getWorkerId(), pkProperties.getDatacenterId());
    }

    @Bean
    public PkGenerateFactory keyGeneratorFactory() {
        return new PkGenerateFactory();
    }

    @Bean
    public LongSnowFlakeGenerate longSnowKeyGenerator() {
        return new LongSnowFlakeGenerate();
    }

    @Bean
    public StringSnowFlakeGenerate stringSnowKeyGenerator() {
        return new StringSnowFlakeGenerate();
    }

    @Bean
    public StringUUIDGenerate stringUUIDKeyGenerator() {
        return new StringUUIDGenerate();
    }
}