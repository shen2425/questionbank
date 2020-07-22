package cn.software.bank.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 数据库连接
 *
 */
public class JDBCUtil {
	static String driverClass = null;
	static String url = null;
	static String name = null;
	static String password = null;

	static {
		try {
			// 1.创建一个属性配置对象
			Properties properties = new Properties();
			// 1.对应文件位于工程根目录
			InputStream is = new FileInputStream("src/main/resources/application.properties");

			// 2.使用类加载器,读取drc下的资源文件 对应文件位于src目录底下 建议使用
//			InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("/src/main/resources/application.properties");
			// 2.导入输入流,抓取异常
			properties.load(is);
			// 3.读取属性
			driverClass = properties.getProperty("spring.datasource.druid.driver-class-name");
			url = properties.getProperty("spring.datasource.druid.url");
			name = properties.getProperty("spring.datasource.druid.username");
			password = properties.getProperty("spring.datasource.druid.password");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 注册驱动 建立参数
	 * <p>
	 * Title: close
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param connection
	 * @param resultSet
	 * @param statement
	 */

	public static Connection getConn() {

		Connection connection = null;
		// 2. 建立连接 参数一： 协议 + 访问的数据库 ， 参数二： 用户名 ， 参数三： 密码。
		try {
			// Class.forName(driverClass);可写可不写
			// Class.forName(driverClass);
			connection = DriverManager.getConnection(url, name, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("数据库建立连接失败！");
			e.printStackTrace();
		}
		return connection;

	}

	/**
	 * 释放资源
	 * <p>
	 * Title: close
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param connection
	 * @param resultSet
	 * @param statement
	 */
	public static void close(Connection connection, Statement statement) {
		closeSt(statement);
		closeConn(connection);

	}

	private static void closeSt(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statement = null;
		}
	}

	private static void closeConn(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection = null;
		}
	}

}
