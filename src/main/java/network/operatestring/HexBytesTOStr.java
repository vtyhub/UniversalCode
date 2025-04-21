package network.operatestring;


public class HexBytesTOStr {

	/** 
	 * ����һ�� byte[] to hex string
	 * 
	 * @param bytes
	 * @return
	 */ 
	public static String hexBytesTOS1(byte[] bytes) {
		// һ��byteΪ8λ����������ʮ������λ��ʶ
		final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] buf = new char[bytes.length * 2];
		int a = 0;
		int index = 0;
		for (byte b : bytes) { // ʹ�ó���ȡ�����ת��
			if (b < 0) {
				a = 256 + b;
			} else {
				a = b;
			}

			buf[index++] = HEX_CHAR[a / 16];
			buf[index++] = HEX_CHAR[a % 16];
		}

		return new String(buf);
	}

	/**
	 * �������� byte[] to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String hexBytesTOS2(byte[] bytes) {
		final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] buf = new char[bytes.length * 2];
		int index = 0;
		for (byte b : bytes) { // ����λ�������ת�������Կ�������һ�ı���
			buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
			buf[index++] = HEX_CHAR[b & 0xf];
		}

		return new String(buf);
	}

	/**
	 * �������� byte[] to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String hexBytesTOS3(byte[] bytes) {
		StringBuilder buf = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) { // ʹ��String��format��������ת��
			buf.append(String.format("%02x", new Integer(b & 0xff)));
		}

		return buf.toString();
	}

}
