/**
 * 
 */
package com.netfinworks.restx.persist.jdbc;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * Jdbc 数据源工厂bean，做到根据属性配置，生成特定的数据源（dbcp，jndi）
 * 
 * @author bigknife
 * 
 */
public class JdbcDataSourceFactoryBean implements FactoryBean<DataSource> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final String DSTYPE_DBCP = "dbcp";
	public static final String DSTYPE_JNDI = "jndi";
	private String dsType = DSTYPE_DBCP;

	private DataSource dbcpDs;
	private String jndiName;
	
	private Map<String, String> dbcpConfig;
	
	public void setDbcpConfig(Map<String, String> dbcpConfig) {
        this.dbcpConfig = dbcpConfig;
    }

    /**
	 * 返回数据源
	 * 
	 * @return
	 */
	public DataSource factory() {
		if (DSTYPE_DBCP.equalsIgnoreCase(dsType)) {
		    if(dbcpConfig != null){
		        BasicDataSource ds = new BasicDataSource();
		        String className = (String) dbcpConfig.get("driverClassName");
		        ds.setDriverClassName(className);
		        
		        
		        String url =dbcpConfig.get("url");
		        ds.setUrl(url);
		        
		        String username =  dbcpConfig.get("username");
		        ds.setUsername(username);
		        
		        String password =  dbcpConfig.get("password");
		        ds.setPassword(password);
		        
		        try{
		            int maxIdle =Integer.parseInt(dbcpConfig.get("maxIdle"));
		            ds.setMaxIdle(maxIdle);
		        }finally{}
                try{
                    int maxActive =Integer.parseInt(dbcpConfig.get("maxActive"));
                    ds.setMaxActive(maxActive);
                }finally{}
                
                try{
                    long timeBetweenEvictionRunsMillis =Long.parseLong(dbcpConfig.get("timeBetweenEvictionRunsMillis"));
                    ds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
                }finally{}
                
                try{
                    long minEvictableIdleTimeMillis =Long.parseLong(dbcpConfig.get("minEvictableIdleTimeMillis"));
                    ds.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
                }finally{}
                
                return ds;
		    }else{
		        return dbcpDs;
		    }
		}
		if (DSTYPE_JNDI.equalsIgnoreCase(dsType)) {
			JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
			factory.setJndiName(jndiName);
			try {
				factory.afterPropertiesSet();
			} catch (Exception e) {
				logger.error("look jndi datasource failed!", e);
				throw new RuntimeException(e);
			}
			return (DataSource) factory.getObject();
		}
		logger.error("dsType error! no datasource returned! dsType:{}",dsType);
		return null;
	}

	/**
	 * @param dsType the dsType to set
	 */
	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	/**
	 * @param jndiName the jndiName to set
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	/**
	 * @param dbcpDs the dbcpDs to set
	 */
	public void setDbcpDs(DataSource dbcpDs) {
		this.dbcpDs = dbcpDs;
	}

	@Override
	public DataSource getObject() throws Exception {
		return factory();
	}

	@Override
	public Class<?> getObjectType() {
		return DataSource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
