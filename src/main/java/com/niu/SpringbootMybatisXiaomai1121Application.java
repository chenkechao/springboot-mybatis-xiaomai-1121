package com.niu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.niu.mapper")//mapper接口的路径
public class SpringbootMybatisXiaomai1121Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisXiaomai1121Application.class, args);
	}
}
