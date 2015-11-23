package nl.tudelft.tbm.noblesavage.domain.http;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class Proxify {

  private static final String PROXY_URL_STRING = "http://proxifyit.appspot.com/proxy";
  private static final Logger log = Logger.getLogger(Proxify.class.getName());

  public static InputStream getProxiedStream(String url) {

    try {
      log.info("Proxifying: " + url);
      URL PROXY_URL = new URL(PROXY_URL_STRING);
      HttpURLConnection connection = (HttpURLConnection) PROXY_URL.openConnection();
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");

      OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
      writer.write("content=" + url);
      writer.close();

      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        return connection.getInputStream();
      } else {
        log.error("Proxy returned HTTP code: " + connection.getResponseCode());
        return null;
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

}
