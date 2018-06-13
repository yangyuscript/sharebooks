package com.lin.sharebooks;

import com.github.pagehelper.PageHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
@EnableCaching
//表明开启servlet的注解
@ServletComponentScan
public class SharebooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharebooksApplication.class, args);
	}
	//配置mybatis的分页插件pageHelper
	@Bean
	public PageHelper pageHelper(){
		PageHelper pageHelper=new PageHelper();
		Properties properties=new Properties();
		properties.setProperty("offsetAsPageNum","true");
		properties.setProperty("rowBoundsWithCount","true");
		//设置为true时表示参数合理化，例如currpage<1,自动返回第一页数据，currpage>总页数时，自动返回最后一页数据
		properties.setProperty("reasonable","false");
		//配置mysql数据库的方言
		properties.setProperty("dialect","mysql");
		pageHelper.setProperties(properties);
		return pageHelper;
	}
}
