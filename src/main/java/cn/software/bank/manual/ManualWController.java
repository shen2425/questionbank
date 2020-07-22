package cn.software.bank.manual;

import cn.software.bank.utils.ExportWordUtil;
/**
 * 后台导出/控制台输出world
 *
 */
public class ManualWController {
	private static String url = "";

	public static void main(String[] args) throws Exception {
		url="E:/bankword.doc";
		ExportWordUtil.start(url);
	}
}
