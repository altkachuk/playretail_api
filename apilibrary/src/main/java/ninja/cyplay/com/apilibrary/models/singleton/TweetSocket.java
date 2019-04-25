package ninja.cyplay.com.apilibrary.models.singleton;

import android.content.Context;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import io.socket.client.IO;
import io.socket.client.Socket;
import ninja.cyplay.com.apilibrary.utils.Constants;


/**
 * Created by anishosni on 18/06/15.
 */

// Singleton class
public class TweetSocket {

    private static TweetSocket INSTANCE;
    // SocketIO socket
    private Socket mSocket;

    private Context myContext;

    private TweetSocket(Context myContext) throws IOException {

        this.myContext = myContext;

        String tweet_endpoint = Constants.TWEET_SERVER_URL + "/" + Constants.CLIENT_ID;

        try {
            // prepare certif
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = null;
            Certificate ca;
            caInput = new BufferedInputStream(myContext.getResources().getAssets().open("cy-play-comodo.com.cert.der"));
            ca = cf.generateCertificate(caInput);
            System.err.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            // set socketio options
            IO.Options opts = new IO.Options();
            opts.sslContext = context;
            opts.secure = true;
            opts.hostnameVerifier = new AllowAllHostnameVerifier();

            opts.reconnection = true;
            opts.reconnectionDelay = 7500;

            // init socket client
            mSocket = IO.socket(tweet_endpoint, opts);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public static TweetSocket getINSTANCE(Context myContext) throws IOException {
        if (INSTANCE == null)
            INSTANCE = new TweetSocket(myContext);
        return INSTANCE;
    }

    public Socket getsocket() {
        return mSocket;
    }


}
