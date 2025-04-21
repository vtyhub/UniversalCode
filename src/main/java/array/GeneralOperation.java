package array;

import java.util.LinkedHashSet;

/**
 * 
 *
 */
public class GeneralOperation {

	/**
	 * 返回数组第一个非NULL元素索引
	 * 
	 * @param a
	 * @return
	 */
	public static <T> int notNull(T[][] a) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	// 从输入的二维数组中提取出所有不重复的元素
	public static <E> LinkedHashSet<E> extract(E[][] a) {
		if (a == null) {
			throw new IllegalArgumentException();
		}
		LinkedHashSet<E> result = new LinkedHashSet<>();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] != null && !"".equals(a[i][j]))
					result.add(a[i][j]);
			}
		}
		return result;
	}
}
