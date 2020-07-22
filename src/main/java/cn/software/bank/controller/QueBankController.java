package cn.software.bank.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.software.bank.service.ExportTxtService;
import cn.software.bank.service.ExportWordService;
import cn.software.bank.service.QueBankService;
import cn.software.bank.vo.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bank")
@Api(value = "/bank", tags = "题库功能")
public class QueBankController {

	@Autowired
	private QueBankService queBankService;
	@Autowired
	private ExportWordService exportWordService;
	@Autowired
	private ExportTxtService exportTxtService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Excel导入题库")
	@RequestMapping(value = "/saveExcel", method = RequestMethod.POST)
	public ApiResponse saveExcel(MultipartFile file) throws Exception {
//				MultipartFile file=null;
		return queBankService.saveExcel(file);
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	@ApiOperation(value = "导出word试题")
	@RequestMapping(value = "/getWord", method = RequestMethod.POST)
	public ApiResponse getWord() throws Exception {
		exportWordService.exportWord(true);
		return new ApiResponse<>().success(null);
	}

//	@ApiOperation(value = "导出Txt试题")
//	@RequestMapping(value = "/getTxt", method = RequestMethod.POST)
//	public ApiResponse getTxt() throws Exception {
//		exportTxtService.exportTxt(true);
//		return new ApiResponse<>();
//	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	@ApiOperation(value = "导出Txt试题")
	@RequestMapping(value = "/getTxt", method = RequestMethod.POST)
//    @RequestMapping(value = "/proposalPreValidate", method = RequestMethod.POST, consumes = "application/json")
	public ApiResponse getTxt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		exportTxtService.exportTxt(true, request, response);
		return new ApiResponse<>().success(null);
	}

}
