package cn.software.bank.service.impl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.software.bank.mapper.QuestionBankTableMapper;
import cn.software.bank.model.QuestionBankTable;
import cn.software.bank.service.ExportTxtService;

@Service
public class ExportTxtServiceImpl implements ExportTxtService {

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
		numMap.put(5, "1");
	}

	public void exportTxt(boolean flag, HttpServletRequest request, HttpServletResponse response) {

		StringBuffer write = new StringBuffer();
		write.append("人保新架构笔试题" + "\r\n");

//		this.method(request, response, write, flag);
		this.method2(request, response, write, flag);
		numMap.put(0, "1");
		numMap.put(5, "1");
		BufferedOutputStream buffoutputstream = null;
		// 设置编码
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/text;charset=utf-8");
		// 设置txt文件名称编码，防止中文乱码
		ServletOutputStream outputstream = null;
		response.reset();// 清空输出流
		try {
			String txt_name = "人保新架构笔试题.txt";// 导出的txt文件名
//			String txt_name = "aaa.txt";// 导出的txt文件名
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(txt_name, "UTF-8"));
			String str = new String(write.toString().getBytes(), "UTF-8");
			outputstream = response.getOutputStream();
			buffoutputstream = new BufferedOutputStream(outputstream);
			buffoutputstream.write(str.toString().getBytes("UTF-8"));
			buffoutputstream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void method2(HttpServletRequest request, HttpServletResponse response, StringBuffer write, boolean ansFlag) {
		List<String> points = questionBankTableMapper.selectPoints();
		boolean titleFlag = false;
		for (String point : points) {
			if (StringUtils.isNotBlank(point)) {
				int index = 1;
				List<QuestionBankTable> banks = questionBankTableMapper.selectByPoints(point);
				List<QuestionBankTable> bank1 = new ArrayList<>();
				List<QuestionBankTable> bank2 = new ArrayList<>();
				List<QuestionBankTable> bank3 = new ArrayList<>();
				List<QuestionBankTable> bank4 = new ArrayList<>();
				for (QuestionBankTable bank : banks) {
					if (bank.getQuestionType().equals("1")) {
						bank1.add(bank);
					} else if (bank.getQuestionType().equals("2")) {
						bank2.add(bank);
					} else if (bank.getQuestionType().equals("3")) {
						bank3.add(bank);
					} else if (bank.getQuestionType().equals("4")) {
						bank4.add(bank);
					}

				}
				write.append(point + ":\r\n");
				index = Integer.valueOf(numMap.get(5));
				bankXZ(bank1, write, ansFlag, titleFlag, index);// 单选题
				index = Integer.valueOf(numMap.get(5));
				bankDX(bank2, write, ansFlag, titleFlag, index);// 多选题
				index = Integer.valueOf(numMap.get(5));
				bankPD(bank3, write, ansFlag, titleFlag, index);// 判断题
				index = Integer.valueOf(numMap.get(5));
				bankJD(bank4, write, ansFlag, titleFlag, index);// 简答题
//				System.err.println(write.toString());
				numMap.put(5, "1");
			}
		}
	}

	public void method(HttpServletRequest request, HttpServletResponse response, StringBuffer write, boolean ansFlag) {

		boolean titleFlag = true;// 是否显示题干类型
		List<QuestionBankTable> bank1 = questionBankTableMapper.selectByType("1");
		bankXZ(bank1, write, ansFlag, titleFlag, 1);// 单选题
//		int hashCode = write.toString().hashCode();
//		System.err.println(hashCode);
		List<QuestionBankTable> bank2 = questionBankTableMapper.selectByType("2");
		bankDX(bank2, write, ansFlag, titleFlag, 1);// 多选题
		List<QuestionBankTable> bank3 = questionBankTableMapper.selectByType("3");
		bankPD(bank3, write, ansFlag, titleFlag, 1);// 判断题
		List<QuestionBankTable> bank4 = questionBankTableMapper.selectByType("4");
		bankJD(bank4, write, ansFlag, titleFlag, 1);// 简答题
	}

//	public void doPost(HttpServletRequest request, HttpServletResponse response)   {
//		this.exportTxt(true, request, response);
//	}

	public String getNum(Integer num) {
		Integer valueOf = Integer.valueOf(numMap.get(0));
		String numString = numMap.get(valueOf);
		valueOf++;
		numMap.put(0, valueOf.toString());
		return numString;
	}

	// 单选题
	public void bankXZ(List<QuestionBankTable> bank1, StringBuffer write, boolean ansFlag, boolean titleFlag,
			Integer index) {
		if (!bank1.isEmpty()) {
			if (titleFlag)
				write.append(getNum(num) + "、单选题" + "\r\n");

			for (int i = 0; i < bank1.size(); i++) {
				write.append(index++ + "、");
				if (!titleFlag)
					write.append("（单选）");
				write.append(bank1.get(i).getStem());
				if (ansFlag)
					write.append("  （" + bank1.get(i).getAnswer() + "）");
				write.append("\r\n");
				write.append("    A." + bank1.get(i).getOptionA() + "\r\n");
				write.append("    B." + bank1.get(i).getOptionB() + "\r\n");
				write.append("    C." + bank1.get(i).getOptionC() + "\r\n");
				write.append("    D." + bank1.get(i).getOptionD() + "\r\n");
				write.append("\r\n");
			}
			numMap.put(5, index.toString());
		}
	}

	// 多选题
	public void bankDX(List<QuestionBankTable> bank2, StringBuffer write, boolean ansFlag, boolean titleFlag,
			Integer index) {
		// 多选题
		if (!bank2.isEmpty()) {
			if (titleFlag)
				write.append(getNum(num) + "、多选题" + "\r\n");
			for (int i = 0; i < bank2.size(); i++) {
				write.append(index++ + "、");
				if (!titleFlag)
					write.append("（多选）");
				write.append(bank2.get(i).getStem());
				if (ansFlag)
					write.append("  （" + bank2.get(i).getAnswer() + "）");
				write.append("\r\n");
				write.append("    A." + bank2.get(i).getOptionA() + "\r\n");
				write.append("    B." + bank2.get(i).getOptionB() + "\r\n");
				write.append("    C." + bank2.get(i).getOptionC() + "\r\n");
				write.append("    D." + bank2.get(i).getOptionD() + "\r\n");
				write.append("\r\n");
			}
			numMap.put(5, index.toString());
		}
	}

	// 判断题
	public void bankPD(List<QuestionBankTable> bank3, StringBuffer write, boolean ansFlag, boolean titleFlag,
			Integer index) {
		// 判断题
		if (!bank3.isEmpty()) {
			if (titleFlag)
				write.append(getNum(num) + "、判断题" + "\r\n");
			for (int i = 0; i < bank3.size(); i++) {
				write.append(index++ + "、");
				if (!titleFlag)
					write.append("（判断）");
				write.append(bank3.get(i).getStem());
				if (ansFlag)
					write.append("    （" + bank3.get(i).getAnswer() + "）");
				write.append("\r\n");
				write.append("\r\n");
			}
			numMap.put(5, index.toString());
		}
	}

	// 简答题
	public void bankJD(List<QuestionBankTable> bank4, StringBuffer write, boolean ansFlag, boolean titleFlag,
			Integer index) {
		// 简答题
		if (!bank4.isEmpty()) {
			if (titleFlag)
				write.append(getNum(num) + "、简答题" + "\r\n");
			for (int i = 0; i < bank4.size(); i++) {

				write.append(index++ + "、");
				if (!titleFlag)
					write.append("（简答）");
				write.append(bank4.get(i).getStem() + "\r\n");
				if (ansFlag)
					write.append("    " + bank4.get(i).getAnswer() + "\r\n");
				write.append("\r\n");
			}
			numMap.put(5, index.toString());
		}
	}
}
