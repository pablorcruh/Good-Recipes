package ec.com.pablorcruh.goodrecipes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.fragments.HomeFragment;
import ec.com.pablorcruh.goodrecipes.fragments.MyRecipesFragment;
import ec.com.pablorcruh.goodrecipes.fragments.NewRecipeFragment;
import ec.com.pablorcruh.goodrecipes.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {


    private MainViewModel mainViewModel;

    private FirebaseUser firebaseUser;

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
            bottomNavigation.setOnNavigationItemSelectedListener(navListener);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            }
            firebaseUser = mainViewModel.getCurrentUser();
            Toast.makeText(this, "Usuario"+firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
            LiveData<QuerySnapshot> liveData = mainViewModel.getAllRecipes(this);
            liveData.observe(this, new Observer<QuerySnapshot>() {
                @Override
                public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                    Log.d(TAG, "onChanged: ++++++++++++++++++++++"+queryDocumentSnapshots.size());
                }
            });
        } catch (FirebaseAuthException e) {
            Log.e(TAG, "onCreate: ",e);
            Toast.makeText(this, "Something went wrong on Authentication", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.e(TAG, "onCreate: ",e);
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
                mainViewModel.logout();
                Intent intent = new Intent(this, LoginActivity.class);
                finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
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
}
