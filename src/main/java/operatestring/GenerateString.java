package operatestring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.filechooser.FileSystemView;

public class GenerateString implements GeneralOperation {

	/**
	 * 以mould为模版，从mould的startindex索引开始,循环复制出一个长度为n的字符串
	 * 
	 * @param n
	 * @param startindex
	 * @param mould
	 * @return
	 */
	public static String genLoopString(int n, int startindex, String mould) {
		int len = mould.length(), group = n / len, module = n % len;
		n = n < 0 ? 0 : n;
		startindex = correctStartIndex(startindex, len);
		String left = GeneralOperation.ringShiftLeft(mould, startindex);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < group; i++) {
			sb.append(left);
		}
		if (module != 0) {
			return sb.append(left.substring(0, module)).toString();
		}
		return sb.toString();
	}

	/**
	 * 生成循环字符串时调用
	 * 
	 * @param startindex
	 * @param len
	 * @return
	 */
	public static int correctStartIndex(int startindex, int len) {
		return startindex < 0 ? 0 : startindex > len - 1 ? len - 1 : startindex;
	}

	/**
	 * 在mould中存在的字符中选择，随机生成一个长度为l的字符串，为了保证随机性应当对mould去重
	 * 
	 * @param l
	 * @param mould
	 */
	public static String genRandomString(int l, String mould) {
		String template = GeneralOperation.duplicateRemoval(mould);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < l; i++) {
			sb.append(template.charAt(new Random().nextInt(template.length())));
		}
		return sb.toString();
	}

	/**
	 * 将mould复制l遍
	 * 
	 * @param l
	 * @param mould
	 * @return
	 */
	public static String copyMould(int l, String mould) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < l; i++) {
			sb.append(mould);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		int len = 100;
		String file = "Len" + len;
		String suffix = ".txt";
		File desktop = FileSystemView.getFileSystemView().getHomeDirectory();
		for (int i = 1; i < 4; i++) {
			try (PrintWriter pw = new PrintWriter(new File(desktop + "/" + i + file + suffix))) {
				pw.println(genRandomString(len, "ATGC"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
