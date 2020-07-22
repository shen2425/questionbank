package cn.software.bank.constant;

import lombok.Getter;

/**
 * 定义响应头的code和message的枚举类
 */
@Getter
public enum Status {

	SUCCESS("0", "成功"),

	EMPTY_DATA("-1001", "未查到数据，请检查填写是否有误"),

	INVALID_PARAM("-1002", "参数检验失败"),

	BIZ_EXCEPTION("-1003", "业务异常"),

	ERROR("-9999", "接口运行异常");

	/**
	 * 响应代码
	 */
	private String code;

	/**
	 * 响应信息
	 */
	private String message;

	Status(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
