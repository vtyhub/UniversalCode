package type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import constant.IsModifiedUtilConstant;
import poi.word.TestModifA;
import poi.word.TestModifB;



/**
 * 用于比较同一个类的两个实例是否相同
 * 
 * @author ljp
 * @since 19-8-5
 *
 */

public class IsModifiedUtil implements IsModifiedUtilConstant {

	/**
	 * 修改内容列表，若列表为null则未修改过，若列表不为null则修改过
	 */
	private List<ModifiedAttribute> modifiedAttributeList;

	private List<ErrorInfo> errorInfoList;

	private IsModifiedUtil() {
	}

	/**
	 * 
	 * @param <E>
	 * @param beforeModified 修改前的值
	 * @param afterModified  修改后的值
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <E> IsModifiedUtil compare(E beforeModified, E afterModified)
			throws IllegalArgumentException, IllegalAccessException {
		IsModifiedUtil result = new IsModifiedUtil();
		ArrayList<ModifiedAttribute> list = new ArrayList<ModifiedAttribute>();
		ArrayList<ErrorInfo> errorList = new ArrayList<>();
		result.errorInfoList = errorList;
		result.modifiedAttributeList = list;// 不提供对外set方法

		String beforeType = "", afterType = "";// 当前顶级值的类型

		// 空值判断存在逻辑错误，after为null但before不为null的情况如何检测？不用三元运算符了，是否相等在后面检测
		if (beforeModified == null || afterModified == null) {
			// 若两者都为null，或者两者通过equals比较直接就相等，可以得知没有进行过修改直接返回，借鉴于list的equals
			// 但若两个变量名称相同，类型不同，但都是null的话，因为无法反射，也没法发现不同之处，只会默认相同
			if (beforeModified == afterModified) {
				// 均为null
				return result;
			} else if (beforeModified == null) {
				// before是null而after不是
				beforeType = afterType = afterModified.getClass().getSimpleName();
			} else {
				// after是null而before不是
				beforeType = afterType = beforeModified.getClass().getSimpleName();
			}
			// 其他情况有必要确定谁是null，因为要通过非null的那个来反射拿到变量类型
			ModifiedAttribute modifiedAttribute = new ModifiedAttribute();
			modifiedAttribute.setModifiedAttributeName(beforeType);// 因为有一个是null，因此无法判断类型只能使用一个共同的
			modifiedAttribute.setBefore(beforeModified);
			modifiedAttribute.setAfter(afterModified);
			list.add(modifiedAttribute);
			return result;
		} else {
			// 不存在null
			beforeType = beforeModified.getClass().getSimpleName();
			afterType = afterModified.getClass().getSimpleName();
		}

		return compare(result, "", beforeType, afterType, beforeModified, afterModified);
	}

	/**
	 * 递归时调用方法，实际处理数据的方法
	 * 默认显示明细，因为这个方法只是返回成员变量的区别，具体map或list中元素的区别要通过另一个方法调用，在这两个方法的合体方法中有是否明细选项
	 * 指的就是是否显示map中元素区别的明细，而这个copmare里是一定要默认明细的
	 * 
	 * @param <E>
	 * @param result           最终结果，在递归调用中传递
	 * @param currentLayerName 进行比较的变量之前累计的层次
	 * @param beforeName       修改前的值的变量名
	 * @param afterName        修改后的值的变量名
	 * @param beforeModified   修改前的值
	 * @param afterModified    修改后的值
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <E> IsModifiedUtil compare(IsModifiedUtil result, String currentLayerName, String beforeName,
			String afterName, E beforeModified, E afterModified)
			throws IllegalArgumentException, IllegalAccessException {
		// 递归时在方法之间传递result，list在一开始就放入其中传递
		List<ModifiedAttribute> modifiedAttrList = result.getModifiedAttributeList();
		List<ErrorInfo> errorInfoList = result.getErrorInfoList();

		// 首先通过空值判断
		// 空值判断存在逻辑错误，after为null但before不为null的情况如何检测？不用三元运算符了，是否相等在后面检测
		if (beforeModified == null || afterModified == null) {
			// 若两者都为null，或者两者通过equals比较直接就相等，可以得知没有进行过修改直接返回，借鉴于list的equals
			// 但若两个变量名称相同，类型不同，但都是null的话，因为无法反射，也没法发现不同之处，只会默认相同
			if (beforeModified == afterModified) {
				// 均为null
				return result;
			} else {
				// 其他情况无所谓谁是不是null，反正已经确定不相等了，就是发生了修改
				ModifiedAttribute modifiedAttribute = new ModifiedAttribute();
				// 标记方式为当前所在类的变量名.该属性的变量名 断点标记上次写到的位置
				if ("".equals(beforeName)) {
					// 若比较的变量即为顶级，无法获取变量名
					modifiedAttribute.setModifiedAttributeName(currentLayerName);
				} else {
					modifiedAttribute.setModifiedAttributeName(currentLayerName + '.' + beforeName);
				}
				modifiedAttribute.setBefore(beforeModified);
				modifiedAttribute.setAfter(afterModified);
				modifiedAttrList.add(modifiedAttribute);
				return result;
			}
		}

		// 开始反射
		Class<? extends Object> beforeClass = beforeModified.getClass();
		Class<? extends Object> afterClass = afterModified.getClass();
		// 修改前后变量类型全名
		String beforeType = beforeClass.getTypeName();
		String afterType = afterClass.getTypeName();

		// 检测参与比较的类型是否相同
		if (!beforeType.equals(afterType)) {
			// 若两个都是Object类型则可以绕过泛型，但是实际比较起来就会出现问题,写入errorInfo
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(TYPE_MISMATCH_CODE);
			errorInfo.setErrorMessage("错误种类:" + TYPE_MISMATCH_INFO + ".\n层次:" + currentLayerName + ".\n修改前类型:"
					+ beforeType + ",变量名:" + beforeName + ".\n修改后类型:" + afterType + ",变量名:" + afterName + '.');

			errorInfo.setBefore(beforeModified);
			errorInfo.setBeforeAttributeType(beforeType);
			errorInfo.setBeforeAttributeName(beforeName);

			errorInfo.setAfter(afterModified);
			errorInfo.setAfterAttributeType(afterType);
			errorInfo.setAfterAttributeName(afterName);
			errorInfoList.add(errorInfo);
			return result;
		}

		// 若参与比较的是基本类型或jdk自带类型，则默认其实现了equals方法，直接比较
		if (TypeJudgeUtil.isBasicType(beforeType) || TypeJudgeUtil.isJdkType(beforeType)) {
			// 类型相同只要检测一个即可,如果是基本类型的话,直接进行比较，若相同则返回，若不相同则记录不同之处后返回
			// 但相同的情况在空值判断已经被过滤掉了，因此只剩下不相同其实可以不判断但语义上还是加上
			if (!beforeModified.equals(afterModified)) {
				ModifiedAttribute modifiedAttribute = new ModifiedAttribute();
				// 标记方式为当前所在类的变量名.该属性的变量名 断点标记上次写到的位置
				if ("".equals(beforeName)) {
					// 若比较的变量即为顶级，无法获取变量名
					modifiedAttribute.setModifiedAttributeName(currentLayerName);
				} else {
					modifiedAttribute.setModifiedAttributeName(currentLayerName + '.' + beforeName);
				}
				modifiedAttribute.setBefore(beforeModified);
				modifiedAttribute.setAfter(afterModified);
				modifiedAttrList.add(modifiedAttribute);
				return result;
			} else {
				// 相等的情况，直接返回
				return result;
			}
		}

		// 其余情况，比较的是两个非基本类型与jdk自定义类型，很可能未实现equals方法，如com开头的类型，递归对其中所有成员变量使用本方法
		Field[] beforeFields = beforeClass.getDeclaredFields();
		Field[] afterFields = afterClass.getDeclaredFields();

		if (beforeFields.length != afterFields.length) {
			// 长度不同时
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(FIELDS_MISMATCH_CODE);
			errorInfo.setErrorMessage("错误种类:" + FIELDS_MISMATCH_INFO + ".\n层次:" + currentLayerName + ".\n修改前类型:"
					+ beforeType + ",变量名:" + beforeName + ".\n修改后类型:" + afterType + ",变量名:" + afterName + '.');

			errorInfo.setBefore(beforeModified);
			errorInfo.setBeforeAttributeType(beforeType);
			errorInfo.setBeforeAttributeName(beforeName);

			errorInfo.setAfter(afterModified);
			errorInfo.setAfterAttributeType(afterType);
			errorInfo.setAfterAttributeName(afterName);
			errorInfoList.add(errorInfo);
			return result;
		}

		// 填充进map
		HashMap<String, Field> beforeMap = new HashMap<String, Field>();
		HashMap<String, Field> afterMap = new HashMap<String, Field>();
		for (Field field : beforeFields) {
			field.setAccessible(true);// 防止后面访问不到，提前设置
			beforeMap.put(field.getName(), field);
		}
		for (Field field : afterFields) {
			field.setAccessible(true);// 防止后面访问不到，提前设置
			afterMap.put(field.getName(), field);
		}

		// 根据选择比较的参数是否为null或值为空区分比较模式，在递归中过于复杂暂时取消默认全部比较
//		if (attributesToCompare != null && attributesToCompare.size() != 0) {
//			// 无需遍历两个map，只需要遍历参数里的这个，依次来比较两个map即可
//			// 若传过来的参数全都不存在，则就视为没有修改过
//			for (String attrName : attributesToCompare) {
//				Field beforeField = beforeMap.get(attrName);
//				if (beforeField == null) {
//					// 万一该属性不在任意map中存在
//					continue;
//				}
//				Field afterField = afterMap.get(attrName);
//				if (afterField == null) {
//					continue;
//				}
//				Object beforeToCompare = beforeField.get(beforeModified);
//				Object afterToCompare = afterField.get(afterModified);
//				// 在前面进行的是对参数的空值校验，参数中变量的值还未进行
//				if (!(beforeToCompare == null ? afterToCompare == null : beforeToCompare.equals(afterToCompare))) {
//					// 不相等时,将不相等数据添加至list
//					ModifiedAttribute modifiedAttribute = new ModifiedAttribute();
//					modifiedAttribute.setModifiedAttributeName(attrName);
//					modifiedAttribute.setBefore(beforeToCompare);
//					modifiedAttribute.setAfter(afterToCompare);
//					modifiedAttrList.add(modifiedAttribute);
//				}
//			}
//		} 

		// 全部比较，遍历before
		for (Field beforeField : beforeFields) {
			Field afterField = afterMap.get(beforeField.getName());
			if (afterField == null) {
				// before中存在的域after中不存在
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(NO_SPECIFIED_TYPE_IN_AFTER_CODE);
				errorInfo.setErrorMessage("错误种类:" + NO_SPECIFIED_TYPE_IN_AFTER_INFO + ".\n层次:" + currentLayerName
						+ ".\n修改前类型:" + beforeType + ",变量名:" + beforeName + ".\n修改后类型:" + afterType + ",变量名:"
						+ afterName + '.');

				errorInfo.setBefore(beforeModified);
				errorInfo.setBeforeAttributeType(beforeType);
				errorInfo.setBeforeAttributeName(beforeName);

				errorInfo.setAfter(afterModified);
				errorInfo.setAfterAttributeType(afterType);
				errorInfo.setAfterAttributeName(afterName);
				errorInfoList.add(errorInfo);

				// 不能返回，在这里需要遍历完之后才可以返回
				continue;
			}

//			Object beforeToCompare = beforeField.get(beforeModified);
//			Object afterToCompare = afterField.get(afterModified);
//			// 在前面进行的是对参数的空值校验，参数中变量的值还未进行
//			if (!(beforeToCompare == null ? afterToCompare == null : beforeToCompare.equals(afterToCompare))) {
//				// 不相等时,将不相等数据添加至list
//				ModifiedAttribute modifiedAttribute = new ModifiedAttribute();
//				modifiedAttribute.setModifiedAttributeName(beforeField.getName());
//				modifiedAttribute.setBefore(beforeToCompare);
//				modifiedAttribute.setAfter(afterToCompare);
//				modifiedAttrList.add(modifiedAttribute);
//			}

			// 似乎不需要返回？里面应该已经把东西写入result了
			compare(result, currentLayerName + '.' + beforeName + '.' + beforeField.getName(), beforeField.getName(),
					afterField.getName(), beforeField.get(beforeModified), afterField.get(afterModified));
		}

		return result;
	}

	/**
	 * @return 修改内容
	 */
	public List<ModifiedAttribute> getModifiedAttributeList() {
		return modifiedAttributeList;
	}

	/**
	 * @return 错误信息
	 */
	public List<ErrorInfo> getErrorInfoList() {
		return errorInfoList;
	}

	/**
	 * 判断依据是，修改列表或者错误列表之中有一个有内容，就认为是修改过
	 * 
	 * @return 比较的元素是否修改过
	 */
	public boolean isModified() {
		return (modifiedAttributeList != null && modifiedAttributeList.size() > 0)
				|| (errorInfoList != null && errorInfoList.size() > 0);
	}

	@SuppressWarnings("unused")
	private static class ModifiedAttribute {
		/**
		 * 修改的属性名
		 */
		private String modifiedAttributeName;
		/**
		 * 修改前的值
		 */
		private Object before;
		/**
		 * 修改后的值
		 */
		private Object after;

		public String getModifiedAttributeName() {
			return modifiedAttributeName;
		}

		public void setModifiedAttributeName(String modifiedAttributeName) {
			this.modifiedAttributeName = modifiedAttributeName;
		}

		public Object getBefore() {
			return before;
		}

		public void setBefore(Object before) {
			this.before = before;
		}

		public Object getAfter() {
			return after;
		}

		public void setAfter(Object after) {
			this.after = after;
		}

	}

	@SuppressWarnings("unused")
	private static class ErrorInfo {

		private int errorCode; // 错误代码

		private String errorMessage = ""; // 错误信息

		private String beforeAttributeType;// 发生错误时修改前的带深度的类型

		private String beforeAttributeName;// 发生错误时修改前的带深度的类型

		private String afterAttributeType;// 发生错误时修改后的带深度的类型

		private String afterAttributeName;// 发生错误时修改后的带深度的类型

		private Object before;// 修改前值

		private Object after;// 修改后值

		public int getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getBeforeAttributeType() {
			return beforeAttributeType;
		}

		public void setBeforeAttributeType(String beforeAttributeType) {
			this.beforeAttributeType = beforeAttributeType;
		}

		public String getBeforeAttributeName() {
			return beforeAttributeName;
		}

		public void setBeforeAttributeName(String beforeAttributeName) {
			this.beforeAttributeName = beforeAttributeName;
		}

		public String getAfterAttributeType() {
			return afterAttributeType;
		}

		public void setAfterAttributeType(String afterAttributeType) {
			this.afterAttributeType = afterAttributeType;
		}

		public String getAfterAttributeName() {
			return afterAttributeName;
		}

		public void setAfterAttributeName(String afterAttributeName) {
			this.afterAttributeName = afterAttributeName;
		}

		public Object getBefore() {
			return before;
		}

		public void setBefore(Object before) {
			this.before = before;
		}

		public Object getAfter() {
			return after;
		}

		public void setAfter(Object after) {
			this.after = after;
		}

		/**
		 * 获取返回代码
		 * 
		 * -1~-99 警告 -100~-1000 错误 -9999 异常 0默认
		 */
	}

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		TestModifA t1 = new TestModifA();
		TestModifA t2 = new TestModifA();
		TestModifB t3 = new TestModifB();

		t1.setIntea(123123);
		t1.setBoola(false);
		t1.setLista(new ArrayList<>());
		t1.setMapa(new HashMap<>());
		ArrayList<TestModifB> listb = new ArrayList<TestModifB>();
		listb.add(t3);
		t1.setListforb(listb);
		t1.setB(t3);

		IsModifiedUtil result1 = compare(t1, t2);

		System.out.println(result1.isModified());

		List<ModifiedAttribute> result1modlist = result1.getModifiedAttributeList();
		List<ErrorInfo> result1errorlist = result1.getErrorInfoList();

		System.out.println();
	}
}
