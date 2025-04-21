package type;

import java.util.HashSet;
import java.util.Set;

public class TypeJudgeUtil {

	private static Set<String> basicTypeSet;

	static {
		basicTypeSet = new HashSet<String>();

		// 整数4种
		basicTypeSet.add("byte");
		basicTypeSet.add("short");
		basicTypeSet.add("int");
		basicTypeSet.add("long");

		// 浮点2种
		basicTypeSet.add("float");
		basicTypeSet.add("double");

		// 字符1种
		basicTypeSet.add("char");

		// 布尔1种
		basicTypeSet.add("boolean");
	}

	/**
	 * 检测类型是否为基本类型
	 * 
	 * @param typeName
	 * @return typeName对应的类型名称是否是基本类型
	 */
	public static boolean isBasicType(String typeName) {
		return basicTypeSet.contains(typeName);
	}

	/**
	 * 检测类型是否为JDK自带
	 * 
	 * @param typeName
	 * @return
	 */
	public static boolean isJdkType(String typeName) {
		return typeName == null ? false : typeName.startsWith("java");
	}
}
