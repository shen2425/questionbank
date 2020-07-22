package cn.software.bank.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExportTxtService {

public void exportTxt(boolean flag, HttpServletRequest request, HttpServletResponse response);
}
