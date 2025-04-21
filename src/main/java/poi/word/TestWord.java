package poi.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class TestWord {

	public static void main(String[] args) throws Exception {
		String openpath = "C:\\Users\\Administrator\\Desktop\\修改制度表.docx",
				savepath = "C:\\Users\\Administrator\\Desktop\\output.docx";
		FileInputStream fin = new FileInputStream(openpath);
		XWPFDocument xwpf = new XWPFDocument(fin);
//		XWPFDocument xwpf = new XWPFDocument(POIXMLDocument.openPackage(openpath));//使用这种方式无法正常关闭
		
		// 获取文档内全部表格
		List<XWPFTable> tables = xwpf.getTables();
		// 获取文档内全部段落，一共五个段落，获取不到表格内容
		List<XWPFParagraph> paragraphs = xwpf.getParagraphs();
		// 获取文档内全部图片
		List<XWPFPictureData> allPictures = xwpf.getAllPictures();
//		for (XWPFParagraph paragraph : paragraphs) {
//			System.out.println(++i + "line");
//			System.out.println(paragraph.getText());
//			System.out.println(paragraph.getFontAlignment());
//			System.out.println(paragraph.getStyle());
//			System.out.println(paragraph.getNumID());
//		}
		int i = 0;
		for (XWPFTable table : tables) {
			// 获取一张表格内的全部行模型
			List<XWPFTableRow> rows = table.getRows();
			table.getCTTbl().getTblPr().addNewTblBorders();//设置边框的方式
			for (int j = 0; j < rows.size(); j++) {
				XWPFTableRow row = rows.get(j);
				
				// 获取一行内的全部单元格，类似于通过行-列来遍历
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					// 获取一个单元格内的段落，模板中一个单元格有两个自然段
					List<XWPFParagraph> parags = cell.getParagraphs();
					for (XWPFParagraph para : parags) {
						// 获取一个段落内的全部sbr
						List<XWPFRun> runs = para.getRuns();
						for (int k = 0; k < runs.size(); k++) {
							XWPFRun run = runs.get(k);
							String runtext = run.text();
							if (!runtext.contains("#")) {
								continue;
							}

							runtext = runtext.replace("#{title}", "测试文件1");
							runtext = runtext.replace("#{abs}", "摘要1");
							runtext = runtext.replace("#{dept}", "部门");
							runtext = runtext.replace("#{apply}", "申请人1");
							para.removeRun(k);
							para.insertNewRun(k).setText(runtext);
							
//							run.setText(celltext);// set是指在后面追加的意思？
						}
					}

//					cell.setText(celltext);//木大，只有run能修改
					cell.getText();
					System.out.println(++i + "lines: " + cell.getText());
				}

			}
			System.out.println(table.getText());
		}

		FileOutputStream fout = new FileOutputStream(savepath);
		xwpf.write(fout);
		fout.close();
		fin.close();
		xwpf.close();
		System.out.println("finished");
	}
}
