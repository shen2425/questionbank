package cn.software.bank.service.impl;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.software.bank.mapper.QuestionBankTableMapper;
import cn.software.bank.model.QuestionBankTable;
import cn.software.bank.service.QueBankService;
import cn.software.bank.vo.ApiResponse;

@Service
public class QueBankServiceImpl implements QueBankService {

	private final static String excel2003L = ".xls"; // 2003- 版本的excel
	private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel
	@Autowired
	private QuestionBankTableMapper questionBankTableMapper;

	public static List<List<Object>> readExcel(MultipartFile file) throws Exception {

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

	public static Workbook getWorkbook(MultipartFile file) throws Exception {

		InputStream inStr = file.getInputStream();
		String fileName = file.getOriginalFilename();
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (excel2003L.equals(fileType)) {
			wb = new HSSFWorkbook(inStr); // 2003-
		} else if (excel2007U.equals(fileType)) {
			wb = new XSSFWorkbook(inStr); // 2007+
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		inStr.close();
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
//				System.out.println("chucuo ele ");
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int saveData(List<Object> list) {
		// 题型* 题干* 答案* 选项A 选项B 选项C 选项D 选项E 选项F 知识点 试题难度 试题区分度
		QuestionBankTable bank = new QuestionBankTable();
		while (list.size() < 8) {
			list.add("");
		}
		String type = String.valueOf(list.get(0));
		if (type.contains("单")) {
			bank.setQuestionType("1");
		} else if (type.contains("多")) {
			bank.setQuestionType("2");
		} else if (type.contains("判断")) {
			bank.setQuestionType("3");
		} else if (type.contains("简答")) {
			bank.setQuestionType("4");

		} else
			bank.setQuestionType("0");
//		bank.setQuestionType(type);
		bank.setStem(String.valueOf(list.get(1)));
		bank.setAnswer(String.valueOf(list.get(2)));
		bank.setPoints(String.valueOf(list.get(3)));
		bank.setOptionA(String.valueOf(list.get(4)));
		bank.setOptionB(String.valueOf(list.get(5)));
		bank.setOptionC(String.valueOf(list.get(6)));
		bank.setOptionD(String.valueOf(list.get(7)));
//		bank.setOptionE(String.valueOf(list.get(8)));
		String uuID = UUID.randomUUID().toString().replace("-", "");
		bank.setStemId(uuID);
		return questionBankTableMapper.insert(bank);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ApiResponse saveExcel(MultipartFile file) {
		System.out.println("----- 开始导入数据 -----");
		// 打印对输入文本的描述
		List<List<Object>> readExcel = null;
		String massgae = "导入成功";
		String errorCode = "1";
		try {
			readExcel = readExcel(file);
		} catch (Exception e2) {
			errorCode = "0";
			massgae = "文件路径错误";
		}
		try {
			for (int i = 0; i < readExcel.size(); i++) {
				List<Object> list = readExcel.get(i);
//				System.out.println(i+2);
				if (list.isEmpty()) {
					continue;
				}
				try {
					if (this.saveData(list) != 1) {
						massgae = massgae + "第" + (i + 2) + "行数据保存失败";
					}
				} catch (Exception e) {
					massgae = massgae + "第" + (i + 2) + "行数据保存失败，其余成功：" + list.get(1);
					errorCode = "0";
					e.printStackTrace();
					throw e;
//					continue;
				}
			}
		} catch (Exception e1) {
			errorCode = "0";
			massgae = "数据库操作失败";
		}
		return new ApiResponse(errorCode, massgae, null);
	}
}
