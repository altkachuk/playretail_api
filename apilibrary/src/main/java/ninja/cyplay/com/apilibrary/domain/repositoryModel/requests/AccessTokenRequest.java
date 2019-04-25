package ninja.cyplay.com.apilibrary.domain.repositoryModel.requests;

/**
 * Created by damien on 27/01/16.
 */
public class AccessTokenRequest {

    public final static String PASSWORD_GRANT_TYPE = "password" ;
    public final static String PASSWORD_REFRESH_TOKEN = "refresh_token";

    private String grant_type;
    private String username;
    private String password;
    private String scope;
    private String client_id;
    private String client_secret;
    private String refresh_token;
    private String token;

    public AccessTokenRequest(String username, String password, String client_id, String client_secret, String grant_type) {
        this.grant_type = grant_type;
        this.username = username;
        this.password = password;
        this.scope = "seller";
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public AccessTokenRequest(String client_id, String client_secret,  String grant_type, String refresh_token){
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.grant_type = grant_type;
        this.refresh_token = refresh_token;
    }

    public AccessTokenRequest(String client_id, String client_secret,  String token){
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.token = token;
    }


    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
