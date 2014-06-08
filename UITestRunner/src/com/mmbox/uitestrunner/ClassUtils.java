package com.mmbox.uitestrunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;


import dalvik.system.DexFile;


public final class ClassUtils {

	private static final String LOGTAG = "ClassUtils";
	
	
	public static void findClassesInApk(String apkPath,List<String> classNames) throws IOException
	{
		findClassesInApk(apkPath,null,classNames);
	}
	
	/**
	 * 遍历某个apk指定的包名中的所有类
	 * @param apkPath apk的名字
	 * @param packageName 用来过滤指定的包名下的类
	 * @param classNames 存放指定包中的所有类名
	 * @throws IOException
	 */
	public static void findClassesInApk(String apkPath, String packageName,
			List<String> classNames)
			throws IOException {

		DexFile dexFile = null;
		try {
			dexFile = new DexFile(apkPath);
			Enumeration<String> apkClassNames = dexFile.entries();
			while (apkClassNames.hasMoreElements()) {
				String className = apkClassNames.nextElement();
				if(packageName != null) {
					if (className.startsWith(packageName) && isToplevelClass(className)) {
						classNames.add(className);
					}
				} else if (isToplevelClass(className)){
					classNames.add(className);
				}

			}
		} catch (IOException e) {
			Log.w(LOGTAG,
					"Error finding classes at apk path: " + apkPath, e);
		}
		if (null != dexFile) {
			dexFile.close();
		}
	}

	private static boolean isToplevelClass(String fileName) {
		return fileName.indexOf('$') < 0;
	}
	
	
	/**
	 * 在指定的包中查找一个基类的所有子类
	 * @param context
	 * @param packageName 指定包名
	 * @param superClass  基类名称
	 * @param subClasses  所有子类列表
	 * @param ignoreList  忽略的类名
	 */
	public static void findSubClasses(Context context,String packageName,Class<?> superClass,List<Class<?>> subClasses,String ignoreList) {
		String apkName;
		try {
			apkName = context.getPackageManager().getApplicationInfo(packageName,0).sourceDir;
			ArrayList<String> classNames = new ArrayList<String>();
			ClassUtils.findClassesInApk(apkName,packageName,classNames);
			
			for (String className : classNames) {
	          if (className.endsWith(".R") || className.endsWith(".Manifest")) {
	                continue;
	            }
	          final Class<?> cls = Class.forName(className);
	          if(superClass.isAssignableFrom(cls) 
	        		  && ! className.equals(superClass.getName())
	        		   && ignoreList.indexOf(className) <0
	        		  ) {
	        	  
	        	  subClasses.add(Class.forName(className));
	         	}
	         
			}
		} 
		catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
