package cn.software.bank.manual;

import java.io.File;

import cn.software.bank.utils.ExcelUtil;

/**
 * 后台导入/控制台导入Execl
 *
 */
public class ManualEController {
	private static File file = null;

	public static void main(String[] args) throws Exception {
		file = new File("E:\\1234.xls");// E:\\123.xlsx
		ExcelUtil.start(file);
	}
}
