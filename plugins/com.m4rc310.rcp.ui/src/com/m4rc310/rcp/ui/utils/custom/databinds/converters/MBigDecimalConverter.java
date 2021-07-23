package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import java.math.BigDecimal;

import org.eclipse.swt.widgets.Text;

public class MBigDecimalConverter extends MConverter<Text, BigDecimal> {

	@Override
	public boolean eq(Class<?> type) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public BigDecimal fromComponent(Text component) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void sendToComponent(Text component, BigDecimal value, String format) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean validate(String svalue) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public boolean eq(Class<?> type) {
//		return type == BigDecimal.class;
//	}
//
//	@Override
//	boolean validate(String value) {
//		if (value.isEmpty()) {
//			return false;
//		}
//		Pattern regex = Pattern.compile("-?(?:\\d+(?:\\.\\d+)?|\\.\\d+)");
//		Matcher regexMatcher = regex.matcher(value);
//		return regexMatcher.find();
//	}
//
//	@Override
//	public String toString(Object value) {
//		BigDecimal bd = (BigDecimal)value;
//		
//		DecimalFormat df = new DecimalFormat("###,###,###");
//		df.setMaximumFractionDigits(2);
//		df.setMinimumFractionDigits(2);
//		
//		String res = df.format(bd);
//		
//		return res;
//	}
//
//	@Override
//	public Object toObject(String svalue) {
//		try {
//			svalue = svalue.replace(",", ".");
//			return BigDecimal.valueOf(Double.parseDouble(svalue));
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	@Override
//	public String toString(Object value, String format) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void initListener(Widget widget) {
//		// TODO Auto-generated method stub
//		
//	}

}
