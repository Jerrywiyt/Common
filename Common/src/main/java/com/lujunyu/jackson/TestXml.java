package com.lujunyu.jackson;

import com.google.common.collect.ImmutableList;
import org.json.JSONObject;
import org.json.XML;

public class TestXml {
  public static void main(String[] args) {
    JSONObject object =
        new JSONObject()
            .put(
                "LICENSE",
                new JSONObject()
                    .put("id", "33000000001")
                    .put(
                        "USERINFO",
                        new JSONObject()
                            .put("org_code", "33000000001")
                            .put("org_Name", "安彼迎网络（北京）有限公司")
                            .put("account_id", "33000000001")
                            .put("ip", "136.24.204.163")
                            .put("begin_date", "20181206142959")
                            .put("end_date", "20231206142959"))
                    .put(
                        "SERVICEINFO",
                        new JSONObject()
                            .put(
                                "SERVICE",
                                ImmutableList.of(
                                    new JSONObject()
                                        .put("SERVICE_CODE", "0103w2")
                                        .put("SERVICE_NAME", "网约房基本信息"),
                                    new JSONObject()
                                        .put("SERVICE_CODE", "0103w4")
                                        .put("SERVICE_NAME", "网约房人员住宿信息"))))
            .put("SIGNATURE", "500D008A9505146915952A6B53BBD5982E219C50"));

    System.out.println(XML.toString(object));
  }
}
