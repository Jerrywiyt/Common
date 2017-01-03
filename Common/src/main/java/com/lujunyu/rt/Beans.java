package com.lujunyu.rt;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public class Beans {
	
	public static void main(String[] args) throws IntrospectionException {
		Test t = new Beans().new Test();
		Class clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			System.out.println(field.getName());
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
			System.out.println(pd.getName());
			System.out.println(pd.getPropertyType());
			System.out.println(pd.getReadMethod());
			
		}
		
	}
	
	
	
	private class Test{
		private String field;

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}
	}
}
