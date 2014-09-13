package com.demo.item;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * BlogController
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(ItemInterceptor.class)
public class ItemController extends Controller {
	
	public void index() {
		setAttr("items", Item.dao.findAll());
		renderJson();

	}
	
	public void add() {
	}
	
	@Before(ItemValidator.class)
	public void save() {
		getModel(Item.class).save();
		redirect("/");
	}
	
	public void edit() {
		setAttr("item", Item.dao.findById(getParaToInt()));
	}
	
	@Before(ItemValidator.class)
	public void update() {
		getModel(Item.class).update();
		redirect("/");
	}
	
	//public void batchCrud() {
		//Item.dao.deleteById(getParaToJson());
		//redirect("/");
	//}
	public void delete() {
		Item.dao.deleteById(getParaToInt());
		redirect("/");
	}
}


