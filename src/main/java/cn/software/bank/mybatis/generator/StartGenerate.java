package cn.software.bank.mybatis.generator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * @Title: StartUp.java
 * @Package com.fendo.mybatis_generator_plus
 * @Description: 自动生成实体类，mapper    启动时需要解开//   myBatisGenerator.generate(null);
 * @author fendo
 * @version V1.0
 */
public class StartGenerate {

	public static void main(String[] args) throws URISyntaxException, SQLException, InterruptedException {
		InputStream in = null;
		// 执行中的异常信息会保存在warnings中
		List<String> warnings = new ArrayList<String>();
		try {
			System.out.println("----- 开始 -----");
			// true:生成的文件覆盖之前的
			boolean overwrite = true;
			// 读取配置,构造 Configuration 对象.
			// 如果不想使用配置文件的话,也可以直接来 new Configuration(),然后给相应属性赋值.
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			in = classloader.getResourceAsStream("mybatis-generator-config.xml");
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(in);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);//需要时解开
			System.out.println("-----操作成功-----");
		}  catch (IOException e) {
			e.printStackTrace();
		}  catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		for (String warning : warnings) {
//			System.out.println(warning);
		}
	}
}
