package mi.song.skill.controller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CinemaController {

    @RequestMapping("/cinemainfo")
    @ResponseBody
    public String index() {
        // getCinemaInfo();
        return getCinemaInfo().toString();//"Hello, Spring";
    }

    //영화 정보 받아오는 함수
    public JSONObject getCinemaInfo() {
        JSONObject jsonObject = new JSONObject();
        String baseUrl = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=%EC%98%81%ED%99%94+%EC%83%81%EC%98%81%EC%9E%91";
        try {

            if (baseUrl.contains("https://"))
                setSSL();

            Document cinemaWholePage = Jsoup.connect(baseUrl).get();
            Elements section = cinemaWholePage.select("ul.thumb_list>li>dl>dt>a");
                        
            JSONArray jsonArray = new JSONArray();

            for (Element element : section) {
                Attributes attr = element.attributes();
                String title = attr.get("title");
                jsonArray.put(title);                
            }          
            
            jsonObject.append("movies", jsonArray);            

        } catch(Exception e){
            e.printStackTrace();
        }

        return jsonObject;
    }

    //ssl 설정
    public void setSSL() throws NoSuchAlgorithmException, KeyManagementException { 
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager(){            
                @Override
                public X509Certificate[] getAcceptedIssuers() {                    
                    return null;
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            }
        }; 
            
            SSLContext sc = SSLContext.getInstance("SSL"); 
            sc.init(null, trustAllCerts, new SecureRandom());
            
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() { 
                @Override public boolean verify(String hostname, SSLSession session) 
                { return true; } 
            }); 
            
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory()); 
        }

}