package cn.software.bank.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class DaoUtil {
	public static <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
//		List<T> list = new ArrayList<T>();
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		// 查询
//		Statement statement = null;
////		List<QuestionBankTable> list=null;
//		try {
//			// 1.获取连接对象
//			connection = JDBCUtil.getConn();
//			// 2.根据连接对象获取statement
//			statement = connection.createStatement();
//			// 3.执行sql语句,得到resultSet
//			resultSet =   statement.executeQuery(sql);// 调用executeQuery出错

//		  try {
		// 1.得到结果集
//		   connection = JDBCTools.GetConnection();
//		   preparedStatement = connection.prepareStatement(sql);
//		   for(int i = 0; i < args.length; i++){
//		    preparedStatement.setObject(i + 1, args[i]);
//		   }
//		   resultSet = preparedStatement.executeQuery();
		// 2.处理结果集，得到 Map 的List，其中 一个 Map对象 就是一条记录
		// Map 的 key 为 resultSet 中 列的别名， Map的 value 为列的值
//		List<Map<String, Object>> values = handleResultSettoMapList(resultSet);

		// 3.把 Map的List 转为 clazz 对应的 List
		// 其中 Map 的key 即为 clazz 对应的对象的 propertyName
		// 而 Map 的 value 即为 clazz 对应的对象的 propertyValue。
//		list = transferMapListToBeanList(clazz, values);
//		  } catch (Exception e) {
//		   e.printStackTrace();
//		  }finally{
//		   JDBCTools.release(resultSet, preparedStatement, connection);
//		  }
// } catch (Exception e) {
//			   e.printStackTrace();
//			  }finally{
//					System.err.println("----------数据库操作失败----------");
//					JDBCUtil.close(connection, statement);
//					JDBCUtil.release(resultSet, preparedStatement, connection);
//			  }
//		return list;

		List<T> list = new ArrayList<T>();
		Connection connection = null;
//		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		// 查询
		Statement statement = null;
//		List<QuestionBankTable> list=null;
		try {
			// 1.获取连接对象
			connection = JDBCUtil.getConn();
			// 2.根据连接对象获取statement
			statement = connection.createStatement();
			// 3.执行sql语句,得到resultSet
			resultSet = statement.executeQuery(sql);// 调用executeQuery出错
			// Map 的 key 为 resultSet 中 列的别名， Map的 value 为列的值
			List<Map<String, Object>> values = handleResultSettoMapList(resultSet);

			// 3.把 Map的List 转为 clazz 对应的 List
			// 其中 Map 的key 即为 clazz 对应的对象的 propertyName
			// 而 Map 的 value 即为 clazz 对应的对象的 propertyValue。
			list = transferMapListToBeanList(clazz, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, statement);
		}
		return list;
	}

	/**
	 * 处理结果集，得到 Map的一个List，其中 一个 Map对象对应一条记录
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private static List<Map<String, Object>> handleResultSettoMapList(ResultSet resultSet) throws SQLException {
		// 5.准备一个 List<Map<String, Object>>:
		// 键：存放列的别名， 值：存放列的值。其中一个Map 对象对应着一条记录
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		Map<String, Object> map = null;
		// 7.处理 ResultSet， 使用 while 循环
		while (resultSet.next()) {
			map = new HashMap<String, Object>();
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				String columLabel = rsmd.getColumnLabel(i + 1);
				Object value = resultSet.getObject(i + 1);
				map.put(columLabel, value);
			}
			// 11.把一条记录的一个 Map 对象放入 5 准备的List中
			values.add(map);
		}
		return values;
	}

	private static <T> List<T> transferMapListToBeanList(Class<T> clazz, List<Map<String, Object>> values)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		// 12. 判断 List 是否为空集合，若不为空
		// 则遍历List，得到一个一个的Map对象，再把一个 Map对象转换为一个 Class参数对应的 Object对象
		List<T> result = new ArrayList<T>();
		T bean = null;
		if (values.size() > 0) {
			for (Map<String, Object> m : values) {
				bean = clazz.newInstance();
				for (Map.Entry<String, Object> entry : m.entrySet()) {
					String propertyName = entry.getKey();
					Object value = entry.getValue();

					BeanUtils.setProperty(bean, propertyName, value);
				}
				// 13. 把 Object 对象放入到 list 中
				result.add(bean);
			}
		}
		return result;
	}

	// 查询一条记录，返回对应的对象
	public <T> T get(Class<T> clazz, String sql, Object... args) {
		List<T> results = getForList(clazz, sql, args);
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;

	}

	// 返回某条记录的某一个字段的值 或一个统计的值（一共有多少条记录等）
//	public <E> E getForValue(String sql, Object... args) {
//		// 1.得到结果集：该结果集应该只有一行，且只有一列
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//
//		try {
//			// 1.得到结果集
//			connection = JDBCTools.GetConnection();
//			preparedStatement = connection.prepareStatement(sql);
//			for (int i = 0; i < args.length; i++) {
//				preparedStatement.setObject(i + 1, args[i]);
//			}
//			resultSet = preparedStatement.executeQuery();
//
//			// 2.取得结果集
//			if (resultSet.next()) {
//				return (E) resultSet.getObject(1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			JDBCTools.release(resultSet, preparedStatement, connection);
//		}
//
//		return null;
//	}
}
