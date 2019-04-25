package ninja.cyplay.com.apilibrary.utils;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;

/**
 * Created by damien on 03/06/16.
 */
public class RealmHelper {

    public static void saveObjectToRealm(Context context,final RealmObject object) {
        // Get a Realm instance for this thread
        final  Realm realm = Realm.getInstance(MVPCleanArchitectureApplication.get(context).getRealmConfiguration());
        //realm.beginTransaction();
        //realm.copyToRealm(object);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                realm.copyToRealm(object);
            }
        });
        //realm.commitTransaction();
    }

}
