mybatis生成代码配置mybatis-generator-config.xml：
1、修改<classPathEntry
            location="E:\WorkSpace\Repository\org\postgresql\postgresql\42.2.5\postgresql-42.2.5.jar"/>
为本地jar包文件位置

2、修改或增加<table tableName="t_c_quotiety" schema='public' enableCountByExample="false" enableDeleteByExample="false"
               enableSelectByExample="false"
               enableUpdateByExample="false">
            <property name="ignoreQualifiersAtRuntime" value="true"/>
        </table>
为本次要生成文件的表

3、maven build输入命令：mybatis-generator:generate -e则会生成对应实体类及mapper

