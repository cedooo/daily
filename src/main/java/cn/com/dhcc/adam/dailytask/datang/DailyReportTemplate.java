package cn.com.dhcc.adam.dailytask.datang;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * 中国大唐集团公司信息系统运行日总结报告 模版
 * @author cedo
 *
 */
public class DailyReportTemplate implements IReportTemplate {
	public DailyReportTemplate() {
		build();
	}

	private void build() {
		try {
			Map<String,String> contents = new HashMap<String, String>();
			String contentsTitleOne = "（二）网络设备运行情况";
			String contentsOne = 
			"1.集团公司骨干广域网路由器共30台；设备运行率100%。目前重点监测路由器20台，CPU平均使用率最大的路由器为R_B_JL_CCRL；使用率8.5%值。与上级交互平均流量最大的路由器为RBJLCCRL核心路由器；流量6M. \n" + 
			"2.集团公司总部主核心交换机运行基本正常， CPU使用率为7%，（通常应在10%以下），到骨干广域网流量平均值为1.  ，最大值为6M 。" + 
			"集团公司总部备用核心交换机 7%;到骨干广域网流量最大6M；平均流量0.8M 。\n";
			contents.put(contentsTitleOne, contentsOne);
			JasperReportBuilder report=DynamicReports.report();
			report.title(title("中国大唐集团公司信息系统运行日总结报告"))
					.columnHeader(
							overview("11月20日00:00-24:00", "本期，集团公司信息系统总体运行状况稳定/略有提升/略有下降，系统安全状况正常/基本正常，未发生/发生 0 次重大异常事件。")
					)
					.columnHeader(reportParagraph("三、广域网链路与设备运行监测", contents))
					//.pageHeader(pageHeader()) 
					.pageFooter(pageFooter())
					.show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 报告标题
	 * @param title 标题
	 * @return
	 */
	public TextFieldBuilder<String>  title(String title){
		StyleBuilder titleStyle = stl.style().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE).setFontSize(22).bold().setPadding(16);
		return cmp.text(title).setStyle(titleStyle);
	}
	/**
	 * 概要
	 * @param datatime 内容时间
	 * @param details 内容详细
	 * @return TextFieldBuilder 2维数组
	 */
	public TextFieldBuilder<String>[] overview(String datatime, String details){
		@SuppressWarnings("unchecked")
		TextFieldBuilder<String>[] tfba = new TextFieldBuilder[2];
		StyleBuilder datatimeStyle =stl.style().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE).setFontSize(16).setPadding(16);
		tfba[0] = cmp.text(datatime).setStyle(datatimeStyle);
		
		StyleBuilder detailsStyle = stl.style().setFontSize(16).setFirstLineIndent(2*16);
		tfba[1] = cmp.text(details).setStyle(detailsStyle);
		
		return tfba;
	}
	
	public ComponentBuilder<?, ?>[] reportParagraph(String paTitle, Map<String, String> contents){
		ComponentBuilder<?, ?>[] compoentBuilder = new ComponentBuilder<?, ?>[contents.size()+1];
		int counter = 0;
		StyleBuilder paTitleStyle =stl.style().setAlignment(HorizontalAlignment.LEFT, VerticalAlignment.MIDDLE).setFontSize(14).bold();
		compoentBuilder[counter++] = cmp.text(paTitle).setStyle(paTitleStyle);
		
		StyleBuilder detailsStyle = stl.style().setFontSize(16).setFirstLineIndent(2*14);
		for(String key: contents.keySet()){
			compoentBuilder[counter++] = cmp.text(contents.get(key)).setStyle(detailsStyle);
		}
		return compoentBuilder;
	}

	public ComponentBuilder<?, ?> pageHeader(){
		StyleBuilder headerStyle =stl.style().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE).setFontSize(10)
				.setForegroundColor(Color.LIGHT_GRAY);
		return cmp.text ("©东华软件").setStyle(headerStyle);
	}
	public ComponentBuilder<?, ?> pageFooter(){
		StyleBuilder footerStyle =stl.style().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE).setFontSize(10)
				.setForegroundColor(Color.LIGHT_GRAY);
		return cmp.text ("©东华软件").setStyle(footerStyle);
	}
	public static void main(String[] args) {
		new DailyReportTemplate();
	}
}
