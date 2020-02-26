package mi.song.skill.util;

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

public class SkillSSL{
    //SSL 설정
    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException{
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager(){
                        
                @Override
                public X509Certificate[] getAcceptedIssuers() {    return null; }
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            }
        }; 
            
            SSLContext sc = SSLContext.getInstance("SSL"); 
            sc.init(null, trustAllCerts, new SecureRandom());
                         
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
            
                @Override
                public boolean verify(String arg0, SSLSession arg1) {    return true;    }
            });           
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory()); 
    }            
}
