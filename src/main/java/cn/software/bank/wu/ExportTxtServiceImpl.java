package cn.software.bank.wu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.software.bank.mapper.QuestionBankTableMapper;
import cn.software.bank.model.QuestionBankTable;
import cn.software.bank.utils.DocumentHandler;

public class ExportTxtServiceImpl {

	private static final Map<Integer, String> numMap = new HashMap<Integer, String>();

	Integer num = 1;
	@Autowired
	private QuestionBankTableMapper questionBankTableMapper;
	static {
		numMap.put(0, "1");
		numMap.put(1, "一");
		numMap.put(2, "二");
		numMap.put(3, "三");
		numMap.put(4, "四");
	}

	public void exportWord(boolean flag) throws Exception {
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
		mdoc.createDoc(dataMap, "E:/bankword.doc");
//		mdoc.createDoc(dataMap, "/bankword.doc");

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
		List<QuestionBankTable> bank1 = questionBankTableMapper.selectByType("1");
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
		List<QuestionBankTable> bank2 = questionBankTableMapper.selectByType("2");
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
		List<QuestionBankTable> bank3 = questionBankTableMapper.selectByType("3");
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
		List<QuestionBankTable> bank4 = questionBankTableMapper.selectByType("4");
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
}
