package com.demo.item;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * BlogValidator.
 */
public class ItemValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateRequiredString("item.title", "titleMsg", "请输入item标题!");
		validateRequiredString("item.des", "desMsg", "请输入item内容!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Item.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/item/save"))
			controller.render("add.jsp");
		else if (actionKey.equals("/item/update"))
			controller.render("edit.jsp");
	}
}
