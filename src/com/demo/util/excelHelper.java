package com.demo.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.demo.item.Item;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class excelHelper {

	@SuppressWarnings("deprecation")
	public void readExcel(String filePath) {

		try {

			Workbook workBook = null;
			FileInputStream in = new FileInputStream(filePath);
			try {
				workBook = new XSSFWorkbook(filePath); // 支持2007
			} catch (Exception ex) {
				workBook = new HSSFWorkbook(in); // 支持2003及以前
			}

			// 获得Excel中工作表个数
			System.out.println("工作表个数 :" + workBook.getNumberOfSheets());

			// 循环每个工作表
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				// 创建工作表
				Sheet sheet = workBook.getSheetAt(i);

				int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
				if (rows > 0) {
					sheet.getMargin(Sheet.TopMargin);
					for (int r = 1; r < rows; r++) { // 行循环
						Row row = sheet.getRow(r);
						String myTitle="",myDes="";
						if (row != null) {
							Cell cell = row.getCell(0);
							if (cell != null) {

							}
							 cell = row.getCell(1);
							if (cell != null) {
								myDes = cell.getRichStringCellValue().toString();
							};

							cell = row.getCell(2);
							if (cell != null) {
								myTitle = cell.getRichStringCellValue().toString();
							};
							
							Record item = new Record().set("des", myDes).set("title", myTitle);
							Db.save("item", item);   
						
						}
					}
				}

				new FileInputStream(filePath).close();

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getValue(Cell cell) {

		String value = "";
		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_NUMERIC: // 数值型
			if (DateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				value = DateUtil.getJavaDate(cell.getNumericCellValue())
						.toString();
			} else {// 纯数字
				value = String.valueOf(cell.getNumericCellValue());
			}
			break;
		/* 此行表示单元格的内容为string类型 */
		case Cell.CELL_TYPE_STRING: // 字符串型
			value = cell.getRichStringCellValue().toString();
			break;
		case Cell.CELL_TYPE_FORMULA:// 公式型
			// 读公式计算值
			value = String.valueOf(cell.getNumericCellValue());
			if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
				value = cell.getRichStringCellValue().toString();
			}
			// cell.getCellFormula();读公式
			break;
		case Cell.CELL_TYPE_BOOLEAN:// 布尔
			value = " " + cell.getBooleanCellValue();
			break;
		/* 此行表示该单元格值为空 */
		case Cell.CELL_TYPE_BLANK: // 空值
			value = "";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			value = "";
			break;
		default:
			value = cell.getRichStringCellValue().toString();
		}

		return value;
	}

	@SuppressWarnings("deprecation")
	public void CreateSimpleExcelToDisk() {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("学生表一");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("ID");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("Description");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("Title");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("生日");
		cell.setCellStyle(style);

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		// List list = CreateSimpleExcelToDisk.getStudent();
		List<Item> itemList = Item.dao.findAll();
		for (int i = 0; i < itemList.size(); i++) {
			row = sheet.createRow((int) i + 1);
			Item item = (Item) itemList.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue((double) item.getInt("id"));
			row.createCell((short) 1).setCellValue(item.getStr("des"));
			row.createCell((short) 2).setCellValue(item.getStr("title"));
			// cell = row.createCell((short) 3);
			// cell.setCellValue(new
			// SimpleDateFormat("yyyy-mm-dd").format(stu.getBirth()));

		}
		// 第六步，将文件存到指定位置
		try {
			FileOutputStream fout = new FileOutputStream("D:/items.xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		excelHelper im = new excelHelper();

		// im.readExcel("d:/123.xls");
		// im.readExcel("d:/221.xlsx");
		// im.readExcel("F:/2007.xls"); //2007下保存为2003
		im.CreateSimpleExcelToDisk();

	}

}
