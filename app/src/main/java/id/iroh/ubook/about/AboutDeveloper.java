package id.iroh.ubook.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import id.iroh.ubook.R;

public class AboutDeveloper extends Fragment implements View.OnClickListener{

    public AboutDeveloper() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_developer, container, false);

        Button btnInstagram= v.findViewById(R.id.btn_instagram);
        btnInstagram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/maratulbariroh3630/"));
                startActivity(intent);
            }
        });

        Button btnGmail= v.findViewById(R.id.btn_gmail);
        btnGmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"maratulbariroh3630@gmail.com"});

                try {
                    startActivity(Intent.createChooser(intent, "Want to Send Email ?"));
                } catch (android.content.ActivityNotFoundException ex) {
                    //do something else
                }
            }
        });

        Button btnLinkedin= v.findViewById(R.id.btn_linkedin);
        btnLinkedin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.linkedin.com/in/mar-atul-bariroh-979073189/"));
                startActivity(intent);
            }
        });

        Button btnGithub= v.findViewById(R.id.btn_github);
        btnGithub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/missmb"));
                startActivity(intent);
            }
        });
        return v;
    }
    @Override
    public void onClick(View view) {

    }

}
