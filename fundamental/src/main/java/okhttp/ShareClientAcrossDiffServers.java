package okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShareClientAcrossDiffServers {

  public static void main(String[] args) throws Exception {
    OkHttpClient client = new OkHttpClient();
    Request getReqForBing = new Request.Builder().url("https://cn.bing.com").build();
    try (Response resp = client.newCall(getReqForBing).execute()) {
      System.out.println("[from bing.com] resp status: " + resp.code());
    }

    Request getReqForBaidu = new Request.Builder().url("https://www.baidu.com").build();
    try (Response resp = client.newCall(getReqForBaidu).execute()) {
      System.out.println("[from baidu.com] resp status: " + resp.code());
    }
  }
}
