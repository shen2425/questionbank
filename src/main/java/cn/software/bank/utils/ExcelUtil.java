package cn.software.bank.utils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 手动入库
 */
public class ExcelUtil {
	private final static String excel2003L = ".xls"; // 2003- 版本的excel
	private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

	public static List<List<Object>> readExcel(File file) throws Exception {
		List<List<Object>> list = null;
		// 创建Excel工作薄
		Workbook work = getWorkbook(file);
		if (null == work) {
			throw new Exception("创建Excel工作薄为空！");
		}
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		list = new ArrayList<List<Object>>();
		int numberOfSheets = work.getNumberOfSheets();
		numberOfSheets = 1;// 只是导入第一个工作薄
		for (int i = 0; i < numberOfSheets; i++) {
			sheet = work.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			for (int j = sheet.getFirstRowNum() + 1; j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				List<Object> li = new ArrayList<Object>();
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					cell = row.getCell(y);
					li.add(getCellValue(cell));
				}
				list.add(li);
			}
		}
		return list;
	}

	public static Workbook getWorkbook(File file) throws Exception {
		String filePath = file.getAbsolutePath();// 绝对路径
		FileInputStream in = new FileInputStream(filePath);
		Workbook wb = null;
		String fileType = file.getName().substring(file.getName().lastIndexOf("."));
		if (excel2003L.equals(fileType)) {
			wb = (Workbook) new HSSFWorkbook(in); // 2003-
		} else if (excel2007U.equals(fileType)) {
			wb = new XSSFWorkbook(in); // 2007+
		} else {
			System.err.println("解析的文件格式有误！");
		}
		in.close();
		return wb;
	}

	public static Object getCellValue(Cell cell) {
		Object value = null;
		DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
		DecimalFormat df2 = new DecimalFormat("0"); // 格式化数字
		if (cell == null) {
			return value = "";
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if ("General".equals(cell.getCellStyle().getDataFormatString())) {
				value = df.format(cell.getNumericCellValue());
			} else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
				value = sdf.format(cell.getDateCellValue());
			} else {
				value = df2.format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_FORMULA: // 返回经公式计算后的

			try {
				value = String.valueOf(cell.getRichStringCellValue());

			} catch (IllegalStateException e) {
				value = String.valueOf(cell.getNumericCellValue());
				System.out.println("chucuo ele ");
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			value = "";
			break;
		default:
			break;
		}
		return value;
	}

	@SuppressWarnings({ "resource", "unused" })
	public static void start(File file) {
		System.out.println("----- 开始导入数据 -----");
		String flag = "";
		if (file == null) {
			Scanner scanner = new Scanner(System.in);// 创建输入流扫描器
			tagaaa: do {
				System.err.println("请输入文件路径：");// 提示用户输入
				String line = scanner.nextLine();// 获取用户输入的一行文本
				file = new File(line);// "E:\\123.xlsx"
				System.err.println("请确认:Y\\N");// 提示用户输入
				flag = scanner.nextLine();// 获取用户输入的一行文本
			} while (!flag.equals("Y"));
		}

		// 打印对输入文本的描述
		List<List<Object>> readExcel = null;
		try {
			readExcel = readExcel(file);
		} catch (Exception e1) {
			System.err.println("-----文件路径错误-----");
			tagaaa: start(null);
//			e1.printStackTrace();
		}
		String massgae = "----- 导入成功 -----";
		for (int i = 0; i < readExcel.size(); i++) {
			List<Object> list = readExcel.get(i);
//			System.out.println(i+2);
//			boolean contains = list.toString().contains("/>");
//			System.err.println(list.toString());
//			System.err.println(contains);
//			if(list.isEmpty() ||contains) {
			if (list.isEmpty()) {
				continue;
			}
			try {
				if (saveData(list)) {
				} else
					massgae = "第" + (i + 2) + "行数据保存失败";
			} catch (Exception e) {
				System.err.println("操作失败");
				System.err.println(list.get(1));
				System.err.println(list.get(2));
				System.err.println(list.get(3));
				System.err.println(list.get(4));
				System.err.println(list.get(5));
				System.err.println(list.get(6));

			}
		}
		System.out.println(massgae);
		System.exit(0);

	}

	/**
	 * 保存数据到数据库
	 * 
	 * @param list
	 * @return
	 */
	public static boolean saveData(List<Object> list) {
		while (list.size() < 8) {
			list.add("");
		}
		String type = String.valueOf(list.get(0));
		String value = "0";
		if (type.contains("单")) {
			value = "1";
		} else if (type.contains("多")) {
			value = "2";
		} else if (type.contains("判断")) {
			value = "3";
		} else if (type.contains("简答")) {
			value = "4";
		}
//		String point = String.valueOf(list.get(3));
//		String pointValue = "0";
//		if (type.contains("单")) {
//			pointValue = "1";
//		} else if (type.contains("多")) {
//			pointValue = "2";
//		} else if (type.contains("判断")) {
//			pointValue = "3";
//		} else if (type.contains("简答")) {
//			pointValue = "4";
//		}
		
		
		boolean falg = true;
		String sql = "insert into question_bank_table (stem_id, question_type, stem, answer, points, option_a, option_b, option_c,option_d, option_e) values ('"
				+ UUID.randomUUID().toString().replace("-", "") + "','" + value + "','" + String.valueOf(list.get(1))
				+ "','" + String.valueOf(list.get(2)) + "','" + String.valueOf(list.get(3)) + "','"
				+ String.valueOf(list.get(4)) + "','" + String.valueOf(list.get(5)) + "','"
				+ String.valueOf(list.get(6)) + "','" 
				+ String.valueOf(list.get(7))  + "')";

		// 查询
		Connection connection = null;
		Statement statement = null;
		int resultSet = 0;
		try {
			// 1.获取连接对象
			connection = JDBCUtil.getConn();
			// 2.根据连接对象获取statement
			statement = connection.createStatement();
			// 3.执行sql语句,得到resultSet
			resultSet = statement.executeUpdate(sql);// 调用executeQuery出错
			if (resultSet == 0) {
				falg = false;
				JDBCUtil.close(connection, statement);
			}
			// 4.遍历结果集
		} catch (Exception e) {
			falg = false;
			System.err.println("----------数据库操作失败----------");
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, statement);
		}
		return falg;
	}

}