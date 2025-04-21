package poi.word;

import java.util.List;

/**
 * 表格中一行的模型，一行中应该有多个单元格
 * 
 * @author sbr
 *
 */
public class WordTableRowModel {

	// 一行中有多个单元格
	private List<WordRowCellModel> cellModelList;

	//高度
	private int height;

	public List<WordRowCellModel> getCellModelList() {
		return cellModelList;
	}

	public void setCellModelList(List<WordRowCellModel> cellModelList) {
		this.cellModelList = cellModelList;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
