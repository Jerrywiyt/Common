package com.lujunyu.okhttp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Test {

  public static void main(String[] args) throws IOException {
    MediaType mediaType = MediaType.parse("image/jpeg");
    RequestBody requestBody = RequestBody.create(getImageFile(), mediaType);
    Request request =
        new Request.Builder()
            .url(
                "https://airbnb-photos.s3.amazonaws.com/pictures/watermark/111111111/original/test?x-amz-acl=public-read&X-Amz-Security-Token=FwoGZXIvYXdzEAQaDP5vyh%2FSYTWutWvnNiK5AbL2AVX2blp8QlWMrdQoDOy6ap%2BbvVNKgLIkLi4DTO0OKNeHlyJM2Xb0utw2rtqVMQasAxS1M2S%2FFN5KZiTbEtA7UJuqGgyZ3ak6CVEGYC06WEbDrj6JCZ1MhCKsc0o3GyjCH1J2iN13IdUS8lKJcFxDT2KlD3fU8o7%2B%2BD6vjRquIiXr05SHSjET3rlhc8x1rZJNmEfijzOtwcRhIqG7fyrl2kWWxvTckRUyobf9eXnGosGNuP2JHYDwKOH6s%2FwFMi0ejg1Z9HuyCnAFyWm37UMQqj83a%2BUtztu0alnbLg%2FbeRCWBHg0F9M4U6xkw44%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20201019T025253Z&X-Amz-SignedHeaders=content-type%3Bhost&X-Amz-Expires=300&X-Amz-Credential=ASIASQMNC3HJVFINPMOO%2F20201019%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=e9431b85aa12f78f4a39cb6265e3ad6264195d7a35ccba6dcadf294dfa34c697")
            .put(requestBody)
            .build();
    OkHttpClient client = new OkHttpClient();
    Response response = client.newCall(request).execute();
    System.out.println(new String(response.body().bytes(), "utf-8"));
  }

  private static byte[] getImageFile() throws IOException {
    FileInputStream fileInputStream =
        new FileInputStream(new File("/Users/jerry_lu/Desktop/1111.jpg"));

    byte[] bytes = new byte[fileInputStream.available()];

    fileInputStream.read(bytes);
    return bytes;
  }
}
