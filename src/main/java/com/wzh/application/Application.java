package com.wzh.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Spring Boot 应用启动类,这里继承SpringBootServletInitializer并重写SpringApplicationBuilder方法
 * 是为了打包为war进行外部tomcat的部署
 */
@SpringBootApplication // Spring Boot 应用的标识
@EnableTransactionManagement // 启动注解事务 等同于传统Spring 项目中xml配置<tx:annotation-driven />
@EnableAsync //异步调用
@ComponentScan(basePackages = { "com.wzh"}) // 指定spring管理路径，就是那些bean 注解的路径
@MapperScan({ "com.wzh.**.mapper" }) // mapper 接口类扫描包配置，两个*为目录通配符
public class Application extends SpringBootServletInitializer{

	// 程序启动入口
	public static void main(String[] args) {
		// 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
		SpringApplication.run(Application.class, args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	// 创建数据源，因为用是mybatis-spring 1.2 取消了数据源的自动注入，所以这里需要手动配置
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")// 指定数据源的前缀 ,在application.properties文件中指定
	public DataSource dataSource() {
		return new DataSource();
	}

	// 创建SqlSessionFactory
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

		//解决myBatis下 不能嵌套jar文件的问题
		VFS.addImplClass(SpringBootVFS.class);
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		// 两个*为目录通配符
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/**/*.xml"));

		//指定扫描别名包的路径，多个bean的扫描路径，拼接以分号隔开
		String typeAliasesPackage = "com.wzh.demo.domain;com.wzh.config.framework.domain";
	
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
		return sqlSessionFactoryBean.getObject();
	}

	// 创建事物管理器
	@Bean
	public PlatformTransactionManager transactionManager() {

		return new DataSourceTransactionManager(dataSource());
	}

	//自定义线程池，当配置多个executor时，被@Async("id")指定使用；也被作为线程名的前缀
	@Bean
	public AsyncTaskExecutor taskExecutor()
	{
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		//线程池名字
		executor.setThreadNamePrefix("asyncExecutor");
		//最大线程数
		executor.setMaxPoolSize(50);
		//最小线程数
		executor.setCorePoolSize(3);

		// 使用预定义的异常处理类
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());


		// 自定义拒绝策略
		/*executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				//........
			}
		});*/
		return executor;
	}

	// websocket配置
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
