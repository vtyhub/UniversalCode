package swing;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

public class CommonMethod {

	/**
	 * 在component上添加一个右键JPopup菜单
	 * 
	 * @param component
	 * @param popup
	 */
	public static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/**
	 * 批量设置组件可用或不可用的变长方法
	 * @param enable
	 * @param Components
	 */
	public static void setComponentsEnable(boolean enable, JComponent... Components) {
		for (JComponent b : Components) {
			if (b != null) {
				b.setEnabled(enable);
			}
		}
	}

}
