package com.lujunyu.http;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class HttpUtils {

  private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

  private static final CloseableHttpClient HTTPCLIENT =
      HttpClients.createMinimal(new MultiThreadHttpConnectionManager());

  private static final String ENCODING = "utf-8";

  public static final String CONTENTTYPE = "Content-Type";

  public enum HttpMethodType {
    GET,
    POST
  }

  public enum HttpDataType {
    XML,
    JSON;
  }

  private static class MultiThreadHttpConnectionManager extends PoolingHttpClientConnectionManager {
    public MultiThreadHttpConnectionManager() {
      super();
      setMaxTotal(500);
      setDefaultMaxPerRoute(500);
    }
  }

  private static final RequestConfig HTTPCONFIG =
      RequestConfig.custom()
          .setConnectionRequestTimeout(30000)
          .setConnectTimeout(30000)
          .setSocketTimeout(30000)
          .build();

  /**
   * 下载文件到本地。 @Description
   *
   * @author lujunyu
   * @date 2018年2月6日 上午10:47:37
   * @param remoteFilepath
   * @param localFilepath
   * @return
   */
  public static boolean download(String remoteFilepath, String localFilepath) {
    boolean res = false;
    try {
      File file = new File(localFilepath);
      if (file.exists()) {
        file.delete();
      }
      Request.Get(remoteFilepath).execute().saveContent(file);
      logger.info("下载文件 " + remoteFilepath + " 到本地 " + localFilepath + " 成功！");
      res = true;
    } catch (Exception e) {
      logger.error("下载文件 " + remoteFilepath + " 失败", e);
    }
    return res;
  }

  /**
   * 暂不支持https @Description
   *
   * @author lujunyu
   * @date 2018年2月5日 下午4:11:22
   * @param url
   * @param methodType GET、POST
   * @param dataType json、xml
   * @param clazz
   * @param headers
   * @param param
   * @return
   * @throws Exception
   */
  public static <T> T send(
      String url,
      HttpMethodType methodType,
      HttpDataType dataType,
      Class<T> clazz,
      Map<String, Object> headers,
      Object param)
      throws Exception {
    CloseableHttpResponse response = null;
    try {
      if (null != methodType && methodType.equals(HttpMethodType.POST)) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(HTTPCONFIG);
        if (null != param && !(param instanceof String)) {
          List<NameValuePair> parameters = formatHttpPostParam(param);
          logger.debug("HTTP_POST_REQ:" + parameters.toString());
          httpPost.setEntity(new UrlEncodedFormEntity(parameters, ENCODING));
        } else {
          if (null != param) {
            String paramInfo = param.toString();
            httpPost.setEntity(new StringEntity(paramInfo, ENCODING));
            logger.debug("HTTP_POST_REQ:" + paramInfo);
          }
        }
        // 添加请求头
        if (null != headers) {
          addHeaders(headers, httpPost, methodType);
          logger.debug("HTTP_REQ_HEADER" + headers.toString());
        }
        logger.debug("HTTP_POST_URL:" + httpPost.toString());
        response = getHttpClient(headers).execute(httpPost);
        logger.debug("HTTP_POST_RESP:" + response.toString());
      } else {
        StringBuffer paramUrl = new StringBuffer(url);
        if (null != param && !(param instanceof String)) {
          paramUrl.append(formatHttpGetParam(param));
        }
        HttpGet httpGet = new HttpGet(paramUrl.toString());
        httpGet.setConfig(HTTPCONFIG);
        // 添加请求头
        if (null != headers) {
          addHeaders(headers, httpGet, methodType);
        }
        logger.debug("HTTP_GET_URL：" + paramUrl);

        response = getHttpClient(headers).execute(httpGet);
        logger.debug("HTTP_GET_RESP：" + response.toString());
      }
      return buildResp(url, response, clazz, dataType, headers);
    } catch (Exception e) {
      logger.error(url + e.getMessage(), e);
      throw e;
    } finally {
      if (null != response) {
        try {
          response.close();
        } catch (Exception e2) {
          logger.error(e2.getMessage(), e2);
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static <T> T buildResp(
      String url,
      CloseableHttpResponse response,
      Class<T> clazz,
      HttpDataType dataType,
      Map<String, Object> headers)
      throws Exception {
    StringBuilder result = new StringBuilder();
    BufferedReader reader = null;
    try {
      if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        logger.error(url + " respcode: " + response.getStatusLine().getStatusCode());
        throw new Exception("调用外部服务失败，错误码：" + response.getStatusLine().getStatusCode());
      }

      HttpEntity entity = response.getEntity();
      if (entity == null) {
        throw new Exception("系统错误");
      }

      reader = new BufferedReader(new InputStreamReader(entity.getContent(), ENCODING));
      String line = null;
      while ((line = reader.readLine()) != null) {
        result.append(line);
      }

      if (null == clazz) {
        return null;
      }

      if (clazz.equals(String.class)) {
        return (T) result.toString();
      }
      if (null == dataType || dataType.equals(HttpDataType.JSON)) {
        return JSON.parseObject(result.toString(), clazz);
      } else {
        return xmlToObj(result.toString(), clazz);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  //	TODO
  private static <T> T xmlToObj(String string, Class<T> clazz) {
    return null;
  }

  // TODO 支持SSL通讯。
  private static CloseableHttpClient getHttpClient(Map<String, Object> headers) {
    return HTTPCLIENT;
  }

  /**
   * 添加头部信息
   *
   * @param headers
   * @param httpRequestBase
   */
  private static void addHeaders(
      Map<String, Object> headers, HttpRequestBase httpRequestBase, HttpMethodType methodType) {
    if (headers == null || headers.size() == 0) {
      return;
    }

    // post 请求修改默认content type
    if (methodType != null && headers.get(CONTENTTYPE) != null) {
      String contentType = headers.get(CONTENTTYPE).toString().trim();
      if (methodType.equals(HttpMethodType.POST)) {
        HttpPost httpPost = (HttpPost) httpRequestBase;
        StringEntity entity = (StringEntity) httpPost.getEntity();
        entity.setContentType(contentType);
        headers.remove(CONTENTTYPE);
      }
    }
    for (Iterator<Entry<String, Object>> it = headers.entrySet().iterator(); it.hasNext(); ) {
      Entry<String, Object> entry = it.next();
      httpRequestBase.setHeader(entry.getKey(), entry.getValue().toString());
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static List<NameValuePair> formatHttpPostParam(Object param)
      throws IntrospectionException, IllegalArgumentException, IllegalAccessException,
          InvocationTargetException {
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    if (param instanceof Map) {
      for (Map.Entry<String, String> entry : ((Map<String, String>) param).entrySet()) {
        nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
      }
    } else {
      Object obj = null;
      Class clazz = param.getClass();
      for (Field field : clazz.getDeclaredFields()) {
        if (field.getName().equals("serialVersionUID")) {
          continue;
        }
        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
        Method getMethod = pd.getReadMethod(); // 获得get方法
        obj = getMethod.invoke(param); // 执行get方法返回一个Object
        if (null != obj) {
          nvps.add(new BasicNameValuePair(field.getName(), obj.toString()));
        }
      }
    }
    return nvps;
  }

  @SuppressWarnings("rawtypes")
  private static String formatHttpGetParam(Object param)
      throws IntrospectionException, IllegalArgumentException, IllegalAccessException,
          InvocationTargetException {
    StringBuffer sb = null;
    Object obj = null;
    Class clazz = param.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      if (field.getName().equals("serialVersionUID")) {
        continue;
      }
      PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
      Method getMethod = pd.getReadMethod(); // 获得get方法
      obj = getMethod.invoke(param); // 执行get方法返回一个Object
      if (null != obj) {
        if (null == sb) {
          sb = new StringBuffer("?").append(field.getName()).append("=").append(obj);
        } else {
          sb = sb.append("&").append(field.getName()).append("=").append(obj);
        }
      }
    }
    String resultParam = null;
    if (null != sb) {
      resultParam = sb.toString();
    }
    return resultParam;
  }
}
