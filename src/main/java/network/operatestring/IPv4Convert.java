package network.operatestring;

public class IPv4Convert {
	
	public static final String ARPLayerBroadcastIP = "0.0.0.0";
	public static final byte[] ARPLayerBroadcastIPBytes = strToBytes(ARPLayerBroadcastIP);
 
	/**
	 * ͨ��ǰ׺��ȡbyte[],String��������������㷨,String�Ĺ�Լ��byte[]
	 * 
	 * @param prefix
	 * @return
	 */
	public static byte[] getIPv4ByteMask1(int prefix) {
		final int byteofbit = 8, number = 4, minprefix = 0, maxprefix = byteofbit * number;
		if (prefix < minprefix) {
			prefix = minprefix;
		}
		if (prefix > maxprefix) {
			prefix = maxprefix;
		}
		byte[] mask = new byte[number];
		int group = prefix / byteofbit, mod = prefix % byteofbit;
		for (int i = 0; i < group; i++) {
			mask[i] = (byte) 0xff;
		}
		if (mod != 0) {
			for (int i = 1; i <= mod; i++) {
				mask[group] += 1L << (byteofbit - i);// Ϊ���Է���һ�����ǲ���1L
			}
			for (int i = 0; i < number - group - 1; i++) {
				mask[group + 1 + i] = 0x00;
			}
		} else {
			for (int i = 0; i < number - group; i++) {
				mask[group + i] = 0x00;
			}
		}
		return mask;
	}

	public static byte[] getIPv4ByteMask2(int prefix) {
		final int byteofbit = 8, number = 4, minprefix = 0, maxprefix = byteofbit * number;
		if (prefix < minprefix) {
			prefix = minprefix;
		}
		if (prefix > maxprefix) {
			prefix = maxprefix;
		}
		int tempmask = 0;
		byte[] mask = new byte[number];
		for (int i = 1; i <= prefix; i++) {
			tempmask += 1 << (maxprefix - i);
		}
		for (int i = number - 1; i >= 0; i--) {
			mask[i] |= tempmask >>> (maxprefix - (i + 1) * byteofbit);// ���ֽ������ȡ�����Ӧ���ǻ�����
		}
		return mask;
	}

	public static String getIPv4StrMask1(int prefix) {
		return bytesToStr(getIPv4ByteMask1(prefix));
	}

	public static String getIPv4StrMask2(int prefix) {
		return bytesToStr(getIPv4ByteMask2(prefix));
	}

	/**
	 * ͨ��byte[],String���������ȡǰ׺���ȣ����߹�Լ��ǰ��
	 * 
	 * @param bytemask
	 * @return
	 */
	public static int getPrefix(byte[] bytemask) {
		int tempmask = 0, byteofbit = 8;
		for (int i = 0; i < bytemask.length; i++) {
			int temp = bytemask[i] << (byteofbit * (bytemask.length - i - 1));
			tempmask |= temp;
		}
		return Integer.toBinaryString(tempmask).lastIndexOf("1") + 1;// ���ص���������������0��ʼ����������������Ҫ��1
	}

	public static int getPrefix(String strmask) {
		return getPrefix(strToBytes(strmask));
	}

	/**
	 * ͨ��ǰ׺���ȣ�byte[]��ʽ�������룬String���������ȡ��������������������Լ��ǰ׺����
	 * 
	 * @param prefix
	 * @return
	 */
	public static long getMaxHostNum(int prefix) {
		// ����һ��ʼʹ��1Ĭ��Ϊint�����ƴﵽ31λ�ͻ����쳣���������ʹ��1L���ܴ���һ��long
		return (1L << (32 - prefix)) - 2;
	}

	public static long getMaxHostNum(byte[] bytemask) {
		return getMaxHostNum(getPrefix(bytemask));
	}

	public static long getMaxHostNum(String strmask) {
		return getMaxHostNum(getPrefix(strmask));
	}

	/**
	 * byte[]��ʽ��String��ʽ��������Ļ���ת��
	 * 
	 * @param ipv4bytes
	 * @return
	 */
	public static String bytesToStr(byte[] ipv4bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ipv4bytes.length; i++) {
			int temp = ipv4bytes[i] & 0xff;// ʹ�÷�Χ�����int���������λΪ1��Java��û���޷��ű���
			sb.append(temp);// ����֮���Բ����÷�Χ�պ��ʵ�short����Ϊ���õĻ���Java������Ҫ�����&0xff�������ı��ʽ�Ľ��ǿ��ת��Ϊshort
			sb.append('.');// Ҳ����ΪJavaλ����Ĭ�Ϸ���intֵ����shortռ�õ��ڴ��ֽ�����intС��Ե�ʣ�tempʹ��longҲ����Ҫ��ת��int��ʹ��ռ�ÿռ����Ĳ�����ַ���λ�쳣
							// ��������ת�����ڱ���������İ�ȫ��Χ֮��
		}
		return sb.substring(0, sb.length() - 1);// -1��Ϊ�˽����һ��.ȥ������λ֮��ֻ������.
	}

	// ��172.16.1.242������ʮ�����ַ���ת��Ϊbyte����
	public static byte[] strToBytes(String ipv4str) {
		String[] ipv4strsp = ipv4str.trim().split("\\.");
		byte[] ipv4bytes = new byte[ipv4strsp.length];
		for (int i = 0; i < ipv4bytes.length; i++) {

			// ʹ��Byte.parseByte(ipv4strsp[i], 10)��ת���쳣����ΪJava��û���޷�������
			// byte���͵�ȡֵ��-128��127֮�䣬����һ��ipv4strsp[i]��ʮ���Ʊ�ʾ�����������Χ���ͻ�ת���쳣�������Ǻܳ�����
			// ����192.168.170.155���ĸ���ַλÿһλ�Ͷ������������Χ��ֻ��ͨ��ʹ���ڴ��д洢�ռ��������ͱ������Ϊ��
			// �������þ�����С�Ŀռ䣬�ܸ�Ŀ�꾡����ƥ���ƥ���ԭ��û��ʹ�ô����ձ�ʹ�õ�Integer��ʹ����Short

			ipv4bytes[i] = (byte) Short.parseShort(ipv4strsp[i], 10);
		}
		return ipv4bytes;
	}

	/**
	 * ͨ��������ַ���������뷵�������ַ
	 * 
	 * @param localIPBytes
	 * @param maskBytes
	 * @return
	 */
	public static byte[] networkAddressBytes(byte[] localIPBytes, byte[] maskBytes) {
		byte[] networkaddress = new byte[maskBytes.length];
		for (int i = 0; i < maskBytes.length; i++) {
			networkaddress[i] = (byte) (localIPBytes[i] & maskBytes[i]);
		}
		return networkaddress;
	}

	public static byte[] networkAddressBytes(byte[] localIPBytes, int prefix) {
		return networkAddressBytes(localIPBytes, getIPv4ByteMask2(prefix));
	}

	public static byte[] networkAddressBytes(String localIP, String mask) {
		byte[] localIPBytes = IPv4Convert.strToBytes(localIP);
		byte[] maskBytes = IPv4Convert.strToBytes(mask);
		return networkAddressBytes(localIPBytes, maskBytes);
	}

	public static byte[] networkAddressBytes(String localIP, int prefix) {
		return networkAddressBytes(localIP, getIPv4StrMask2(prefix));
	}

	/**
	 * ����String���͵������ַ
	 * 
	 * @param localIPBytes
	 * @param maskBytes
	 * @return
	 */
	public static String networkAddressStr(byte[] localIPBytes, byte[] maskBytes) {
		return bytesToStr(networkAddressBytes(localIPBytes, maskBytes));
	}

	public static String networkAddressStr(byte[] localIPBytes, int prefix) {
		return networkAddressStr(localIPBytes, getIPv4ByteMask1(prefix));
	}

	public static String networkAddressStr(String localIP, String mask) {
		return bytesToStr(networkAddressBytes(localIP, mask));
	}

	public static String networkAddressStr(String localIP, int prefix) {
		return networkAddressStr(localIP, getIPv4StrMask1(prefix));
	}
}
