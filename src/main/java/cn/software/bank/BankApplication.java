package cn.software.bank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("cn.software.bank.mapper")
//@EnableDiscoveryClient// 注解表明此应用需开启服务注册与发现功能。
@EnableCaching
//@EnableFeignClients// 使用Feign微服务调用时请启用
@EnableTransactionManagement // 开启事务
@EnableSwagger2
@ComponentScan(basePackages = "cn.software.bank")

public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);

	}
}
