package algorithm;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class PermuteCombine {

	// -------------------permutation-and-combination--------------------------------------------



	/**
	 * @param a
	 * @return a的组合
	 */
	public static String[][] combine(String[] a) {
		int cl = (a.length * (a.length - 1)) / 2;
		int clcpy = cl;
		String x[][] = new String[cl][1];
		String tem[] = new String[2];

		for (int i = 0; i < a.length; i++)
			for (int j = a.length - 1; j > 0 + i - 1; j--) {
				if (a[j] != a[i]) {
					tem[1] = a[j];
					tem[0] = a[i];
					x[clcpy - 1] = tem.clone();
					clcpy--;
				}
			}
		return x;
	}

	/**
	 * 将inviter和每个invitee组合起来
	 * @param inviterName
	 * @param inviteeArray
	 * @return
	 */
	public static String[][] combine(String inviterName, String[] inviteeArray) {
		String[][] result = new String[inviteeArray.length][2];
		for (int i = 0; i < result.length; i++) {
			result[i][0] = inviterName;
			result[i][1] = inviteeArray[i];
		}
		return result;
	}

	public static String[][] permutate(String[] a, int take) {
		// 从a中取出take个元素排列
		if (a == null || a.length < take) {
			throw new IllegalArgumentException(take + ">" + a.length);
		}
		ArrayList<String> tmp = new ArrayList<String>();
		// 可能的情况数
		// result为Object数组，返回时会将其强制转换为实际类型，这是问题
		// T[][] result = (T[][]) new Object[factorial(a.length) / factorial(a.length -
		// take)][take];
		ArrayList<String[]> relist = new ArrayList<String[]>();
		return permutate(take, a, tmp, relist);
	}

	public static String[][] permutate(int k, String[] arr, ArrayList<String> tmpArr, ArrayList<String[]> relist) {
		if (k == 1) {
			for (int i = 0; i < arr.length; i++) {
				tmpArr.add(arr[i]);
				// 此时数组内就是完整结果
				relist.add(tmpArr.toArray(new String[0]));
				tmpArr.remove(arr[i]);
			}
		} else if (k > 1) {

			for (int i = 0; i < arr.length; i++) { // 按顺序挑选一个元素
				tmpArr.add(arr[i]); // 添加选到的元素
				permutate(k - 1, removeArrayElements(arr, tmpArr.toArray(new String[0])), tmpArr, relist); // 没有取过的元素，继续挑选
				tmpArr.remove(arr[i]);
			}
		}
		return relist.toArray(new String[0][]);
	}

	// 从数组中删除elements元素,不改变原数组
	public static String[] removeArrayElements(String[] arr, String... elements) {
		List<String> remainList = new ArrayList<>(arr.length);
		for (int i = 0; i < arr.length; i++) {
			boolean find = false;
			for (int j = 0; j < elements.length; j++) {
				if (arr[i].equals(elements[j])) {
					find = true;
					break;
				}
			}
			if (!find) { // 没有找到的元素保留下来
				remainList.add(arr[i]);
			}
		}
		String[] remainArray = new String[remainList.size()];
		for (int i = 0; i < remainList.size(); i++) {
			remainArray[i] = remainList.get(i);
		}
		return remainArray;
	}


}
