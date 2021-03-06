package com.qfedu.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库会话
 * @author jinyu
 *
 */
public class DbSession {
	private Connection connection;
	/**
	 * 开启数据库会话
	 */
	public void open() {
		if(connection==null) {
			connection=DbResourceManager.openConnection();
		}
	}
	/**
	 * 关闭数据库会话
	 */
	public void close() {
		DbResourceManager.closeConnection(connection);
	}
	/**
	 * 开启事务
	 */
	public void beginTx() {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(DbException.TX_EX, e);
		}
	}
	/**
	 * 提交事务
	 */
	public void commitTx() {
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(DbException.TX_EX, e);
		}
	}
	/**
	 * 回滚事务
	 */
	public void rollbackTx() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(DbException.TX_EX, e);
		}
	}
	
	/**
	 * 执行增删改
	 * @param con 连接对象
	 * @param sql Sql语句
	 * @param params 替换SQl语句中占位符的参数
	 * @return 受影响的行数
	 */
	public int executeUpdate(String sql,Object...params) {		
		try(java.sql.PreparedStatement stmt=connection.prepareStatement(sql)){	
			for(int i=0;i<params.length;++i) {
				stmt.setObject(i+1, params[i]);
			}
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(DbException.EXEC_EX, e);
		}		
	}
	
	/**
	 * 执行查询
	 * @param con 连接对象
	 * @param sql Sql语句
	 * @param params 替换SQl语句中占位符的参数
	 * @return 结果集对象
	 */
	public ResultSet executeQuery(String sql,Object...params) {		
		try {
			java.sql.PreparedStatement stmt=connection.prepareStatement(sql);
			for(int i=0;i<params.length;++i) {
				stmt.setObject(i+1, params[i]);
			}return stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(DbException.EXEC_EX, e);
		}
	}
}
