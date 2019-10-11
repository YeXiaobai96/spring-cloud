package com.thingboard.device.datasource.config;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.github.pagehelper.PageInterceptor;
import com.thingboard.device.datasource.database.DynamicDataSource;
import com.thingboard.device.datasource.template.CustomSqlSessionTemplate;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 *数据源配置
 */
@Configuration
@Order(0)
@MapperScan(basePackages = "com.thingboard.device.dao.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfig extends AbstractDataSourceConfig {

	@Bean("wall-filter")
	public WallFilter wallFilter() {
		WallFilter wallFilter = new WallFilter();
		wallFilter.setConfig(wallConfig());
		return wallFilter;
	}

	@Bean
	public WallConfig wallConfig() {
		WallConfig config = new WallConfig();
		config.setMultiStatementAllow(true);// 允许一次执行多条语句
		config.setNoneBaseStatementAllow(true);// 允许非基本语句的其他语句
		return config;
	}

	@Primary
	@Bean(name = "dataSourceOne")
	public DataSource dataSourceOne(Environment env) {
		String prefix = "spring.datasource.druid.one.";
		return getDataSource(env, prefix, "one");
	}


	@Bean(name = "dataSourceTwo")
	public DataSource dataSourceTwo(Environment env) {
		String prefix = "spring.datasource.druid.two.";
		DataSource ds = getDataSource(env, prefix, "two");

		return ds;
	}
	 

	@Bean("dynamicDataSource")
	public DynamicDataSource dynamicDataSource(@Qualifier("dataSourceOne") DataSource dataSourceOne,
											   @Qualifier("dataSourceTwo") DataSource dataSourceTwo) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("one", dataSourceOne);
		targetDataSources.put("two", dataSourceTwo);

		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(dataSourceOne);
		return dataSource;
	}

	@Primary
	@Bean(name = "sqlSessionFactoryOne")
	public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("dataSourceOne") DataSource dataSource) throws Exception {
		return createSqlSessionFactory(dataSource);
	}

	@Bean(name = "sqlSessionFactoryTwo")
	public SqlSessionFactory sqlSessionFactoryTwo(@Qualifier("dataSourceTwo") DataSource dataSource) throws Exception {
		return createSqlSessionFactory(dataSource);
	}
	 
	@Bean(name = "sqlSessionTemplate")
	public CustomSqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryOne") SqlSessionFactory factoryOne,
													   @Qualifier("sqlSessionFactoryTwo") SqlSessionFactory factoryTwo) throws Exception {
		Map<Object, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();
		sqlSessionFactoryMap.put("one", factoryOne);
		sqlSessionFactoryMap.put("two", factoryTwo);

		CustomSqlSessionTemplate customSqlSessionTemplate = new CustomSqlSessionTemplate(factoryOne);
		customSqlSessionTemplate.setTargetSqlSessionFactorys(sqlSessionFactoryMap);
		return customSqlSessionTemplate;
	}

	/**
	 * 创建数据源
	 * 
	 * @param dataSource
	 * @return
	 */
	private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setVfs(SpringBootVFS.class);
		bean.setTypeAliasesPackage("com.thingboard.device.model.entity");
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
		//分页插件
		Interceptor interceptor = new PageInterceptor();
		Properties properties = new Properties();
		//数据库
		properties.setProperty("helperDialect", "postgresql");
		//是否将参数offset作为PageNum使用
		properties.setProperty("offsetAsPageNum", "true");
		//是否进行count查询
		properties.setProperty("rowBoundsWithCount", "true");
		//是否分页合理化
		properties.setProperty("reasonable", "true");
		interceptor.setProperties(properties);
		bean.setPlugins(new Interceptor[] {interceptor});
		return bean.getObject();
	}

}
