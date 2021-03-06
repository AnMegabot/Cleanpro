package com.pakpobox.cleanpro.net.ssl;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * 双向验证实现代码
 * User:Sean.Wei
 * Date:2018/5/22
 * Time:16:04
 */

public class MySSLSocketFactory {
    private static final String KEY_STORE_TYPE_BKS = "bks";//证书类型
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//证书类型


    private static final String KEY_STORE_CLIENT_PATH = "client.p12";//客户端要给服务器端认证的证书
    private static final String KEY_STORE_TRUST_PATH = "client.truststore";//客户端验证服务器端的证书库
    private static final String KEY_STORE_PASSWORD = "****";//证书密码（应该是客户端证书密码）
    private static final String KEY_STORE_TRUST_PASSWORD = "***";//授信证书密码（应该是服务端证书密码）

    public static SSLSocketFactory getSocketFactory(Context context) {

        InputStream trust_input = null;
        InputStream client_input = null;
        try {
//          InputStream trust_input = context.getResources().openRawResource(R.raw.trust);//服务器授信证书
            trust_input = context.getResources().getAssets().open(KEY_STORE_CLIENT_PATH);//服务器授信证书
            //        InputStream client_input = context.getResources().openRawResource(R.raw.client);//客户端证书
            client_input = context.getResources().getAssets().open(KEY_STORE_TRUST_PATH);//客户端证书
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(trust_input, KEY_STORE_TRUST_PASSWORD.toCharArray());
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
            keyStore.load(client_input, KEY_STORE_PASSWORD.toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory factory = sslContext.getSocketFactory();
            return factory;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                trust_input.close();
                client_input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
