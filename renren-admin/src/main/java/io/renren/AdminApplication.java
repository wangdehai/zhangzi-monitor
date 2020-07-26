package io.renren;

import io.renren.common.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetSocketAddress;


@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = {"io.renren.modules.*.dao"})
public class AdminApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdminApplication.class);
	}
	@Async
	@Override
	public void run(String... args) throws Exception {
		/**
		 * 使用异步注解方式启动netty服务端服务
		 */
		new NettyServer().start(new InetSocketAddress(8088));

	}
}
