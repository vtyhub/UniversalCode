package constant;

import java.io.File;
import javax.swing.filechooser.FileSystemView;


public interface SysConstant {
	FileSystemView FSV = FileSystemView.getFileSystemView();

	File DESKTOPPATH = FSV.getHomeDirectory();
	// String DESKTOPPATH = System.getProperty("user.home");

	File DOCUMENTPATH = FSV.getDefaultDirectory();

	String OSNAME = System.getProperty("os.name");
	

}
