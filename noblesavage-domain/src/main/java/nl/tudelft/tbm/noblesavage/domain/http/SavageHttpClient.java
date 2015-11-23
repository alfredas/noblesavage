package nl.tudelft.tbm.noblesavage.domain.http;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CookieStore;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

public class SavageHttpClient {

  DefaultHttpClient httpclient = null;
  HttpContext persistentContext = new BasicHttpContext();
  CookieStore cookieStore = new BasicCookieStore();

  private String proxyUrl;
  private int proxyPort;

  private static SavageHttpClient instance = null;

  private static final Logger log = Logger.getLogger(SavageHttpClient.class);

  public SavageHttpClient() {
    // see
    // http://hc.apache.org/httpcomponents-client-ga/tutorial/html/connmgmt.html
    // for configuration tutorial
    HttpParams params = new BasicHttpParams();
    ConnManagerParams.setMaxTotalConnections(params, 100);
    // Increase default max connection per route to 20
    ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
    // Increase max connections for localhost:80 to 50
    HttpHost localhost = new HttpHost("locahost", 80);
    connPerRoute.setMaxForRoute(new HttpRoute(localhost), 50);
    ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);
    ConnManagerParams.setTimeout(params, 5000);

    SchemeRegistry schemeRegistry = new SchemeRegistry();
    schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

    ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);

    httpclient = new DefaultHttpClient(cm, params);

    httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
    httpclient.setCookieStore(cookieStore);
    instance = this;
  }

  public SavageHttpClient(String proxyUrl, int proxyPort) {
    this();
    this.proxyPort = proxyPort;
    this.proxyUrl = proxyUrl;
    if (proxyUrl != null && proxyPort > 0) {
      HttpHost proxy = new HttpHost(proxyUrl, proxyPort);
      httpclient.getCredentialsProvider().setCredentials(new AuthScope(proxyUrl, proxyPort), null);
      httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
      log.info("Initialized http client with proxy: " + proxyUrl + ":" + proxyPort);
    } else {
      log.info("Initialized http client without proxy");
    }

  }

  public static DefaultHttpClient getClient() {
    return instance.httpclient;
  }

  public HttpContext getContext() {
    return persistentContext;
  }

  public String getProxyUrl() {
    return proxyUrl;
  }

  public void setProxyUrl(String proxyUrl) {
    this.proxyUrl = proxyUrl;
  }

  public int getProxyPort() {
    return proxyPort;
  }

  public void setProxyPort(int proxyPort) {
    this.proxyPort = proxyPort;
  }

}