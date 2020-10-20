package com.lujunyu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Preconditions;
/**
 * 需要改造。
 *
 * @author lujunyu
 */
public enum ConfigUtils {
  // FIXME 需要重构，不搞成这种枚举的形式了。
  // 只加载classpath下面的配置,需要搞清楚spring 和 linux下文件加载方式。然后设计一个通用的模板化设计。
  CONFIG("prop/config.properties");

  private String path;
  private Properties prop = new Properties();

  private ConfigUtils(String path) {
    this.path = path;
    loadFile(path, prop);
  }

  private static void loadFile(String path, Properties prop) {
    try {
      Preconditions.checkArgument(!StringUtils.isEmpty(path), "文件路径为空");
      String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
      String filePath = rootPath + path.replace("/", File.separator);
      System.out.println(filePath);
      prop.load(new FileInputStream(filePath));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getPath() {
    return this.path;
  }

  public String getProperty(String key) {
    return prop.getProperty(key);
  }

  public String getProperty(String key, String defaultValue) {
    return prop.getProperty(key, defaultValue);
  }

  public <T> T getProperty(String key, String defaultValue, Class<T> clazz) {
    String str = getProperty(key, defaultValue);
    return StringUtils.stringToClass(str, clazz);
  }

  public <T> T getProperty(String key, Class<T> clazz) {
    String str = getProperty(key);
    return StringUtils.stringToClass(str, clazz);
  }

  public static String getPropertyByPath(String path, String key) {
    return Prop.getProperty(path, key);
  }

  public static String getPropertyByPath(String path, String key, String defaultValue) {
    return Prop.getProperty(path, key, defaultValue);
  }

  public static <T> T getPropertyByPath(String path, String key, Class<T> clazz) {
    String str = getPropertyByPath(path, key);
    return StringUtils.stringToClass(str, clazz);
  }

  public static <T> T getPropertyByPath(
      String path, String key, String defaultValue, Class<T> clazz) {
    String str = getPropertyByPath(path, key, defaultValue);
    return StringUtils.stringToClass(str, clazz);
  }

  private static class Prop {
    private static Map<String, Properties> map = new ConcurrentHashMap<>();

    private static String getProperty(String path, String key) {
      Properties prop = getProp(path);
      return prop.getProperty(key);
    }

    private static String getProperty(String path, String key, String defaultValue) {
      Properties prop = getProp(path);
      return prop.getProperty(key, defaultValue);
    }

    private static Properties getProp(String path) {
      Preconditions.checkArgument(!StringUtils.isEmpty(path), "path不能空");
      Properties prop = map.get(path);
      if (prop != null) {
        return prop;
      } else {
        synchronized (path) {
          prop = map.get(path);
          if (prop != null) {
            return prop;
          } else {
            prop = new Properties();
            loadFile(path, prop);
            map.put(path, prop);
            return prop;
          }
        }
      }
    }
  }
}
