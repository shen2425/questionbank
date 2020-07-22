package cn.software.bank.wu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.software.bank.utils.DocumentHandler;

public class ExportWord {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {

//		questionBankTableMapper.selectByType("4");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("xytitle", "试卷");
		int index = 1;
		// 选择题
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 题目
		List<Map<String, Object>> list11 = new ArrayList<Map<String, Object>>();// 答案
		index = 1;
		for (int i = 0; i < 5; i++) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("xzn", index + ".");
			map.put("xztest", "(   )操作系统允许在一台主机上同时连接多台终端，多个用户可以通过各自的终端同时交互地使用计算机。");
			map.put("ans1", "A" + index);
			map.put("ans2", "B" + index);
			map.put("ans3", "C" + index);
			map.put("ans4", "D" + index);// xzans
			map.put("xzans", "");//
			list1.add(map);

//			Map<String, Object> map1 = new HashMap<String, Object>();
//			map1.put("fuck", index + ".");
//			map1.put("abc", "A" + index);
//			list11.add(map1);

			index++;
		}
		dataMap.put("table1", list1);
		dataMap.put("table11", list11);

		// 多选题
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list12 = new ArrayList<Map<String, Object>>();
		index = 1;
		for (int i = 0; i < 5; i++) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dxzn", index + ".");
			map.put("dxztest", "操作系统是计算机系统中的一个___系统软件_______，它管理和控制计算机系统中的___资源_________.");
//			
			map.put("dxans", "");//
			map.put("dans1", "A" + index);
			map.put("dans2", "B" + index);
			map.put("dans3", "C" + index);
			map.put("dans4", "D" + index);
			list2.add(map);

//			Map<String, Object> map1 = new HashMap<String, Object>();
//			map1.put("fill", index + ".");
//			map1.put("def", "中级调度" + index);
//			list12.add(map1);

			index++;
		}
		dataMap.put("table2", list2);
//		dataMap.put("table12", list12);

		// 判断题
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list13 = new ArrayList<Map<String, Object>>();
		index = 1;
		for (int i = 0; i < 5; i++) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pdn", index + ".");
			map.put("pdtest", "复合型防火墙防火墙是内部网与外部网的隔离点，起着监视和隔绝应用层通信流的作用，同时也常结合过滤器的功能。");
			// pdans
			map.put("pdans", "");
			list3.add(map);

//			Map<String, Object> map1 = new HashMap<String, Object>();
//			map1.put("judge", index + ".");
//			map1.put("hij", "对" + index);
//			list13.add(map1);

			index++;
		}
		dataMap.put("table3", list3);
//		dataMap.put("table13", list13);

		// 简答题
		List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list14 = new ArrayList<Map<String, Object>>();
		index = 1;
		for (int i = 0; i < 5; i++) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jdn", index + ".");
			map.put("jdtest", "说明作业调度，中级调度和进程调度的区别，并分析下述问题应由哪一级调度程序负责。");
			map.put("jdans", "a");// jdans
			list4.add(map);

//			Map<String, Object> map1 = new HashMap<String, Object>();
//			map1.put("answer", index + ".");
//			map1.put("xyz", "说明作业调度，中级调度和进程调度的区别，并分析下述问题应由哪一级调度程序负责。");
//			list14.add(map1);

			index++;
		}
		dataMap.put("table4", list4);
//		dataMap.put("table14", list14);

		DocumentHandler mdoc = new DocumentHandler();
		mdoc.createDoc(dataMap, "E:/outFile.doc");

	}
}
