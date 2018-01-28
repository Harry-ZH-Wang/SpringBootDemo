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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
/**
 * Spring Boot 应用启动类,这里继承SpringBootServletInitializer并重写SpringApplicationBuilder方法
 * 是为了打包为war进行外部tomcat的部署
 */
@SpringBootApplication // Spring Boot 应用的标识
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
		sqlSessionFactoryBean.setMapperLocations(resolver
				.getResources("classpath:/mapper/**/*.xml"));

		//指定扫描别名包的路径，多个bean的扫描路径，拼接以分号隔开
		String typeAliasesPackage = "com.wzh.demo.domain;";
	
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
		return sqlSessionFactoryBean.getObject();
	}

	// 创建事物管理器
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
