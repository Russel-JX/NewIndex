package com.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.common.FormulaStatic;
import com.pojo.ZbData;
import com.pojo.ZbDetailData;
import com.service.ThreeQueryThreeDataService;

/**
 *各种具体指标值统计计算 
 */
public class ThreeQueryThreeDataServiceImpl implements ThreeQueryThreeDataService{
	/**
	 *判断哪个三级指标
	 */
	/*
	 * 当一下情况时，得分统计一列为“未统计得分”
	 * 1.所占分数或权重存在null或空字符时
	 * 2.三级指标的公式字段为null或-1
	 * 当具体指标值没填时（为null或空字符），为满分
	 */
	public List<ZbDetailData> defineThreeIndex(List<ZbDetailData> items_l3_data){
		for(int i=0;i<items_l3_data.size();i++){
			//某项三级指标月度总分
			double sum = 0.0;
			//判断是否有所占分数和权重
			if((items_l3_data.get(i).getZbPoint()!=null)&&(items_l3_data.get(i).getZbWeight()!=null)){
				//截取字符串中的连续数字
				double target = getNumberFromString(items_l3_data.get(i).getZbTarget());
				if(target==-1){
					System.out.println("指标目标值中没有数字，无法转换！！！");
					items_l3_data.get(i).setResult("指标目标值不正确，无法计算得分！");
					continue;
				}
				//选择公式
				switch(items_l3_data.get(i).getZbFormula()){
					case FormulaStatic.TXYWTDKYL:sum = formatTXYWTDJYL(items_l3_data.get(i).getData(),target);		break;
					case FormulaStatic.XXXTPJFJHTYSC:sum = formatXXXTPJFJHTYSC(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXXTPJFJHTYCS:sum = formatXXXTPJFJHTYCS(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXWLPJFJHTYSC:sum = formatXXWLPJFJHTYSC(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXWLPJFJHTYCS:sum = formatXXWLPJFJHTYCS(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.ZBFZGXPJFJHZDSC:sum = formatZBFZGXPJFJHZDSC(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.QYMHGTBKYSC:sum = formatQYMHGTBKYSC(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.YWYYGTBKYSC:sum = formatYWYYGTBKYSC(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.NWWWZWXLJS:sum = formatNWWWZWXLJS(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXXTPJXYSC:sum = formatXXXTPJXYSC(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXTXXXDDLXDCLSC:try {
						sum = formatXXTXXXDDLXDCLSC(items_l3_data.get(i).getData(),target);
					} catch (ParseException e) {
						e.printStackTrace();
					}break;
					case FormulaStatic.XXTXXXDDZLZXHGL:sum = formatXXTXXXDDZLZXHGL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXTXXXDDZBBHGSL:sum = formatXXTXXXDDZBBHGSL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXXXYDLPBHGS:sum = formatXXXXYDLPBHGS(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXTXSBTZHGL:sum = formatXXTXSBTZHGL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXSBJZZDJKK:sum = formatXXSBJZZDJKK(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXXXQXJSXQL:sum = formatXXXXQXJSXQL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXTXXXJXZXHGL:sum = formatXXTXXXJXZXHGL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXXXLSJXL:sum = formatXXXXLSJXL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXXXPJJHJXSC:sum = formatXXXXPJJHJXSC(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXXXYJXTZL:sum = formatXXXXYJXTZL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXXXCFJXCS:sum = formatXXXXCFJXCS(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.XXXTJXJHSPYCTGL:sum = formatXXXTJXJHSPYCTGL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.SJKFZFJHZDCLBHGCS:sum = formatSJKFZFJHZDCLBHGCS(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.GDCLJSL:sum = formatGDCLJSL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.DHQHL:sum = formatDHQHL(items_l3_data.get(i).getData(),target);break;
					case FormulaStatic.DHJSJTL:sum = formatDHJSJTL(items_l3_data.get(i).getData(),target);break;
					
					//不用任何公式
					default:sum = defaultFormula();	break;
				}
				String result = calculatePointsByMonth(items_l3_data.get(i).getZbPoint(),items_l3_data.get(i).getZbWeight(),sum,items_l3_data.get(i).getData().size());
				items_l3_data.get(i).setResult(result);
				System.out.println("指标名称是： "+items_l3_data.get(i).getZbName());
				System.out.println("________________________________");
			}else{
				items_l3_data.get(i).setResult("未统计得分");
			}
			
		}
		return items_l3_data;
	}
	/**
	 * 默认执行的公式
	 */
	public double defaultFormula(){
		return -1;
	}
	/**
	 * 计算该月所得总分
	 * param acountPoint	所占分数
	 * param weight	权重
	 * param sum	所有指标分数总和
	 * param dataNum	该月指标值个数
	 */
	public String calculatePointsByMonth(Double acountPoint ,Double weight ,double sum,int dataNum){
		if(sum==-1){
			return "未统计得分";
		}
		
		//double acountPoint_double = Double.parseDouble(acountPoint);
		//double weight_double = Double.parseDouble(weight);
		
		//每一分占所占分数的比例
		if(dataNum==0){//如果该月这个指标没有数据，即dataNum为0
			return "本月指标数据值不存在！";
		}
		double rate = (acountPoint* weight)/(dataNum*100);
		
		System.out.println("所占分数是： "+acountPoint+"权重："+weight+"总和是; "+sum+"个数是： "+dataNum+"比率是： "+rate);
		System.out.println("+++++++++++++++++++++最终得分是： "+ rate*sum);
		//最后得分
		Double finalpoint = rate*sum;
		
		//保留小数点后四位
		BigDecimal bd = new BigDecimal(finalpoint);
		Double finalpoint_format = bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("保留两位小数后： "+finalpoint_format);
		
		return (finalpoint_format.toString());
	}
	/**
	 * 截取字符串中的连续数字
	 */
	public double getNumberFromString(String target){
		//指标目标值为null或空字符串，直接返回
		if(target==null||target.equalsIgnoreCase("")){
			return -1;
		}
		char[] target_array = target.toCharArray();
		int beginIndex = 0;
		int endIndex = 0;
		int length = target_array.length;
		
		int count = 0;
		for (int i = 0; i < length; i++) {
			if (target_array[i] == '0' || target_array[i] == '1'
					|| target_array[i] == '2' || target_array[i] == '5'
					|| target_array[i] == '4' || target_array[i] == '3'
					|| target_array[i] == '6' || target_array[i] == '7'
					|| target_array[i] == '8' || target_array[i] == '9') {
				beginIndex = i;
				break;
			}
			count++;
		}
		if(count==length){
			System.out.println("——————————————————————————————————————————————目标值里没有数字！！");
			return -1;
		}
		for (int i = length - 1; i >= 0; i--) {
			if (target_array[i] == '0' || target_array[i] == '1'
					|| target_array[i] == '2' || target_array[i] == '5'
					|| target_array[i] == '4' || target_array[i] == '3'
					|| target_array[i] == '6' || target_array[i] == '7'
					|| target_array[i] == '8' || target_array[i] == '9') {
				endIndex = i;
				break;
			}
		}
		
		// System.out.println("数字的开头索引是: "+beginIndex+" 数字的末尾索引是: "+endIndex);

		String target_sub = target.substring(beginIndex, endIndex + 1);
		Double target_double = -1.0;
		try {
			target_double = Double.parseDouble(target_sub);
		} catch (Exception e) {
			System.out.println("第一个数字和最后一个数字之间的内容不是数字，返回-1！");
			return -1;
		}
		

		return target_double;
	}
	/**
	 * 截取逗号前后的字符串，作为数字
	 */
	public static double[]  getNumberBeforeAfterComma(String str) throws Exception{
		double[] numberBack = {-1.0,-1.0};
		//可能会是中英文的逗号
		int index_of_comma = str.indexOf(",")==-1?str.indexOf("，"):str.indexOf(",");
		//没有逗号时，返回两个-1.0
		if(index_of_comma==-1){
			return numberBack;
		}
		String firstNumber_string = str.substring(0, index_of_comma);
		String secondNumber_string = str.substring((index_of_comma+1),str.length());
		
		//类型转换异常时，返回-1.0
		double firstNumber = -1.0;
		double secondNumber = -1.0;
		try {
			firstNumber = Double.parseDouble(firstNumber_string);
			secondNumber = Double.parseDouble(secondNumber_string);
		} catch (Exception e) {
			System.out.println("字符串转double发生异常!");
		}  
		
		numberBack[0] = firstNumber;
		numberBack[1] = secondNumber;
		
		return numberBack;
	}
	
	/**
	 *1.通信业务通道可用率
	 *说明：格式化具体指标值(大于99.999%，分数为100；每小于99.999%0.0001%分，在原有基础上减3分)
	 *数据单位：百分比“%”
	 *@param datas	某项指标月度原始数据
	 *@param target	指标目标值（达到目标值为满分）
	 */
	public double formatTXYWTDJYL(List<ZbData> datas,double target){
		//本月所有指标分数总和
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			//数据没有填写（为null或空字符），为满分
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				//如果不是带百分号的数据，格式不正确也为满分
				if(index_of_percentage==-1){
					temp = 100;
				}else{//带百分号
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						//数据格式不对，也是满分
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					} 
					System.out.println("原字符串："+data_substring+"Double： "+data_double);
					
					if(data_double>=target){
						temp = 100;
					}else{
						temp = (100-(99.999-data_double)*1000*3>0)?(100-(99.999-data_double)*1000*3):0;//每小于99.999%0.0001%分，在原有基础上减3分
					}
				}
			}
			sum+=temp;
			//System.out.println("此天得分： "+temp);
		}
		//System.out.println("这个月的总得分累加是： "+sum);
		return sum;
	}
	/**
	 *2.信息系统平均非计划停运时长
	 *说明：（1）当平均非计划停运时长为0得分为100分，
	 *（2）当不为0时，每增加0.6分钟，扣5分，平均时长超过12分钟为0分。
	 *数据单位：分钟
	 */
	public double formatXXXTPJFJHTYSC(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double==target){
					temp = 100;
				}else{
					temp = 100-(data_double/0.6)*5;
				}
				if(data_double>12){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *3.信息系统平均非计划停运次数
	 *说明：（1）当平均非计划停运次数为0得分为100分，
     *（2）当不为0时，每增加0.05次，扣20分，平均次数超过0.25次为0分。
	 *数据单位：次
	 */
	public double formatXXXTPJFJHTYCS(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double==target){
					temp = 100;
				}else{
					temp = 100-(data_double/0.05)*20;//这里向下取整，具体问负责人怎么取
				}
				if(data_double>0.25){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *4.信息网络平均非计划停运时长
	 *说明：（1）当平均非计划停运时长为0分钟得分为100分，
	 *（2）当不为0分钟时，每增加0.6分钟，扣5分，平均时长超过12分钟为0分。
	 *数据单位：分钟
	 */
	public double formatXXWLPJFJHTYSC(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double==target){
					temp = 100;
				}else{
					temp = 100-(data_double/0.6)*5;//这里向下取整，具体问负责人怎么取
				}
				if(data_double>12){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *5.信息网络平均非计划停运次数
	 *说明：（1）当平均非计划停运次数为0次时得分为100分，
	 *（2）当不为0时，每增加0.05次，扣20分，平均次数超过0.25次为0分。
	 *数据单位：次
	 */
	public double formatXXWLPJFJHTYCS(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double==target){
					temp = 100;
				}else{
					temp = 100-(data_double/0.05)*20;//这里向下取整，具体问负责人怎么取
				}
				if(data_double>0.25){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *6.灾备复制关系平均非计划中断时长
	 *说明：（1）当灾备复制平均非计划中断时长不超过1小时为100分，超过10小时为0分。
	 *（2）当灾备复制平均非计划中断时长超过1小时少于10小时，采用线性扣分。
	 *数据单位：时
	 */
	public double formatZBFZGXPJFJHZDSC(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double<=target){
					temp = 100;
				}else{
					temp = 100-(data_double)*10;//这里向下取整，具体问负责人怎么取
				}
				if(data_double>10){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *7.企业门户贯通不可用时长
	 *（1）当不可用时长超过10小时为0分
	 *（2）当不超过10小时，每增加0.1小时扣1分。
	 *数据单位：时
	 */
	public double formatQYMHGTBKYSC(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double>10){
					temp = 0.0;
				}else{
					temp = 100-(data_double/0.1)*1;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *8.业务应用贯通不可用时长
	 *（1）当不可用时长超过10小时为0分
	 *（2）当不超过10小时，每增加0.1小时扣1分。
	 *数据单位：时
	 */
	public double formatYWYYGTBKYSC(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double>10){
					temp = 0.0;
				}else{
					temp = 100-(data_double/0.1)*1;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *9.内外网网站无效链接数
	 *内外网网站无效链接数为0时得分100分，每发现1个无效链接扣5分，超过20个为0分。
	 *数据单位：个
	 */
	public double formatNWWWZWXLJS(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				temp = 100-data_double*5;
				if(data_double>20){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *10.信息系统平均响应时长
	 *if（平均响应时长<=0.2秒，100，if（平均响应时长>=3秒，0，100-（平均响应时长-0.2）/2.8*100))
	 *数据单位：秒
	 */
	public double formatXXXTPJXYSC(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double<=target){
					temp = 100;
				}else{
					temp = 100-(data_double-0.2)/0.028;
				}
				if(data_double>3){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 * 根据日期判断具体指标值属于星期几，只用于“信息通信系统调度联系单处理时长”
	 * 0：周日，6：周六。
	 */
	public static boolean judgeDayOfWeek(Date data_date) throws ParseException{
		int day_of_week = data_date.getDay();
		if (day_of_week == 0 || day_of_week == 6) {
			return true;
		}
		return false;
	}
	/**
	 *11.信息通信系统调度联系单处理时长
	 *（1）当平均解决时长小于4小时为100分；当平均解决时长大于24小时为0分；当平均解决时长在4至24小时之间，线性扣分。每增加0.2小时扣1分。
	 *（2）对于在非工作时日工作联系单的处理时长，由于在计算时是减半处理，所以相当于平均解决时长小于8小时为100分，大于48小时为0分。
	 *周一至周五：每大于目标值1小时扣5分。
	 *周六日：每大于目标值1小时扣2.5分。
	 *数据单位：小时
	 * @throws ParseException 
	 */
	public double formatXXTXXXDDLXDCLSC(List<ZbData> datas,double target) throws ParseException{
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				//判断是否是周末
				boolean isWeekend = judgeDayOfWeek(datas.get(j).getZbDate());
				double muyltiple = 5.0;
				double target_dynamic = target;
				//如果是周末
				if(isWeekend==true){
					target_dynamic = target*2;
					muyltiple = muyltiple/2;
				}
				if(data_double<=target_dynamic){
					temp = 100;
				}else{
					temp = 100-(data_double-target_dynamic)*muyltiple;
				}
				if(temp<0.0){
					temp = 0.0;
				}
				System.out.println("原始数据实是： "+data_double+"目标值： "+target_dynamic+"倍数："+muyltiple+"今天是：周末："+isWeekend+"今天的得分是:"+temp);
			}
			System.out.println("数据格式不正确！今天的得分是:"+temp);
			sum += temp;
		}
		return sum;
	}
	/**
	 *12.信息通信系统调度指令执行合格率
	 *(1)当调度指令执行合格数等于总部下发调度指令总数时，为100分；
	 *（2）当调度指令执行合格数小于总部下发调度指令总数时，调度指令合格数越少，得分越低；
	 *（3）当信息调度指令执行合格率不超过90%得分为0分，当调度指令执行合格率超过90%时，每减少0.1%扣1分。
	 *数据单位：小时
	 */
	public double formatXXTXXXDDZLZXHGL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);

					if (data_double >= target) {
						temp = 100;
					} else {
						temp = (100 - (target - data_double) * 10* 1 > 0) ? (100 - (target - data_double) * 10* 1): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**????问用户怎么填写这一项????
	 *13.信息通信系统调度值班不合格数量
	 *（1）当值班不合格次数为0时不扣分，每发现1次值班不合格扣5分，最多扣30分。
	 *（2）调度人员的编制是否能支撑7*24小时值班，达到此要求不扣分，未达到扣10分。
	 *（3）至少设有一名值长，设立有值长不扣分，否则扣10分。
	 *（4）每个班至少设有一名主值、一名副值，（且在知识结构上能覆盖信息专业与通信专业）。具备以上条件不扣分，否则扣10分。
	 *（5）抽查全部调度员具备调度员资格认证不扣分，每发现1位调度员无资格认证扣2.5分，最多扣10分。
	 *（6）日巡检材料、每日简报上报数占总部要求上报的总次数比率，当比率少于80%时扣20分； 当比率率超过80%时，每减少1%扣1分，按线性扣分。
	 *
	 *假定：只是用规则（1）。其他规则统计由别的途径计算。
	 *每次减5分。
	 *
	 *数据单位：次
	 */
	public double formatXXTXXXDDZBBHGSL(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double<=target){
					temp = 100;
				}else{
					if(data_double*5<30){
						temp = 100-data_double*5;
					}else{
						temp = 100-30;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *14.信息系统一单两票不合格数
	 *（1）当信息系统一单两票不合格数超过20个得分为0分
	 *（2）当信息系统一单两票不合格数小于20个时，每发现一个扣5分。
	 *数据单位：个
	 */
	public double formatXXXXYDLPBHGS(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double<=target){
					temp = 100;
				}else{
					temp = 100-data_double*5;
				}
				if(temp<0){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *15.信息通信设备台账合格率
	 *（1）当信息通信设备台账填报合格率不超过80%得分为0分
	 *（2）当信息通信设备台账填报合格率超过80%时，每减少0.2%扣1分。
	 *数据单位：百分比
	 */
	public double formatXXTXSBTZHGL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);

					if (data_double >= target) {
						temp = 100;
					} else {
						temp = (100 - (target - data_double) * 5 > 0) ? (100 - (target - data_double) * 5): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *16.信息设备集中自动监控率
	 *（1）当信息设备自动监控率大于等于80%时，信息设备自动监控率项满分；
	 *（2）当信息设备自动监控率大于等于60%但小于80%时，线性扣分；
	 *（3）当信息设备自动监控率小于60%时，信息设备自动监控率项0分。
	 *数据单位：百分比
	 */
	public double formatXXSBJZZDJKK(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);
					
					if (data_double >= target) {
						temp = 100;
					} else {
						temp = (100 - (target - data_double) * 5 > 0) ? (100 - (target - data_double) * 5): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *17.信息系统缺陷及时消缺率
	 *if（及时消缺率>=80%,100,if(及时消缺率<=50%,0,(及时消缺率-50%)/0.3*100))
	 *比目标值每少1%，扣3.333分。
	 *数据单位：百分比
	 */
	public double formatXXXXQXJSXQL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);
					
					if (data_double >= target) {
						temp = 100;
					} else {
						temp = (100 - (target - data_double) * 10/3 > 0) ? (100 - (target - data_double) * 10/3): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *18.信息通信系统检修执行合格率
	 *（1）当信息系统检修执行合格率不超过80%时，信息系统检修执行合格率项0分；                      
	 *（2）当信息系统检修执行合格率大于等于80%小于100%时，线性扣分；                    
	 *（3）当通信系统检修执行合格率不超过80%时，通信系统检修执行合格率项0分；
	 *（4）当通信系统检修执行合格率超过80%时，线性扣分。
	 *每低于目标值1%，扣5分。
	 *数据单位：百分比
	 */
	public double formatXXTXXXJXZXHGL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);
					
					if (data_double >= target) {
						temp = 100;
					} else {
						temp = (100 - (target - data_double) * 5 > 0) ? (100 - (target - data_double) * 5): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *19.信息系统临时检修率
	 *（1）当信息系统临时检修率超过40%，信息系统临时检修率项0分；
	 *（2）当信息系统临时检修率不超过40%时，线性扣分；
	 *检修率每增加1%，扣2.5分。
	 *数据单位：百分比
	 */
	public double formatXXXXLSJXL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);
					
					if (data_double <= target) {
						temp = 100;
					} else {
						temp = (100 - (target - data_double) * 2.5 > 0) ? (100 - (target - data_double) * 2.5): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *20.信息系统平均计划检修时长
	 *（1）当信息系统平均计划检修时长小于等于2小时得分100分，
	 *（2）当超过2小时,每增加1小时，扣50分，信息系统平均计划检修时长超过4小时为0分。
	 *超过目标值每多一小时，扣25分
	 *数据单位：小时
	 */
	public double formatXXXXPJJHJXSC(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double<=target){
					temp = 100;
				}else{
					temp = 100-(data_double-target)*25;
				}
				if(temp<0){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *21.信息系统月检修调整率
	 *（1）调整率低于或等于20%得分100分，调整率为100%得分0分。
	 *（2）当调整率超过20%，低于100%，采用线性扣分。
	 *每高于目标值1%，扣5/4分
	 *数据单位：百分率
	 */
	public double formatXXXXYJXTZL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);
					
					if (data_double <= target) {
						temp = 100;
					} else {
						temp = (100 - (data_double-target) * (5/4) > 0) ? (100 - (data_double-target) * (5/4)): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *22.信息系统重复检修次数
	 *每出现一次重复检修次数扣5分，最高100分，最低0分。
	 *数据单位：次
	 */
	public double formatXXXXCFJXCS(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double data_double = 0.0;
				try {
					data_double = Double.parseDouble(datas.get(j).getZbData());
				} catch (Exception e) {
					temp = 100;
					sum += temp;
					continue;
				}
				if(data_double<=target){
					temp = 100;
				}else{
					temp = 100-data_double*5;
				}
				if(temp<0){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *23.信息系统检修计划审批一次通过率
	 *（1）当检修计划审批一次通过率大于等于80%时，满分；
	 *（2）当检修计划审批一次通过率大于等于60%但小于80%时，线性扣分；
	 *（3）当检修计划审批一次通过率小于60%时，0分。
	 *每小于目标值1%，扣5分
	 *数据单位：百分率
	 */
	public double formatXXXTJXJHSPYCTGL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);
					
					if (data_double >= target) {
						temp = 100;
					} else {
						temp = (100 - (target-data_double) * 5 > 0) ? ((target-data_double) * 5 ): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**???问用户需求???
	 *24.数据库复制非计划中断处理不合格次数
	 *（1）当无非计划中断次数或采取不影响数据一致性的处理方式恢复进程运行，该项指标为100分
	 *（2）对可能导致数据不一致的处理，每次扣5分，针对未及时同步的中断表，每次扣10分，两项扣分累计，直至0分。
	 *
	 *先自己确定规则，以后和用户确认
	 *a,b。a:可能导致数据不一致的处理次数；b:未及时同步的中断表的处理次数。
	 *
	 *数据单位：次数。数据必须中间带中或英文逗号。
	 *输入单个0时，表示a和b都是0，即满分。
	 */
	public double formatSJKFZFJHZDCLBHGCS(List<ZbData> datas,double target){
		double sum = 0.0;
		for(int j=0;j<datas.size();j++){
			double temp = 0.0;
			if(datas.get(j).getZbData()==null||datas.get(j).getZbData().equalsIgnoreCase("")){
				temp = 100;
			}else{
				double[] data_doubles = {-1.0,-1.0};
				if(datas.get(j).getZbData().equalsIgnoreCase("0")){
					temp = 100;
				}else{
					try {
						//data_doubles[0]:可能导致数据不一致的处理次数，data_doubles[1]:针对未及时同步的中断表次数
						data_doubles = getNumberBeforeAfterComma(datas.get(j).getZbData());
						//System.out.println("原字符串："+datas.get(j).getZbData()+"Double： "+data_doubles[0]+","+data_doubles[1]);
					} catch (Exception e) {
						temp = 100;
						sum += temp;
						continue;
					}
					temp = 100-data_doubles[0]*5-data_doubles[1]*10;
				}
				
				if(temp<0){
					temp = 0.0;
				}
			}
			sum += temp;
		}
		System.out.println("%%%总数是： "+sum);
		return sum;
	}
	/**
	 *25.工单处理及时率
	 *（1）当工单处理及时率大于等于95%时，满分；
	 *（2）当工单处理及时率大于等于60%但小于95%时，线性扣分；
	 *（3）当工单处理及时率小于60%时，0分。
	 *每小于目标值1%，扣20/7分
	 *数据单位：百分率
	 */
	public double formatGDCLJSL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);
					
					if (data_double >= target) {
						temp = 100;
					} else {
						temp = (100 - (target-data_double) * (20/7) > 0) ? ((target-data_double) * (20/7) ): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *26.电话弃话率
	 *（1）电话弃话率低于或等于20%时，100分。
	 *（2）当电话弃话率超过20%，低于50%时，采用线性扣分。
	 *（3）电话弃话率超过50%时，0分。
	 *每高于目标值3%，扣10分
	 *数据单位：百分率
	 */
	public double formatDHQHL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);
					
					if (data_double <= target) {
						temp = 100;
					} else {
						temp = (100 - (data_double-target) * (10/3) > 0) ? ((data_double-target) * (10/3) ): 0;
					}
				}
			}
			sum += temp;
		}
		return sum;
	}
	/**
	 *27.电话及时接通率
	 *（1）当电话及时接通率大于等于95%时，满分；
	 *（2）当电话及时接通率大于等于75%但小于95%时，线性扣分；
	 *（3）当电话及时接通率小于75%时，0分。
	 *每小于目标值1%，扣5分
	 *数据单位：百分率
	 */
	public double formatDHJSJTL(List<ZbData> datas,double target){
		double sum = 0.0;
		for (int j = 0; j < datas.size(); j++) {
			double temp = 0.0;
			if (datas.get(j).getZbData() == null|| datas.get(j).getZbData().equalsIgnoreCase("")) {
				temp = 100;
			} else {
				String data_string = datas.get(j).getZbData();
				int index_of_percentage = data_string.indexOf("%");
				if (index_of_percentage == -1) {
					temp = 100;
				} else {
					String data_substring = data_string.substring(0,index_of_percentage);
					double data_double = 0.0;
					try {
						data_double = Double.parseDouble(data_substring);
					} catch (Exception e) {
						//System.out.println("！！！数据格式不正确，String-->Double转换失败！！！");
						temp = 100;
						sum += temp;
						//System.out.println("一异常已经发生！");
						continue;
					}
//					System.out.println("原字符串：" + data_substring + "Double： "+ data_double);
					
					if (data_double >= target) {
						temp = 100;
					} else {
						temp = (100 - (target-data_double) * 5 > 0) ? (100 - (target-data_double) * 5 ): 0;
					}
					//System.out.println("电话及时接通率2每天得分是： "+temp);
				}
			}
			sum += temp;
		}
		return sum;
	}
	
}
