package cn.software.bank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.software.bank.model.QuestionBankTable;

public interface QuestionBankTableMapper {
	int deleteByPrimaryKey(String stemId);

	int insert(QuestionBankTable record);

	QuestionBankTable selectByPrimaryKey(String stemId);

	List<QuestionBankTable> selectAll();

	int updateByPrimaryKey(QuestionBankTable record);

	List<QuestionBankTable> selectByType(@Param("type") String question_type);

	List<QuestionBankTable> selectByPoints(@Param("points") String points);

	List<String> selectPoints();

}