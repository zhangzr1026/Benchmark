import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpClient implements Runnable{

	int threadNo;
    public int getThreadNo() {
        return threadNo;
    }
    public void setThreadNo(int threadNo) {
        this.threadNo = threadNo;
    }
  
    
	public void run(){
		String deviceSn,accessKey;
		deviceSn = "";
		accessKey = "";
		
			long t1 = System.currentTimeMillis();	       
	       DefaultHttpClient httpclient = new DefaultHttpClient();
	       //enableSSL(httpclient);

	       String url = "http://192.168.1.19:7001/VaneServer/device!activeDevice.do";
	       HttpPost httpPost = new HttpPost(url);
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("device_sn", deviceSn));
	        nvps.add(new BasicNameValuePair("access_key", accessKey));
	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }

	        try {
	            // System.out.println(httpPost.getRequestLine());
	            HttpResponse response2 = httpclient.execute(httpPost);
	            
	           // System.out.println(response2.getStatusLine());
	            HttpEntity entity2 = response2.getEntity();
	            String content = EntityUtils.toString(entity2);
	           // System.out.println(content);
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            httpPost.releaseConnection();
	        }
	        Long t2 = System.currentTimeMillis();
	        BigDecimal end  = new BigDecimal(t2);
	        BigDecimal start  = new BigDecimal(t1);
	        System.out.println(getThreadNo()+"#"+end.subtract(start));
    }
	
	public static void main(String[] args) {
    }
}
