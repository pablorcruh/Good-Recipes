package ec.com.pablorcruh.goodrecipes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.common.SharedPreferencesManager;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import ec.com.pablorcruh.goodrecipes.fragments.HomeFragment;
import ec.com.pablorcruh.goodrecipes.fragments.MyRecipesFragment;
import ec.com.pablorcruh.goodrecipes.fragments.NewRecipeFragment;
import ec.com.pablorcruh.goodrecipes.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {


    private MainViewModel mainViewModel;

    private FirebaseUser firebaseUser;

    private boolean isUserLoggedIn;

    private static final String TAG = MainActivity.class.getName();

    private Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_main);
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            SharedPreferencesManager.setSomeStringValue(Constants.PREF_EMAIL, firebaseUser.getEmail());
            mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
            bottomNavigation.setOnNavigationItemSelectedListener(navListener);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            }

        }catch(Exception e){
            Crashlytics.log(e.getMessage());
            Log.e(TAG, "onCreate: ", e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                isUserLoggedIn = SharedPreferencesManager.getSomeBooleanValue(Constants.PREF_IS_USER_LOGGED_IN);
                if (isUserLoggedIn) {
                    mainViewModel.logout();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }else{
                 startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_add_new:
                            selectedFragment = new NewRecipeFragment();
                            break;
                        case R.id.nav_list_my_recipes:
                            selectedFragment = new MyRecipesFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
