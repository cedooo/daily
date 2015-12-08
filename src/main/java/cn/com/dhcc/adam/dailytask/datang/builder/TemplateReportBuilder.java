package cn.com.dhcc.adam.dailytask.datang.builder;

import java.util.ArrayList;
import java.util.List;

import net.sf.dynamicreports.report.exception.DRException;
import cn.com.dhcc.adam.dailytask.datang.query.ReportQuery;
import cn.com.dhcc.adam.dailytask.datang.tools.TxtReportTemplate;
/**
 * 模版报表builder
 * @author cedo
 *
 */
public class TemplateReportBuilder extends AbstractReportBuilder {
	public TemplateReportBuilder(String templatePath, int type){
		reportQuery = new ReportQuery(type);

		List<String> template = new TxtReportTemplate().getReportString(templatePath);
		if(template!=null){
			title = reportPart(template.get(0));
			dateTime = reportPart(template.get(1));
			overView = reportPart(template.get(2));
			List<String> listContent = template.subList(3, template.size());
			String contentsTitle = null;
			List<String> strBuilder = new ArrayList<String>();
			for (String string : listContent) {
				if(string.matches("^[一二三四五六七八九十]、.*$")){
	//System.out.println(string);
					if(contentsTitle!=null&& strBuilder!=null){
						List<String> listReportPart = new ArrayList<String>();
						for (String paragraph : strBuilder) {
							String contentStr = reportPart(paragraph);
							listReportPart.add(contentStr);
						}
						contents.put(contentsTitle, listReportPart);
					}
					contentsTitle = string;
					strBuilder.clear();
				}else{
					strBuilder.add(string);
				}
				
			}
	
			if(contentsTitle!=null&& strBuilder!=null){
				List<String> listReportPart = new ArrayList<String>();
				for (String paragraph : strBuilder) {
					String contentStr = reportPart(paragraph);
					listReportPart.add(contentStr);
				}
				contents.put(contentsTitle, listReportPart);
			}
		}else{
			try {
				throw new Exception("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new TemplateReportBuilder( "src/main/java/cn/com/dhcc/adam/dailytask/datang/tools/template.txt",1).build().show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
}
