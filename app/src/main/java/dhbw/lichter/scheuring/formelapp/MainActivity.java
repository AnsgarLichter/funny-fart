package dhbw.lichter.scheuring.formelapp;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import dhbw.lichter.scheuring.formelapp.util.DatabaseManager;

public class MainActivity extends AppCompatActivity {

    protected AppBarConfiguration mAppBarConfiguration;
    private DatabaseManager dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Superklassen Konsturktor aufrufen
        super.onCreate(savedInstanceState);
        //Layout activity_main setzen
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseManager(this);

        //Datei für Toolbar bestimmen und als Toolbar setzen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Drawer Layout für Sidebar Navigation bestimmen
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //Sidebar bestimmen
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Menü ID jedes Menüs übergeben
        //Drawer Layout setzen
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_database, R.id.nav_help)
                .setDrawerLayout(drawer)
                .build();

        //Element für Navigationsinhalt setzen
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
