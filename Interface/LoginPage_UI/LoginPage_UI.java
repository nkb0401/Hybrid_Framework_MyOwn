package LoginPage_UI;

public class LoginPage_UI {
	public static final String MENU_LINK_BY_NAME = "//ul[@role='menu']/li/a/p[contains(text(),'%s')]";
	public static final String SUBMENU_LINK_BY_NAME = "//ul[@class='nav nav-treeview' and @style]//p[contains(text(),'%s')]";
	public static final String UPLOAD_BY_CARDNAME= "//div[@id='product-%s']//input[@type='file']";
	// public: phạm vi truy cập ngoài package
	// static: biến tĩnh - cho phép 1 class khác có thể sử dụng biến mà ko cần khởi tạo đối tượng của class này lên
	// final: ngan cản gắn giá trị cho biến này
}
