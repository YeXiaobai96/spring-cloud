package com.thingboard.device.datasource.aspect;

import com.thingboard.device.datasource.annotation.TargetDataSource;
import com.thingboard.device.datasource.database.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面根据注解切换数据源
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected static final ThreadLocal<String> preDatasourceHolder = new ThreadLocal<>();

	@Pointcut("execution(* com.thingboard.device..mapper..*.*(..))")
	protected void datasourceAspect() {

	}

	@Before("datasourceAspect()")
	public void beforeSwitchDS(JoinPoint point) {
		String dataSource = determineDatasource(point);// DataSourceKey.ONE;

		logger.debug("---------> dataSource :" + dataSource);
		// 切换数据源
		DataSourceContextHolder.setDatasourceType(dataSource);

	}

	@After("datasourceAspect()")
	public void afterSwitchDS(JoinPoint point) {

		DataSourceContextHolder.clearDatasourceType();

	}

	/**
	 *
	 * @param jp
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String determineDatasource(JoinPoint jp) {
		String methodName = jp.getSignature().getName();
		Class targetClass = jp.getSignature().getDeclaringType();
		String dataSourceForTargetClass = resolveDataSourceFromClass(targetClass);
		String dataSourceForTargetMethod = resolveDataSourceFromMethod(targetClass, methodName);
		String resultDS = determinateDataSource(dataSourceForTargetClass, dataSourceForTargetMethod);
		return resultDS;
	}

	/**
	 *
	 * @param targetClass
	 * @param methodName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String resolveDataSourceFromMethod(Class targetClass, String methodName) {
		Method m = findUniqueMethod(targetClass, methodName);
		if (m != null) {
			TargetDataSource choDs = m.getAnnotation(TargetDataSource.class);
			return resolveDataSourceName(choDs);
		}
		return null;
	}

	/**
	 *
	 * @param classDS
	 * @param methodDS
	 * @return
	 */
	private String determinateDataSource(String classDS, String methodDS) {
		return methodDS == null ? classDS : methodDS;
	}

	/**
	 *
	 * @param targetClass
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String resolveDataSourceFromClass(Class targetClass) {
		TargetDataSource classAnnotation = (TargetDataSource) targetClass.getAnnotation(TargetDataSource.class);
		return null != classAnnotation ? resolveDataSourceName(classAnnotation) : null;
	}

	/**
	 *
	 * @param ds
	 * @return
	 */
	private String resolveDataSourceName(TargetDataSource ds) {
		return ds == null ? null : ds.value();
	}

	/**
	 *
	 * @param clazz
	 * @param name
	 * @return
	 */
	private static Method findUniqueMethod(Class<?> clazz, String name) {
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName())) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
