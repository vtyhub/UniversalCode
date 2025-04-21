package poi.word;

public class WordCellParaModel {

	//该单元格内自然段的内容
	private String Text;
	
	//字体名称
	private String FontFamily;
	
	//字体大小
	private int FontSize;
	
	//字体颜色
	private String Color;
	
	//是否加粗
	private boolean Bold;

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getFontFamily() {
		return FontFamily;
	}

	public void setFontFamily(String fontFamily) {
		FontFamily = fontFamily;
	}

	public int getFontSize() {
		return FontSize;
	}

	public void setFontSize(int fontSize) {
		FontSize = fontSize;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public boolean isBold() {
		return Bold;
	}

	public void setBold(boolean bold) {
		Bold = bold;
	}
	
}
