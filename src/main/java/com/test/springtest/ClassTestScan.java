package com.test.springtest;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ClassTestScan {

	public static void main(String[] args)
			throws NoSuchMethodException, SecurityException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// 结果 class
		List<Class<?>> result = null;
		String scan = "com.test.springtest";
		// 把 . 转换成 \ 因为下面是文件操作
		scan = scan.replaceAll("\\.", "/");
		Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(scan);
		while (dirs.hasMoreElements()) {
			URL url = dirs.nextElement();
			if (url.getProtocol().equals("file")) {
				List<File> classes = new ArrayList<File>();
				// 递归 变量路径下面所有的 class文件
				listFiles(new File(url.getFile()), classes);
				// 加载我们所有的 class文件 就行了
				result = loadeClasses(classes, scan);
			}

		}
		Map<String, Object> beanMap = new HashMap<String, Object>();
		// 打印结果
		for (Class<?> clazz : result) {
			if(clazz.isAnnotation()) {
				continue;
			}
			Annotation[] annotations = clazz.getDeclaredAnnotations();
			if(annotations == null || annotations.length == 0) {
				continue;
			}
			for (Annotation annotation : annotations) {
				if (annotation instanceof BeanTest) {
					BeanTest bt = (BeanTest) annotation;
					String value = bt.value();
					try {
						beanMap.put(value, clazz.newInstance());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		Map<Class<?>, Object> instanceMap = new HashMap<Class<?>, Object>();
		for (Class<?> clazz : result) {
			if(clazz.isAnnotation()) {
				continue;
			}
			Field fields[] = clazz.getDeclaredFields();
			Object instance = clazz.newInstance();
			instanceMap.put(clazz, instance);
			for (Field f : fields) {
				Annotation[] annotations = f.getDeclaredAnnotations();
				if(annotations == null || annotations.length == 0) {
					continue;
				}
				for (Annotation annotation : annotations) {
					if (annotation instanceof AutoWireTest) {
						AutoWireTest awt = (AutoWireTest) annotation;
						String value = awt.value();
						Object object = beanMap.get(value);
						if (object == null) {
							System.out.println(value +" object is empty!");
							continue;
						}
						f.setAccessible(true);
						try {
							f.set(instance, object);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}
			}
		}
		for (Entry<Class<?>, Object> entry : instanceMap.entrySet()) {
			Object instance = entry.getValue();
			if (instance instanceof ATest) {
				ATest at = (ATest) entry.getValue();
				at.dod();
			}
		}
	}

	private static List<Class<?>> loadeClasses(List<File> classes, String scan) throws ClassNotFoundException {
		List<Class<?>> clazzes = new ArrayList<Class<?>>();
		for (File file : classes) {
			// 因为scan 就是/ ， 所有把 file的 / 转成 \ 统一都是： /
			String fPath = file.getAbsolutePath().replaceAll("\\\\", "/");
			// 把 包路径 前面的 盘符等 去掉 ， 这里必须是lastIndexOf ，防止名称有重复的
			String packageName = fPath.substring(fPath.lastIndexOf(scan));
			// 去掉后缀.class ，并且把 / 替换成 . 这样就是 com.hadluo.A 格式了 ， 就可以用Class.forName加载了
			packageName = packageName.replace(".class", "").replaceAll("/", ".");
			// 根据名称加载类
			clazzes.add(Class.forName(packageName));
		}
		return clazzes;

	}

	/** * 查找所有的文件 * * @param dir 路径 * @param fileList 文件集合 */
	private static void listFiles(File dir, List<File> fileList) {
		if (dir.isDirectory()) {
			for (File f : dir.listFiles()) {
				listFiles(f, fileList);
			}
		} else {
			if (dir.getName().endsWith(".class")) {
				fileList.add(dir);
			}
		}
	}
}
