package poi.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;


public class TestWordTable {

	public static void main(String[] args) throws Exception {
		String openpath = "C:\\Users\\Administrator\\Desktop\\empty.docx",
				savepath = "C:\\Users\\Administrator\\Desktop\\emptysave.docx";
		FileInputStream fin = new FileInputStream(openpath);

//		XWPFDocument docx = new XWPFDocument(fin);
		XWPFDocument docx = new XWPFDocument(OPCPackage.open(openpath));
		int rows = 3, cols = 5;
		XWPFTable table = docx.createTable(rows, cols);
		CTTbl ttbl = table.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();

		CTTblWidth tblW = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		tblW.setW(new BigInteger("8000"));
		tblW.setType(STTblWidth.DXA);
		CTJc jc = tblPr.addNewJc();
		jc.setVal(STJc.CENTER);

		
		CTTblBorders borders = tblPr.getTblBorders();
//		CTBorder hBorder = borders.getInsideH();//水平内边框
//		hBorder.setVal(STBorder.Enum.forString("double"));  // 线条类型
//		hBorder.setSz(new BigInteger("1")); // 线条大小
//		hBorder.setColor("000000"); // 设置颜色

		CTBorder lBorder = borders.addNewLeft();//左外边框
		lBorder.setVal(STBorder.DOUBLE);
		lBorder.setSz(new BigInteger("15"));
		lBorder.setColor("000000");
		
		CTBorder rBorder = borders.addNewRight();//右外边框
		rBorder.setVal(STBorder.Enum.forString("double"));
		rBorder.setSz(new BigInteger("15"));
		rBorder.setColor("000000");

		CTBorder tBorder = borders.addNewTop();//顶外边框
		tBorder.setVal(STBorder.Enum.forString("double"));
		tBorder.setSz(new BigInteger("15"));
		tBorder.setColor("000000");

		CTBorder bBorder = borders.addNewBottom();//下外边框
		
		bBorder.setVal(STBorder.Enum.forString("double"));
		bBorder.setSz(new BigInteger("15"));
		bBorder.setColor("000000");
		
		List<XWPFTableRow> rowlist = table.getRows();
		int height = 0;

		for (XWPFTableRow row : rowlist) {
			row.setHeight(height);
//			height += 200;
			List<XWPFTableCell> celllist = row.getTableCells();
			for (XWPFTableCell cell : celllist) {
				XWPFParagraph firstpara = cell.getParagraphs().get(0);// 第一个自然段
				XWPFRun firstrun = firstpara.createRun();
				firstrun.setText("杀皇killerqueen");
				firstrun.setFontFamily("华文彩云");
				firstrun.setFontSize(10);
				firstrun.setColor("123456");
				firstrun.setBold(true);// 加粗

				XWPFParagraph cellpara = cell.addParagraph();// 默认有一个自然段，加上的是第二个，但是即便加上了run也不会增加
//				System.out.println(cell.getParagraphArray(1));
				XWPFRun run = cellpara.createRun();
				run.setText("2天堂制造made in heavens");
				run.setFontSize(10);
				run.setColor("ff0302");

				// 设置字体的方式1
//				run.setFontFamily("华文琥珀");

				// 设置字体的方式2
				CTR ctr = run.getCTR();
				CTFonts rfonts = ctr.addNewRPr().addNewRFonts();
				rfonts.setEastAsia("幼圆");
				rfonts.setAscii("方正姚体");
				rfonts.setCs("仿宋");
				rfonts.setHAnsi("楷体");

				// 颜色填充
				CTTc cttc = cell.getCTTc();
				CTTcPr ctPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
				CTShd ctshd = ctPr.isSetShd() ? ctPr.getShd() : ctPr.addNewShd();
				// 设置单元格宽度
//				ctPr.addNewTcW().setW(BigInteger.valueOf(900));
				// 设置背景色
				ctshd.setFill("cccccc");
				// 设置上下对齐
				ctPr.addNewVAlign().setVal(VAlignEnum.getEnumByCode(1));
				// 设置左右对齐
				cttc.getPList().get(0).addNewPPr().addNewJc().setVal(HAlignEnum.getEnumByCode(1));
				// 设置单元格显示内容
				cell.setText("1");
				cell.setText("7");
				cell.setText("4");// set是默认在第一个自然段添加，且不受第一个自然段run设置的字体影响

			}
		}

		FileOutputStream fout = new FileOutputStream(savepath);
		docx.write(fout);

	}
}
