package constant;

/**
 * 获取返回代码
 * 
 * -1~-99 警告 -100~-1000 错误 -9999 异常 0默认
 */
public interface IsModifiedUtilConstant {

	/**
	 * 比较的类型不一致
	 */
	int TYPE_MISMATCH_CODE = -100;
	String TYPE_MISMATCH_INFO = "参与比较的类型不一致";

	/**
	 * before和after类中所拥有的成员变量列表数量不一致
	 */
	int FIELDS_MISMATCH_CODE = -100;
	String FIELDS_MISMATCH_INFO = "参与比较类型的成员变量列表数量不一致";

	/**
	 * before中存在的变量，在after中不存在
	 */
	int NO_SPECIFIED_TYPE_IN_AFTER_CODE = -102;
	String NO_SPECIFIED_TYPE_IN_AFTER_INFO = "修改前类型的成员变量不存在于修改后类型中";

}
