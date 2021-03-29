package id.iroh.ubook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import id.iroh.ubook.DataBase.SQLiteHelper;
import id.iroh.ubook.about.AboutActivity;
import id.iroh.ubook.book.BookList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnmoveToAdd, btnmoveToList,btnmoveToAbout,close;
    private Toolbar toolbar;
    private CollapsingToolbarLayout coollapsingToolbarLayout;
    public static SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        sqLiteHelper = new SQLiteHelper(this, "BookDB.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS BOOK(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, author VARCHAR,description VARCHAR, image BLOG)");

        btnmoveToAdd.setOnClickListener(this);
        btnmoveToList.setOnClickListener(this);
        btnmoveToAbout.setOnClickListener(this);
        close.setOnClickListener(this);

        TextView nama = findViewById(R.id.tv_namaMain);
        nama.setText(Preferences.getLoggedInUser(getBaseContext()));
        findViewById(R.id.button_logoutMain).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Preferences.clearLoggedInUser(getBaseContext());
                startActivity(new
                        Intent(getBaseContext(),LoginActivity.class));
                finish();
            }
        });
        //tollbar();
        fab();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.moveToAdd:
                Intent moveIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(moveIntent);
                break;
            case R.id.moveToList:
                Intent moveList = new Intent(MainActivity.this, BookList.class);
                startActivity(moveList);
                break;
            case R.id.moveToAbout:
                Intent moveAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(moveAbout);
                break;
            case R.id.CloseButton:
                closeStatement();
                break;

        }
    }
    public void init(){
        btnmoveToAdd = (Button) findViewById(R.id.moveToAdd);
        btnmoveToList = (Button) findViewById(R.id.moveToList);
        btnmoveToAbout = (Button) findViewById(R.id.moveToAbout);
        close = (Button) findViewById(R.id.CloseButton);

    }

    public void tollbar(){
        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       coollapsingToolbarLayout = findViewById(R.id.collapse);
       coollapsingToolbarLayout.setContentScrimColor(getResources().getColor (R.color.three));
       coollapsingToolbarLayout.setTitle("Ubook for you ");
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void closeStatement(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void fab(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"maratul.18016@mhs.unesa.ac.id"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Email From UBook");
                intent.putExtra(Intent.EXTRA_TEXT, "Hi, This Email from UBook");

                try {
                    startActivity(Intent.createChooser(intent, "Want to Send Email ?"));
                } catch (android.content.ActivityNotFoundException ex) {
                    //do something else
                }

            }
        });
    }
}
