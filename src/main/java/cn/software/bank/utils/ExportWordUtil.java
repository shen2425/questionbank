package cn.software.bank.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.software.bank.model.QuestionBankTable;

/**
 * 手动导出到word
 *
 */
@Service
public class ExportWordUtil {

	private static final Map<Integer, String> numMap = new HashMap<Integer, String>();

	Integer num = 1;

	static {
		numMap.put(0, "1");
		numMap.put(1, "一");
		numMap.put(2, "二");
		numMap.put(3, "三");
		numMap.put(4, "四");
	}

	@SuppressWarnings({ "resource", "unused" })
	public static void start(String url) {
		System.out.println("----- 开始导出数据 -----");
		String flag = "";
		boolean flagAn = false;
		if (StringUtils.isBlank(url)) {
			Scanner scanner = new Scanner(System.in);// 创建输入流扫描器
			tagaaa: do {
				System.err.println("请输入文件路径：");// 提示用户输入
				url = scanner.nextLine();// 获取用户输入的一行文本

				System.err.println("是否打印答案:Y\\N");// 是否打印答案
				flagAn = scanner.nextLine().equals("Y") ? true : false;

				System.err.println("请确认:Y\\N");// 提示用户输入
				flag = scanner.nextLine();// 获取用户输入的一行文本
			} while (!flag.equals("Y"));
		}
		// 打印对输入文本的描述
		List<List<Object>> readExcel = null;
		try {
			ExportWordUtil word = new ExportWordUtil();
			word.exportWord(flagAn, url);
		} catch (Exception e1) {
			System.err.println("-----文件路径错误-----");
			tagaaa: start(null);
//			e1.printStackTrace();
		}
		System.exit(0);
	}

	public void exportWord(boolean flag, String url) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("xytitle", "试卷");
		dataMap.put("xzt", "");
		dataMap.put("dxt", "");
		dataMap.put("pdt", "");
		dataMap.put("jdt", "");

		bankXZ(dataMap, flag);// 单选题
		bankDX(dataMap, flag);// 多选题
		bankPD(dataMap, flag);// 判断题
		bankJD(dataMap, flag);// 简答题
		numMap.put(0, "1");
		DocumentHandler mdoc = new DocumentHandler();
		mdoc.createDoc(dataMap, url);
	}

	public String getNum(Integer num) {
		Integer valueOf = Integer.valueOf(numMap.get(0));
		String numString = numMap.get(valueOf);
		valueOf++;
		numMap.put(0, valueOf.toString());
		return numString;
	}

	// 单选题
	public void bankXZ(Map<String, Object> dataMap, boolean flag) {
		List<QuestionBankTable> bank1 = this.selectData("1");
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 题目
		if (!bank1.isEmpty()) {
			dataMap.put("xzt", getNum(num) + "、单选题");
			int index = 1;
			for (int i = 0; i < bank1.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("xzn", index++ + ".");
				map.put("xztest", bank1.get(i).getStem());
				if (flag)
					map.put("xzans", bank1.get(i).getAnswer());
				else
					map.put("xzans", "");
				map.put("ans1", "A." + bank1.get(i).getOptionA());
				map.put("ans2", "B." + bank1.get(i).getOptionB());
				map.put("ans3", "C." + bank1.get(i).getOptionC());
				map.put("ans4", "D." + bank1.get(i).getOptionD());
				list1.add(map);
			}
		}
		dataMap.put("table1", list1);
	}

	// 多选题
	public void bankDX(Map<String, Object> dataMap, boolean flag) {
		// 多选题
		List<QuestionBankTable> bank2 = this.selectData("2");
		if (!bank2.isEmpty()) {
			dataMap.put("dxt", getNum(num) + "、多选题");
		}
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		int index = 1;
		for (int i = 0; i < bank2.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dxzn", index++ + ".");
			if (flag)
				map.put("dxans", bank2.get(i).getAnswer());
			else
				map.put("dxans", "");
			map.put("dxztest", bank2.get(i).getStem());
			map.put("dans1", "A." + bank2.get(i).getOptionA());
			map.put("dans2", "B." + bank2.get(i).getOptionB());
			map.put("dans3", "C." + bank2.get(i).getOptionC());
			map.put("dans4", "D." + bank2.get(i).getOptionD());
			list2.add(map);
		}
		dataMap.put("table2", list2);

	}

	// 判断题
	public void bankPD(Map<String, Object> dataMap, boolean flag) {
		// 判断题
		List<QuestionBankTable> bank3 = this.selectData("3");
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
//		List<Map<String, Object>> list13 = new ArrayList<Map<String, Object>>();
		if (!bank3.isEmpty()) {
			dataMap.put("pdt", getNum(num) + "、判断题");
			int index = 1;
			for (int i = 0; i < bank3.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pdn", index++ + ".");
				map.put("pdtest", bank3.get(i).getStem());
				if (flag)
					map.put("pdans", bank3.get(i).getAnswer());
				else
					map.put("pdans", "");
				list3.add(map);
			}
		}

		dataMap.put("table3", list3);

	}

	// 简答题
	public void bankJD(Map<String, Object> dataMap, boolean flag) {
		// 简答题
		List<QuestionBankTable> bank4 = this.selectData("4");
		List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();
		if (!bank4.isEmpty()) {
			dataMap.put("jdt", getNum(num) + "、简答题");
			int index = 1;
			for (int i = 0; i < bank4.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("jdn", index++ + ".");
				map.put("jdtest", bank4.get(i).getStem());
				if (flag)
					map.put("jdans", bank4.get(i).getAnswer());
				else
					map.put("jdans", "");
				list4.add(map);
			}
		}
		dataMap.put("table4", list4);
	}

	@SuppressWarnings({ "unused" })
	public List<QuestionBankTable> selectData(String type) {
		boolean falg = true;
		String sql = "select * from  question_bank_table where question_type='" + type + "'";
		// 查询
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<QuestionBankTable> list = null;
		try {
			// 1.获取连接对象
			connection = JDBCUtil.getConn();
			// 2.根据连接对象获取statement
			statement = connection.createStatement();
			// 3.执行sql语句,得到resultSet
			resultSet = statement.executeQuery(sql);// 调用executeQuery出错
			list = DaoUtil.getForList(QuestionBankTable.class, sql, null);

			// 4.遍历结果集
		} catch (Exception e) {
			falg = false;
			System.err.println("----------数据库操作失败----------");
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, statement);
		}
		return list;
	}

}
