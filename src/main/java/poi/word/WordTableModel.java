package poi.word;

import java.util.List;


public class WordTableModel {

	// 行数
	private int rowCount;

	// 列数
	private int colCount;

	// 表格拥有的所有行
	private List<WordTableRowModel> tableRowModelList;

	// 表格对齐方式

	// 表格上外边框样式
	private WordTableBorderModel topBorderModel;

	// 表格下外边框样式
	private WordTableBorderModel bottomBorderModel;

	// 表格左外边框样式
	private WordTableBorderModel leftBorderModel;

	// 表格右外边框样式
	private WordTableBorderModel rightBorderModel;

	public List<WordTableRowModel> getTableRowModelList() {
		return tableRowModelList;
	}

	public void setTableRowModelList(List<WordTableRowModel> tableRowModelList) {
		this.tableRowModelList = tableRowModelList;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getColCount() {
		return colCount;
	}

	public void setColCount(int colCount) {
		this.colCount = colCount;
	}

	public WordTableBorderModel getTopBorderModel() {
		return topBorderModel;
	}

	public void setTopBorderModel(WordTableBorderModel topBorderModel) {
		this.topBorderModel = topBorderModel;
	}

	public WordTableBorderModel getBottomBorderModel() {
		return bottomBorderModel;
	}

	public void setBottomBorderModel(WordTableBorderModel bottomBorderModel) {
		this.bottomBorderModel = bottomBorderModel;
	}

	public WordTableBorderModel getLeftBorderModel() {
		return leftBorderModel;
	}

	public void setLeftBorderModel(WordTableBorderModel leftBorderModel) {
		this.leftBorderModel = leftBorderModel;
	}

	public WordTableBorderModel getRightBorderModel() {
		return rightBorderModel;
	}

	public void setRightBorderModel(WordTableBorderModel rightBorderModel) {
		this.rightBorderModel = rightBorderModel;
	}

}
