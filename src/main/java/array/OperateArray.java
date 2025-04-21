package array;

import java.util.ArrayList;
import java.util.List;

public class OperateArray {

	/**
	 * 返回删除指定元素后的数组，不改变原数组
	 * 
	 * @param arr
	 * @param elements
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] removeArrayElements(T[] arr, T... elements) {
		List<T> remainList = new ArrayList<>(arr.length);
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
		T[] remainArray = (T[]) new Object[remainList.size()];
		for (int i = 0; i < remainList.size(); i++) {
			remainArray[i] = remainList.get(i);
		}
		return remainArray;
	}
	
	// -----------------------------------------------------------------
	// 从src数组中，把dst中已经存在的内容完全相同的一维数组去掉，src应该是dst的超集，若dst是src的超集，则返回null？
	public static <E> ArrayList<E[]> removeSrc(E[][] src, E[][] dst) {
		if (src == null || dst == null) {
			throw new IllegalArgumentException();
		}

		ArrayList<E[]> result = new ArrayList<E[]>(src.length);
		e: for (int i = 0; i < src.length; i++) {
			E[] singlesrc = src[i];
			for (int j = 0; j < dst.length; j++) {
				E[] singledst = dst[j];
				if (singlesrc.length != singledst.length) {
					continue;
				}
				int count = 0;
				while (count < singlesrc.length) {
					if (!singlesrc[count].equals(singledst[count])) {
						// 若不相等，则不需要再比此列，继续比下一数组即可
						break;
					}
					// 对应相等，count++
					count++;
				}
				if (count >= singlesrc.length) {
					// 若正常比完了都没有break，则说明这个在里面有重复，从线性表删除该元素并continue e;
					continue e;
				}
			}
			// 正常执行到这里还没有触发过continue e，说明全都相等
			result.add(singlesrc);
		}
		if (result.size() == 0) {
			return null;
		}
		return result;
	}
	
	//----------------------------------------------------------------------------------

}
