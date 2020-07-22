package cn.software.bank.service;

import org.springframework.web.multipart.MultipartFile;

import cn.software.bank.vo.ApiResponse;

public interface QueBankService {
	@SuppressWarnings("rawtypes")
	public ApiResponse saveExcel(MultipartFile file);
}
