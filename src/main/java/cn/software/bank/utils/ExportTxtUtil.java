package cn.software.bank.utils;
//package cn.software.bank.utils;
//
//import java.io.PrintStream;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Service;
//
//import cn.software.bank.model.QuestionBankTable;
//
///**
// * 手动导出到txt
// *
// */
//@Service
//public class ExportTxtUtil {
//
//	private static final Map<Integer, String> numMap = new HashMap<Integer, String>();
//
//	Integer num = 1;
//
//	static {
//		numMap.put(0, "1");
//		numMap.put(1, "一");
//		numMap.put(2, "二");
//		numMap.put(3, "三");
//		numMap.put(4, "四");
//	}
//
//	public void exportTxt(boolean flag) throws Exception {
//		PrintStream ps = new PrintStream("E:/a.txt"); // 创建一个打印输出流，输出的目标是：C盘的a文件
//		PrintStream out = System.out; // 先把系统默认的打印输出流缓存
//		System.out.println("试卷");
//		bankXZ(flag);// 单选题
//		bankDX(flag);// 多选题
//		bankPD(flag);// 判断题
//		bankJD(flag);// 简答题
//		System.setOut(ps);// 把创建的打印输出流赋给系统。即系统下次向 ps输出
//		numMap.put(0, "1");
//		System.setOut(out);// 恢复原有输出流
//	}
//
//	public String getNum(Integer num) {
//		Integer valueOf = Integer.valueOf(numMap.get(0));
//		String numString = numMap.get(valueOf);
//		valueOf++;
//		numMap.put(0, valueOf.toString());
//		return numString;
//	}
//
//	// 单选题
//	public void bankXZ(boolean flag) {
//		List<QuestionBankTable> bank1 = this.selectData("1");
//		if (!bank1.isEmpty()) {
//			System.out.println(getNum(num) + "、单选题");
//			int index = 1;
//			for (int i = 0; i < bank1.size(); i++) {
//				String text = "";
//				text = text + index++ + ".";
//				text = text + bank1.get(i).getStem();
//				if (flag)
//					text = text + "  （" + bank1.get(i).getAnswer() + "）";
//				System.out.println(text);
//				System.out.println("  A." + bank1.get(i).getOptionA());
//				System.out.println("  B." + bank1.get(i).getOptionB());
//				System.out.println("  C." + bank1.get(i).getOptionC());
//				System.out.println("  D." + bank1.get(i).getOptionD());
//			}
//		}
//		System.out.println("");
//	}
//
//	// 多选题
//	public void bankDX(boolean flag) {
//		// 多选题
//		List<QuestionBankTable> bank2 = this.selectData("2");
//		if (!bank2.isEmpty()) {
//			System.out.println(getNum(num) + "、多选题");
//
//			int index = 1;
//			for (int i = 0; i < bank2.size(); i++) {
//				String text = "";
//				text = text + index++ + ".";
//				if (flag)
//					text = text + "  （" + bank2.get(i).getAnswer() + "）";
//				System.out.println(text);
//				System.out.println("  A." + bank2.get(i).getOptionA());
//				System.out.println("  B." + bank2.get(i).getOptionB());
//				System.out.println("  C." + bank2.get(i).getOptionC());
//				System.out.println("  D." + bank2.get(i).getOptionD());
//			}
//		}
//		System.out.println("");
//	}
//
//	// 判断题
//	public void bankPD(boolean flag) {
//		// 判断题
//		List<QuestionBankTable> bank3 = this.selectData("3");
//		if (!bank3.isEmpty()) {
//			System.out.println(getNum(num) + "、判断题");
//			int index = 1;
//			for (int i = 0; i < bank3.size(); i++) {
//				String text = "";
//				text = text + index++ + ".";
//				text = text + bank3.get(i).getStem();
//				if (flag)
//					text = text + "  （" + bank3.get(i).getAnswer() + "）";
//				System.out.println(text);
//			}
//		}
//	}
//
//	// 简答题
//	public void bankJD(boolean flag) {
//		// 简答题
//		List<QuestionBankTable> bank4 = this.selectData("4");
//		if (!bank4.isEmpty()) {
//			System.out.println(getNum(num) + "、简答题");
//			int index = 1;
//			for (int i = 0; i < bank4.size(); i++) {
//				String text = "";
//				text = text + index++ + ".";
//				text = text + bank4.get(i).getStem();
//				System.out.println(text);
//				if (flag)
//					System.out.println("  " + bank4.get(i).getAnswer());
//			}
//		}
//		System.out.println("");
//	}
//
//	public List<QuestionBankTable> selectData(String type) {
//		boolean falg = true;
//		String sql = "select * from  question_bank_table where question_type='" + type + "'";
//		// 查询
//		Connection connection = null;
//		Statement statement = null;
//		ResultSet resultSet = null;
//		List<QuestionBankTable> list = null;
//		try {
//			// 1.获取连接对象
//			connection = JDBCUtil.getConn();
//			// 2.根据连接对象获取statement
//			statement = connection.createStatement();
//			// 3.执行sql语句,得到resultSet
//			resultSet = statement.executeQuery(sql);// 调用executeQuery出错
//			list = DaoUtil.getForList(QuestionBankTable.class, sql, null);
//
//			// 4.遍历结果集
//		} catch (Exception e) {
//			falg = false;
//			System.err.println("----------数据库操作失败----------");
//			e.printStackTrace();
//		} finally {
//			JDBCUtil.close(connection, statement);
//		}
//		return list;
//	}
//
//}
