package ec.com.pablorcruh.goodrecipes.common;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.google.firebase.FirebaseApp;

public class MyApp extends Application {

    public static MyApp instance;

    public static MyApp getInstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    public static final String CHANNEL_NEW_RECIPE = "CHANNEL NEW RECIPE";

    @Override
    public void onCreate() {
        instance =  this;
        super.onCreate();
        FirebaseApp.initializeApp(this);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            NotificationChannel channelNewRecipe = new NotificationChannel(
              CHANNEL_NEW_RECIPE,
              "New Recipe Created",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channelNewRecipe.setDescription("This is channel new recipe");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channelNewRecipe);
        }

    }


}
