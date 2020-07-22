package cn.software.bank.vo;

import java.io.Serializable;

import cn.software.bank.constant.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "响应信息代码", example = "06250100")
	private String errorCode;

	@ApiModelProperty(value = "响应提示信息", example = "成功")
	private String errorMessage;
	/**
	 * 返回数据
	 */
	@ApiModelProperty(value = "响应的结果")
	private T resultInfo;

	public ApiResponse() {

	}

	/**
	 * 成功的构造
	 * 
	 * @param resultInfo
	 */
	public ApiResponse(T resultInfo) {
		this.errorCode = Status.SUCCESS.getCode();
		this.errorMessage = Status.SUCCESS.getMessage();
		this.resultInfo = resultInfo;
	}

	/**
	 * 根据 code,msg,info构造
	 * 
	 * @param errorCode
	 * @param errorMsg
	 * @param resultInfo
	 */
	public ApiResponse(String errorCode, String errorMsg, T resultInfo) {
		this.errorCode = errorCode;
		this.errorMessage = errorMsg;
		this.resultInfo = resultInfo;
	}

	/**
	 * 返回成功的数据信息
	 * 
	 * @param resultInfo
	 * @return
	 */
	public static <T> ApiResponse<T> success(T resultInfo) {
		return new ApiResponse<T>(resultInfo);
	}

}