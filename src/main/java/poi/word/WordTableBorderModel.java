package poi.word;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;

public class WordTableBorderModel {

	// 表格边框样式
	private STBorder.Enum borderStyle;
	
	//表格边框大小
	private int size;

	//表格边框颜色
	private String color;

	public STBorder.Enum getBorderStyle() {
		return borderStyle;
	}

	public void setBorderStyle(STBorder.Enum borderStyle) {
		this.borderStyle = borderStyle;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
