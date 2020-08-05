package com.lujunyu.okhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lujunyu.test.Field;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Test {

  public static void main(String[] args) throws IOException {
    File file = new File("/Users/jerry_lu/Downloads/tujia.txt");
    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
    for (int i = 10000; i < 1000000; i++) {
      String license = parse(i);
      System.out.println(license);
      if(license!=null){
        bw.write(i+","+license);
        bw.write("\r\n");
        bw.flush();
      }
    }
  }

  private static String parse(long hostId) throws IOException {
    Response response = fetch(hostId);
    String json = new String(response.body().bytes());
    JSONObject jsonObject = JSON.parseObject(json);
    int errorCode = jsonObject.getIntValue("errorCode");
    if (errorCode == 0) {
      String license =
          jsonObject
              .getJSONObject("content")
              .getJSONObject("mainPart")
              .getJSONArray("businessLicenseModule")
              .getString(0);
      return license;
    }
    return null;
  }

  private static Response fetch(long hostingId) throws IOException {
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body =
        RequestBody.create(
            mediaType,
            "{\n    \"psid\": \"0A9F9789-3560-4450-9C62-A4729F4B5F11\",\n    \"parameter\": {\n        \"houseParameter\": {\n            \"preview\": false,\n            \"graft\": false,\n            \"houseId\": "
                + hostingId
                + ",\n            \"houseGuid\": \"\"\n        },\n        \"productParameter\": {\n            \"peopleCount\": 1,\n            \"checkOutDate\": \"2020-08-06\",\n            \"checkInDate\": \"2020-08-05\",\n            \"needPrice\": true,\n            \"bookingCount\": 1,\n            \"activityInfo\": \"\"\n        },\n        \"abTests\": {\n            \"DetailModule_T_0525\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"detailRNnew_0520\": {\n                \"v\": \"B\",\n                \"s\": true\n            },\n            \"T_bdyq20200616\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"T_detail20200414\": {\n                \"v\": \"B\",\n                \"s\": true\n            }\n        }\n    },\n    \"type\": \"bingo/app/house/gethouse/v3/bnb\",\n    \"client\": {\n        \"devToken\": \"D8F205372897C574E52B34845825EC83AF35936F2D7C651E121772091212962F\",\n        \"crnVersion\": \"231\",\n        \"abTests\": {\n            \"appTujiaIMiOS0624\": {\n                \"v\": \"B\",\n                \"s\": true\n            },\n            \"appnodate1106\": {\n                \"v\": \"C\",\n                \"s\": true\n            },\n            \"AppRedPackets\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"dtzfrk0203\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"HomeTab_LikeAB_0508\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"T_bdyq20200616\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"DetailModule_T_0525\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"T_credit_pay0528\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"T_LIST27620\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"Tfugouquan2001\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"cashback24988\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"detailShare_0106\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"CRNHomeABTest_ios_8.13.0\": {\n                \"v\": \"B\",\n                \"s\": true\n            },\n            \"appRevealCoupon\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"detailRNnew_0520\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"TUJIA_LIST_PK_GUIDANCE_38672\": {\n                \"v\": \"A\",\n                \"s\": true\n            },\n            \"T_cleanning_29232\": {\n                \"v\": \"C\",\n                \"s\": true\n            }\n        },\n        \"version\": \"236\",\n        \"osVersion\": \"13.6\",\n        \"appId\": \"com.tujia.tujia\",\n        \"locale\": \"zh-Hans-CN\",\n        \"uID\": \"D1B563C1DB6952E816AC1549128C5DED\",\n        \"channelCode\": \"iappstore\",\n        \"devType\": \"1\",\n        \"sessionId\": \"D1B563C1DB6952E816AC1549128C5DED_1596601554716\",\n        \"traceid\": \"1596602692374_0x139d3caf0_1596602692308\",\n        \"buildTag\": \"8.224-prod.4914\",\n        \"appVersion\": \"236\",\n        \"tid\": \"20080512260755313675\",\n        \"devModel\": \"iPhone\",\n        \"screenInfo\": \"02:00:00:00:00:00,5A1A0935-5219-4733-AEE0-A807CA680363\"\n    },\n    \"user\": {}\n}");
    Request request =
        new Request.Builder()
            .url("https://client.tujia.com/bingo/app/house/gethouse/v3/bnb")
            .method("POST", body)
            .addHeader("Host", "client.tujia.com")
            .addHeader("X-TJH", "56077ad7678f638d178c91495946c7a095ebb860")
            .addHeader("User-Agent", "tujia(hotel/236/236 net/NOT REACHABLE loc/zh-Hans-CN)")
            .addHeader("X-TJTS", "1596602686")
            .addHeader(
                "G-TJS",
                "MM4AzGcAgMzA3yNAZMxwTjIEAMzDz2NANA4A2AEAYAWA3AMANAz4GAgUMADDzANNZAixTDZcAUmT12MMMMm2mTZQIkTT2zYNYNkyjzVAMMjj10ZNZMl2GjFkEQDT1yMNOY1xTzYI")
            .addHeader("G-TJAP", "236")
            .addHeader(
                "X-App-Client",
                "MAC=02:00:00:00:00:00;LON=116.4540103;LAT=39.9176448;CITY=48;CID=;LAC=;IMEI=;IMSI=;UID=D1B563C1DB6952E816AC1549128C5DED;OSVersion=13.6;AppVersion=236;DevType=1;DevModel=iPhone;Manufacturer=Apple")
            .addHeader("X-TJP", "10")
            .addHeader(
                "X-App-Stats",
                "logid=;curPage=;refer=;refPage=;traceId=3f6afc1e-86c9-463f-9e25-4b803bcdfad5")
            .addHeader("G-TJSE", "D1B563C1DB6952E816AC1549128C5DED_1596601554716")
            .addHeader("Accept-Language", "zh-cn")
            .addHeader("L-Uuid", "3edf72bbed028a11d7fe72aae89f967c")
            .addHeader("role", "0")
            .addHeader("G-TJP", "2")
            .addHeader("Accept", "*/*")
            .addHeader("Content-Type", "application/json")
            .addHeader("G-TJU", "D1B563C1DB6952E816AC1549128C5DED")
            .build();
    return client.newCall(request).execute();
  }
}
