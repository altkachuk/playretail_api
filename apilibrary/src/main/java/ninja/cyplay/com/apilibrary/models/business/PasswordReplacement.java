package ninja.cyplay.com.apilibrary.models.business;

import org.parceler.Parcel;

/**
 * Created by romainlebouc on 01/02/2017.
 */
@Parcel
public class PasswordReplacement {

    String old_password;
    String password;

    PasswordReplacement(){

    }

    public PasswordReplacement(String old_password, String password) {
        this.old_password = old_password;
        this.password = password;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
