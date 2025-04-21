package poi.word;


import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder.Enum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class OutPutWord {

	/**
	 * 为一个指定document根据tableModel来生成对应的表格
	 * 
	 * @param documet
	 * @param tableModel
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void createTable(XWPFDocument documet, WordTableModel tableModel) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
		int rowCount = tableModel.getRowCount(), colCount = tableModel.getColCount();
		XWPFTable table;
		if (rowCount == 0 || colCount == 0) {
			table = documet.createTable();
		} else {
			table = documet.createTable(rowCount, colCount);
		}
		CTTbl cttbl = table.getCTTbl();// cttbl
		CTTblPr cttblpr = cttbl.getTblPr() == null ? cttbl.addNewTblPr() : cttbl.getTblPr();// cttblpr
		
		// 设置表格边框样式
		CTTblBorders cttblborders = cttblpr.isSetTblBorders() ? cttblpr.getTblBorders() : cttblpr.addNewTblBorders();
		// 设置上边框样式
		WordTableBorderModel topBorderModel = tableModel.getTopBorderModel();
		if (topBorderModel != null) {
			CTBorder tBorder = cttblborders.isSetTop() ? cttblborders.getTop() : cttblborders.addNewTop();// 顶外边框
			// 设置边框样式
			Enum borderStyle = topBorderModel.getBorderStyle();
			if (borderStyle != null) {
				tBorder.setVal(borderStyle);
			}
			// 边框粗细
			int size = topBorderModel.getSize();
			if (size != 0) {
				tBorder.setSz(BigInteger.valueOf(size));
			}
			// 边框颜色
			String color = topBorderModel.getColor();
			if (color != null) {
				tBorder.setColor(color);
			}
		}
		// 底边框样式
		WordTableBorderModel bottomBorderModel = tableModel.getBottomBorderModel();
		if (bottomBorderModel != null) {
			CTBorder bBorder = cttblborders.isSetBottom() ? cttblborders.getBottom() : cttblborders.addNewBottom();
			// 设置边框样式
			Enum borderStyle = bottomBorderModel.getBorderStyle();
			if (borderStyle != null) {
				bBorder.setVal(borderStyle);
			}
			// 边框粗细
			int size = bottomBorderModel.getSize();
			if (size != 0) {
				bBorder.setSz(BigInteger.valueOf(size));
			}
			// 边框颜色
			String color = bottomBorderModel.getColor();
			if (color != null) {
				bBorder.setColor(color);
			}
		}
		// 左边框样式
		WordTableBorderModel leftBorderModel = tableModel.getLeftBorderModel();
		if (leftBorderModel != null) {
			CTBorder lBorder = cttblborders.isSetLeft() ? cttblborders.getLeft() : cttblborders.addNewLeft();
			// 设置边框样式
			Enum borderStyle = leftBorderModel.getBorderStyle();
			if (borderStyle != null) {
				lBorder.setVal(borderStyle);
			}
			// 边框粗细
			int size = leftBorderModel.getSize();
			if (size != 0) {
				lBorder.setSz(BigInteger.valueOf(size));
			}
			// 边框颜色
			String color = leftBorderModel.getColor();
			if (color != null) {
				lBorder.setColor(color);
			}
		}
		// 右边框样式
		WordTableBorderModel rightBorderModel = tableModel.getRightBorderModel();
		if (rightBorderModel != null) {
			CTBorder rBorder = cttblborders.isSetRight() ? cttblborders.getRight() : cttblborders.addNewRight();
			// 设置边框样式
			Enum borderStyle = rightBorderModel.getBorderStyle();
			if (borderStyle != null) {
				rBorder.setVal(borderStyle);
			}
			// 边框粗细
			int size = rightBorderModel.getSize();
			if (size != 0) {
				rBorder.setSz(BigInteger.valueOf(size));
			}
			// 边框颜色
			String color = rightBorderModel.getColor();
			if (color != null) {
				rBorder.setColor(color);
			}
		}

		// 行数据
		List<WordTableRowModel> tableRowModelList = tableModel.getTableRowModelList();

		// 修改每行格式
		modifyCell(table, tableRowModelList);

		// 填充每个单元格数据
		fillInTable(table, tableRowModelList);

//		return table;
	}

	/**
	 * 修改每个单元格的格式的数据，与填充内容，字体等无关
	 * 
	 * @param table
	 * @param tableRowModelList
	 */
	public static void modifyCell(XWPFTable table, List<WordTableRowModel> tableRowModelList) {
		List<XWPFTableRow> rowList = table.getRows();
		for (int i = 0; i < rowList.size(); i++) {
			XWPFTableRow row = rowList.get(i);// 获取行数据
			List<XWPFTableCell> tableCellList = row.getTableCells();// 获取list
			WordTableRowModel tableRowModel = tableRowModelList.get(i);// 获取行模型数据
			List<WordRowCellModel> cellModelList = tableRowModel.getCellModelList();// 获取list

			// 设置行高
			int height = tableRowModel.getHeight();
			if (height != 0) {
				row.setHeight(height);
			}
			for (int j = 0; j < tableCellList.size(); j++) {
				// 这个位置的单元格
				XWPFTableCell cell = tableCellList.get(j);
				// 获取这个位置单元格对应的单元格数据
				WordRowCellModel cellModel = cellModelList.get(j);
				// cttc
				CTTc cttc = cell.getCTTc();
				// ctpr
				CTTcPr ctpr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
				// ctshd
				CTShd ctshd = ctpr.isSetShd() ? ctpr.getShd() : ctpr.addNewShd();

				// 设置单元格宽度
				int width = cellModel.getWidth();
				if (width != 0) {
					(ctpr.isSetTcW() ? ctpr.getTcW() : ctpr.addNewTcW()).setW(BigInteger.valueOf(width));
				}

				// 设置背景色
				String fill = cellModel.getFill();
				if (fill != null) {
					ctshd.setFill(fill);
				}

				// 设置上下对齐
				STVerticalJc.Enum vAlign = cellModel.getvAlign();
				if (vAlign != null) {
					(ctpr.isSetVAlign() ? ctpr.getVAlign() : ctpr.addNewVAlign()).setVal(vAlign);
				}

				// 设置水平对齐
				STJc.Enum hAlign = cellModel.gethAlign();
				if (hAlign != null) {
					// 直接调用0元素可能有问题
					cttc.getPList().get(0).addNewPPr().addNewJc().setVal(hAlign);
				}

			}
		}

	}

	/**
	 * 填充文字到单元格内的方法，与单元格宽度，底色设置等无关
	 * 
	 * @param table
	 * @param cellModelList
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static void fillInTable(XWPFTable table, List<WordTableRowModel> tableRowModelList)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		List<XWPFTableRow> rowList = table.getRows();
		for (int i = 0; i < rowList.size(); i++) {
			XWPFTableRow row = rowList.get(i);// 获取行
			List<XWPFTableCell> tableCellList = row.getTableCells();// 单元格列表
			WordTableRowModel tableRowModel = tableRowModelList.get(i);// 行数据
			List<WordRowCellModel> cellModelList = tableRowModel.getCellModelList();// 行数据list

			for (int j = 0; j < tableCellList.size(); j++) {
				XWPFTableCell cell = tableCellList.get(j);
				// 获取这个位置单元格对应的单元格数据
				WordRowCellModel cellModel = cellModelList.get(j);
				// 这个位置单元格拥有的自然段数量
				List<WordCellParaModel> cellParaList = cellModel.getCellParaList();
				// 在单元格内遍历自然段
				for (int k = 0; k < cellParaList.size(); k++) {
					WordCellParaModel cellParaModel = cellParaList.get(k);// 获取第k个自然段设置的属性
					XWPFParagraph paragraph;
					if (k == 0) {
						// 第一个自然段无需add，默认就有
						paragraph = cell.getParagraphs().get(k);
					} else {
						paragraph = cell.addParagraph();
					}
					XWPFRun sbr = paragraph.createRun();

					// 开始反射动态根据model中的类型设置run的属性
					Class<? extends XWPFRun> c4Run = sbr.getClass();
					Class<? extends WordCellParaModel> c4ParaModel = cellParaModel.getClass();// 获取全部域，根据属性动态调用run的方法

					Field[] fields = c4ParaModel.getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						String name = field.getName();// 获取变量名
						Object value = field.get(cellParaModel);// 若该域的值为null，则不调用该方法
						if (value != null) {
							// 值不为null时才调用方法
							String methodName = "set" + name;// 变量名全弄成双驼峰，方便调用
							Method method = c4Run.getDeclaredMethod(methodName, field.getType());// 通过方法名和变量类型查找方法
							method.invoke(sbr, value);// 调用set方法
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String savepath = "C:/Users/Administrator/Desktop/testoutput.docx";

		XWPFDocument doc = new XWPFDocument();
		int rownum = 3, colnum = 5;

		ArrayList<List<WordRowCellModel>> list = new ArrayList<>();
		for (int i = 0; i < rownum; i++) {
			ArrayList<WordRowCellModel> rowlist = new ArrayList<>();
			for (int j = 0; j < colnum; j++) {
				WordRowCellModel cellModel = new WordRowCellModel();
				ArrayList<WordCellParaModel> paralist = new ArrayList<WordCellParaModel>();
				WordCellParaModel cp1 = new WordCellParaModel();
				cp1.setBold(false);
//				cp1.setColor("cccccc");
				cp1.setFontFamily("黑体");
				cp1.setFontSize(10);
				cp1.setText("第一行");

				WordCellParaModel cp2 = new WordCellParaModel();
				cp2.setBold(true);
//				cp2.setColor("cccccc");
				cp2.setFontFamily("华文行彩");
				cp2.setFontSize(5);
				cp2.setText("第二行");

				paralist.add(cp1);
				paralist.add(cp2);
				cellModel.setCellParaList(paralist);
				rowlist.add(cellModel);
			}`
			list.add(rowlist);
		}

//		createTable(doc, tableModel);

		doc.write(new FileOutputStream(savepath));
		doc.close();
	}

}
