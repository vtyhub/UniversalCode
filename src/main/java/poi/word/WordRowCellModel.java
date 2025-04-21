package poi.word;

import java.util.List;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class WordRowCellModel {

	// 一个单元格内的全部自然段模型
	private List<WordCellParaModel> cellParaList;

	// 背景色
	private String fill;

	// 单元格宽度
	private int width;

	// 表格行高度
	private int heigth;

	// 上下对齐方式
	private STVerticalJc.Enum vAlign;

	// 水平对齐方式
	private STJc.Enum hAlign;

	public List<WordCellParaModel> getCellParaList() {
		return cellParaList;
	}

	public void setCellParaList(List<WordCellParaModel> cellParaList) {
		this.cellParaList = cellParaList;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public STVerticalJc.Enum getvAlign() {
		return vAlign;
	}

	public void setvAlign(STVerticalJc.Enum vAlign) {
		this.vAlign = vAlign;
	}

	public STJc.Enum gethAlign() {
		return hAlign;
	}

	public void sethAlign(STJc.Enum hAlign) {
		this.hAlign = hAlign;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}
	
}
