package com.sudocn.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import play.Logger;
import play.Play;
import play.db.DB;

/**
 * 批量新增、更新操作
 * 
 * @author shunai
 */
public class BatchUpdateUtil {

	private Connection connection;

	private PreparedStatement prep;

	public BatchUpdateUtil() {
		this.connection = DB.getConnection();
	}

	public int[] executeBatch(ExecuteBatch executeBatch, String sql) {
		int[] updateNums = null;
		try {
			prep = this.connection.prepareStatement(sql);
			executeBatch.invoke(prep);
			updateNums = prep.executeBatch();
			if(Boolean.valueOf(Play.configuration.getProperty("jpa.debugSQL", "false"))) {
				Logger.info("执行批量插入[%s]", sql);
			}
		} catch (SQLException e1) {
			Logger.error("批量操作异常" + e1.getMessage());
			e1.printStackTrace();
		} finally {
			close();
		}
		return updateNums;
	}
	
	public void executeUpdate(ExecuteBatch executeBatch, String sql) {
		try {
			prep = this.connection.prepareStatement(sql);
			executeBatch.invoke(prep);
			prep.executeUpdate();
		} catch (SQLException e1) {
			Logger.error("批量操作异常" + e1.getMessage());
			e1.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * 通过对象的主键批量删除对象
	 * 
	 * @param ids
	 * @param tableName 表名
	 * @param uniqueKey 删除的键名
	 */
	public void deleteBatch(String tableName, String uniqueKey, List<String> ids, boolean needClose) {
		if (null == ids || ids.isEmpty()) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		for (int i = 0; i < ids.size(); i++) {
			sb.append("?,");
			// prep.setString(i+1, ids.get(i));
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(')');
		String deleteSql = "delete from " + tableName + " where " + uniqueKey + " in " + sb.toString();
		try {
			if(null == connection) {
				this.connection = DB.getConnection();
			}
			
			this.connection.setAutoCommit(true);
			PreparedStatement prep = this.connection.prepareStatement(deleteSql);
			for (int i = 0; i < ids.size(); i++) {
				prep.setString(i + 1, ids.get(i));
			}
			prep.execute();
			if(Boolean.valueOf(Play.configuration.getProperty("jpa.debugSQL", "false"))) {
				Logger.info("执行批量删除[%s]", deleteSql);
			}
		} catch (SQLException e) {
			Logger.error("批量删除异常" + e.getMessage());
		} finally {
			if (needClose) {
				close();
			}
		}
	}
	
	public boolean executeUpdate(String sql, String[] args) {
		PreparedStatement prep = null;
		try{
			prep = this.connection.prepareStatement(sql);
			for(int i=0; i<args.length; i++) {
				prep.setString(i+1, args[i]);
			}
			prep.execute();
			return true;
			
		}catch(Exception e){
			Logger.error(e,"不能执行[%s]", sql);
			return false;
		}finally{
			JDBCUtil.close(prep);
			close();
		}
	}
	
	/**
	 * 请使用executeUpdate
	 * 
	 * @param sql
	 * @param args
	 * @throws SQLException
	 */
	@Deprecated
	public void executeDelete(String sql, String[] args) throws SQLException {
		PreparedStatement prep = this.connection.prepareStatement(sql);
		for(int i=0; i<args.length; i++) {
			prep.setString(i+1, args[i]);
		}
		if(!prep.execute()) {
			Logger.error("不能执行[%s]", sql);
		}
	}

	public void close() {
		JDBCUtil.close(prep, connection);
		prep = null;
		connection = null;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public interface ExecuteBatch {
		public void invoke(PreparedStatement prep) throws SQLException;
	}

	public interface DeleteBatch {
		public void invoke(PreparedStatement prep, String tableName, String uniqueKey, List<String> ids)
				throws SQLException;
	}
}
