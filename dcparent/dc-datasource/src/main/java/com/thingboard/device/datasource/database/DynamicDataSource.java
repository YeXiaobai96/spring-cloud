package com.thingboard.device.datasource.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 取得当前使用那个数据源。
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 取得当前使用那个数据源。
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDatasourceType();
	}

}
