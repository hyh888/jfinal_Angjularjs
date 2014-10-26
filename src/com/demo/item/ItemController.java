package com.demo.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.util.excelHelper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

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

	public void uploadExcel() {
		excelHelper im = new excelHelper();
		//im.readExcel("d:/123.xls");
		im.readExcel("d:/items.xls");
		setAttr("items", Item.dao.findAll());
		renderJson();
	}
	
	public void batchCrud() {
		String myRs= "{'params':" + getPara("params") + "}";
	      // 将字符串转为json对象，使用fastjson
      JSONObject obj = JSON.parseObject(myRs);
     // Object[] items=null;
      JSONArray items;
      try{
    	   items = obj.getJSONArray("params");
          for (int idx= 0; idx < items.size(); idx++) {
        	  Integer myId = items.getJSONObject(idx).getInteger("id");
        	  String myTitle = items.getJSONObject(idx).getString("title");
        	  String myDes = items.getJSONObject(idx).getString("des");
        	  String myStatus = items.getJSONObject(idx).getString("myStatus");
        	  System.out.println("id:"+myId);
        	  System.out.println("title:"+myTitle);
        	  System.out.println("Descripton:"+myDes);
        	  System.out.println("myStatus:"+myStatus);
        	  System.out.println("Record "+ idx +"----");
        	  if (myStatus.equals("D")){
        		  Db.deleteById("item", items.getJSONObject(idx).getInteger("id"));
        	  }        	  
        	  if (myStatus.equals("U")){
        		  if(myId!=null){
        			  System.out.println("进入update");
        			  Item.dao.findById(myId).set("des", myDes).set("title", myTitle).update();
		        	  }
        		  else{
        			  System.out.println("进入New");
		        	  Record item = new Record().set("des", myDes).set("title", myTitle);
		        	  Db.save("item", item);        			  

        		  }
        		  
        	  }

        	  
          }
      }catch(NullPointerException ne){
    	  items=null;
    	 // items=new Object[]{};
      }
		//redirect("/");
		setAttr("items", Item.dao.findAll());
		renderJson();
	}

	public void createExcel() {
		excelHelper im = new excelHelper();
		im.CreateSimpleExcelToDisk();
		renderText("OK");
	}
	public void delete() {
		Item.dao.deleteById(getParaToInt());
		redirect("/");
	}
}


