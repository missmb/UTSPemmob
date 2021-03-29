package id.iroh.ubook.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import id.iroh.ubook.R;

public class AboutActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    AboutAdapter aboutAdapter;
    String app, dev;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_act);

        app = "APP";
        dev = "DEVELOPER";
        tabLayout = (TabLayout) findViewById(R.id.tl_about);
        viewPager = (ViewPager) findViewById(R.id.vp_about);

        aboutAdapter = new AboutAdapter(getSupportFragmentManager());
        viewPager.setAdapter(aboutAdapter);

        aboutAdapter.AddFragment(new AboutApp(), app);
        aboutAdapter.AddFragment(new AboutDeveloper(), dev);
        viewPager.setAdapter(aboutAdapter);
        tabLayout.setupWithViewPager(viewPager);
//
//        setUpViewPager(viewPager);


    }
    private void setUpViewPager(ViewPager viewPager){
        aboutAdapter = new AboutAdapter(getSupportFragmentManager());
        aboutAdapter.AddFragment(new AboutApp(), app);
        aboutAdapter.AddFragment(new AboutDeveloper(), dev);

        viewPager.setAdapter(aboutAdapter);
    }
//    public AboutActivity() {
//        // Required empty public constructor
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.about_act, container, false);
//        tabLayout = v.findViewById(R.id.tl_about);
//        viewPager = v.findViewById(R.id.vp_about);
//
//        aboutAdapter = new AboutAdapter(getChildFragmentManager());
//        aboutAdapter.AddFragment(new AboutApp(), "Application");
//        aboutAdapter.AddFragment(new AboutDeveloper(), "Developer");
//
//        viewPager.setAdapter(aboutAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//
//        return v;
//    }
}
