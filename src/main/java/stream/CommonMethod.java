package stream;


public class CommonMethod {
	/**
	 * 批量关闭流
	 * 
	 * @param streams
	 * @throws Exception
	 */
	public static void batchClose(AutoCloseable... streams) throws Exception {
		for (AutoCloseable stream : streams) {
			stream.close();
		}
	}
}
