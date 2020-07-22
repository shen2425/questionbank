
CREATE TABLE `question_bank_table` (
   `stem_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题id',
   `question_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '题型（1：单选 2多选 3：判断 4：简答',
   `stem` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '题干',
   `answer` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '答案',
   `option_a` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '选项A',
   `option_b` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '选项B',
   `option_c` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '选项C',
   `option_d` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '选项D',
   `option_e` varchar(100) DEFAULT NULL COMMENT '选项E',
   `points` varchar(50) DEFAULT NULL COMMENT '知识点',
   PRIMARY KEY (`stem_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8