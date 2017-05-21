package main.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	/**
	 * 数据库配置文件地址
	 */
	private static final String DB_PROPFILE_PATH = "DBinfo.properties";
	/**
	 * 缓存的 Properties 对象，避免每次取值 都读取文件
	 */
	private static Properties DBProp = null;
	
	/**
	 * 获取数据库配置属性对象
	 * @return 数据库配置属性对象，当发生异常时返回 null
	 */
	private static Properties getDBProp() {
		if (DBProp != null) {
			return DBProp;
		}
		
		DBProp = MyUtil.getPropertyByPath(DBUtil.class.getResource(DB_PROPFILE_PATH).getFile());
		
		return DBProp;
	}
	
	/**
	 * 获取数据库连接
	 * @return 数据库连接对象，当发生异常时返回 null
	 */
	public static Connection getConnect() {
		Properties prop = getDBProp();
		
		if (prop == null) {
			return null;
		}
		
		String hostname = prop.getProperty("hostname");
		String port = prop.getProperty("port");
		String dbName = prop.getProperty("dbName");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?useSSL=true&characterEncoding=utf8";
		
		try {
			// 加载数据库驱动（ 成功加载后，会将Driver类的实例注册到DriverManager类中） 
			Class.forName("com.mysql.jdbc.Driver");
			
			// 获取数据库连接
			return DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
	}
}
