package com.itany.nmms.factory;

import com.itany.nmms.exception.ObjectFactoryErrorException;
import com.itany.nmms.util.MyBatisUtil;
import com.itany.nmms.util.ParameterUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {

    private static Map<String, Object> objs;

    static {
        objs = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            ObjectFactory
                                    .class
                                    .getClassLoader()
                                    .getResourceAsStream("beans.properties")));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (!ParameterUtil.isNull(line)) {
                    String[] arr = line.split("=");
                    String key = arr[0];
                    String value = arr[1];
                    Class target = Class.forName(value);
                    if (target.isInterface()) {
                        objs.put(key, target);
                        continue;
                    }
                    objs.put(key, target.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("ObjectFactory初始化失败");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ExceptionInInitializerError("ObjectFactory释放资源出错");
                }
            }
        }
    }

    public static Object getObject(String key) {
        Object obj = objs.get(key);
        if (null == obj) {
            throw new ObjectFactoryErrorException("根据对应的key" + key + "在配置文件beans.properties中没有找到对应配置");
        }

        if (obj instanceof Class) {
            return MyBatisUtil.getSession().getMapper((Class) obj);
        }
        return obj;
    }
}
