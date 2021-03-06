package com.qfedu.util;

import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * 数据库资源管理器类
 * @author Administrator
 *
 */
public final class DbResourceManager {
	
		private static final String DRV = "com.mysql.jdbc.Driver";
		private static final String URL = "jdbc:mysql:///hrs?useUnicode=true&characterEncoding=utf8";
		private static final String UID = "root";
		private static final String PWD = "123456";
		
		private static BasicDataSource dataSource = null;
//		// JNDI - Java Naming and Directory Interface
//		static {
//			try {
//				Context context = new InitialContext();
//				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/foo");
//			} catch (NamingException e) {
//				e.printStackTrace();
//			}
//		}
		
		static {
			dataSource = new BasicDataSource();
			dataSource.setInitialSize(20);
			dataSource.setMaxTotal(140);
			dataSource.setMaxWaitMillis(5000);
			dataSource.setDriverClassName(DRV);
			dataSource.setUrl(URL);
			dataSource.setUsername(UID);
			dataSource.setPassword(PWD);
		}
		
		private DbResourceManager() {
			throw new AssertionError();
		}
	
	/**
	 * 打开数据库连接
	 * @return  Connection
	 */
	public static Connection openConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(DbException.CONN_EX, e);
		}
	}
	
	/**
	 * 关闭数据库连接
	 * @return
	 */
	public static void closeConnection(Connection con) {
		try {
			if(con!=null&&!con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DbException(DbException.DIS_EX, e);
		}
	}
	
}
