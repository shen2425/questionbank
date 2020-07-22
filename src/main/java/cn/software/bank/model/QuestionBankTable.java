package cn.software.bank.model;

public class QuestionBankTable {
	/** 问题id */
	private String stemId;

	/** 题型（1：单选 2多选 3：判断 4：简答 0：其他） */
	private String questionType;

	/** 题干 */
	private String stem;

	/** 选项A */
	private String optionA;

	/** 选项B */
	private String optionB;

	/** 选项C */
	private String optionC;

	/** 选项D */
	private String optionD;

	/** 选项E */
	private String optionE;

	/** 答案 */
	private String answer;

	/** 知识点 */
	private String points;

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	/** 问题id @return stemId */
	public String getStemId() {
		return stemId;
	}

	/** 问题id @param stemId */
	public void setStemId(String stemId) {
		this.stemId = stemId == null ? null : stemId.trim();
	}

	/** 题型（1：单选 2多选 3：判断 4：简答） @return questionType */
	public String getQuestionType() {
		return questionType;
	}

	/** 题型（1：单选 2多选 3：判断 4：简答） @param questionType */
	public void setQuestionType(String questionType) {
		this.questionType = questionType == null ? null : questionType.trim();
	}

	/** 题干 @return stem */
	public String getStem() {
		return stem;
	}

	/** 题干 @param stem */
	public void setStem(String stem) {
		this.stem = stem == null ? null : stem.trim();
	}

	/** 选项A @return optionA */
	public String getOptionA() {
		return optionA;
	}

	/** 选项A @param optionA */
	public void setOptionA(String optionA) {
		this.optionA = optionA == null ? null : optionA.trim();
	}

	/** 选项B @return optionB */
	public String getOptionB() {
		return optionB;
	}

	/** 选项B @param optionB */
	public void setOptionB(String optionB) {
		this.optionB = optionB == null ? null : optionB.trim();
	}

	/** 选项C @return optionC */
	public String getOptionC() {
		return optionC;
	}

	/** 选项C @param optionC */
	public void setOptionC(String optionC) {
		this.optionC = optionC == null ? null : optionC.trim();
	}

	/** 选项D @return optionD */
	public String getOptionD() {
		return optionD;
	}

	/** 选项D @param optionD */
	public void setOptionD(String optionD) {
		this.optionD = optionD == null ? null : optionD.trim();
	}

	/** 选项E @return optionE */
	public String getOptionE() {
		return optionE;
	}

	/** 选项E @param optionE */
	public void setOptionE(String optionE) {
		this.optionE = optionE == null ? null : optionE.trim();
	}

	/** 答案 @return answer */
	public String getAnswer() {
		return answer;
	}

	/** 答案 @param answer */
	public void setAnswer(String answer) {
		this.answer = answer == null ? null : answer.trim();
	}
}