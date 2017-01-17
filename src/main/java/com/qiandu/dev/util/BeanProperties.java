package com.qiandu.dev.util;

import com.qiandu.dev.bean.ObjectBaseInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class BeanProperties {

	public List<String> getPropertiesByClass(Object o) {
		List<String> properties = new ArrayList<String>();
		Class<?> cl = ObjectBaseInfo.class;

		// check all declared fields
		for (Field field : cl.getDeclaredFields()) {

			// if field is private then look for setters/getters
			if (Modifier.isPrivate(field.getModifiers())) {

				// changing 1st letter to upper case
				String name = field.getName();
				String upperCaseName = name.substring(0, 1).toUpperCase() + name.substring(1);
				// and have getter and setter
				try {
					Object object= field.get(o);
					if(null!=object){

						String simpleType = field.getType().getSimpleName();
					// for boolean property methods should be isProperty and
					// setProperty(propertyType)
					if (simpleType.equals("Boolean") || simpleType.equals("boolean")) {
						if ((cl.getDeclaredMethod("is" + upperCaseName) != null)
								&& (cl.getDeclaredMethod("set" + upperCaseName, field.getType()) != null)) {
							properties.add(name);
						}
					}
					// for not boolean property methods should be getProperty
					// and setProperty(propertyType)
					else {
						if ((cl.getDeclaredMethod("get" + upperCaseName) != null)
								&& (cl.getDeclaredMethod("set" + upperCaseName, field.getType()) != null)) {
							properties.add(name);
						}
					}}
				} catch (NoSuchMethodException | SecurityException |IllegalAccessException e) {
					// if there is no method nothing bad will happen
				}
			}
		}
		for (String property : properties)
			System.out.println(property);
		return properties;
	}

	public static void main(String[] args) {
		ObjectBaseInfo o = new ObjectBaseInfo();
		BeanProperties beanProp = new BeanProperties();

		List<String> props = beanProp.getPropertiesByClass(o);
		System.out.println(props);
	}
}
