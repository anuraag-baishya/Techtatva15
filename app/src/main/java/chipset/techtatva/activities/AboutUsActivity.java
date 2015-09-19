package chipset.techtatva.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import chipset.potato.Potato;
import chipset.techtatva.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String fb_url="https://www.facebook.com/MITtechtatva";
        final String twitter_url ="https://www.twitter.com/MITtechtatva";
        final String insta_url = "https://www.instagram.com/MITtechtatva";
        final String youtube_url ="https://www.youtube.com/TechTatva";
        final String gplus_url = "https://plus.google.com/+TechTatva";
        String aboutContent = "TechTatva is the annual, student run, National Level Technical Fest of Manipal Institute of Technology, Manipal. It is one of the largest technical fests in the South Zone of the country, witnessing participation from various prestigious institutes from across the nation. TechTatva comprises a plethora of events, ranging across the various branches of engineering.\n" +
                "\n" +
                "Frugal Innovation – Do More with Less, the theme for this year seeks to extend the mindset of Jugaad, derived from the common Indian experience of innovating frugal, homespun, and simple solutions to the myriad problems that beset everyday life. This October 7th to 10th, TechTatva'15 aims to bear witness to a revamped Jugaadu methodology. ";


        TextView aboutTT_text_view = (TextView)findViewById(R.id.aboutTT);
        aboutTT_text_view.setText(aboutContent);
        ImageView fb,insta,twitter,gplus,youtube;
        fb = (ImageView)findViewById(R.id.fb_image);
        insta = (ImageView)findViewById(R.id.insta_image);
        twitter = (ImageView)findViewById(R.id.twitter_image);
        gplus = (ImageView)findViewById(R.id.gplus_image);
        youtube = (ImageView)findViewById(R.id.youtube_image);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate().Intents().browserIntent(AboutUsActivity.this,fb_url);
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate().Intents().browserIntent(AboutUsActivity.this,insta_url);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate().Intents().browserIntent(AboutUsActivity.this,twitter_url);
            }
        });
        gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate().Intents().browserIntent(AboutUsActivity.this,gplus_url);
            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate().Intents().browserIntent(AboutUsActivity.this,youtube_url);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_results:
                startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                break;
            case R.id.action_developers:
                Toast.makeText(getApplicationContext(), "Developers", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_contact:
                Toast.makeText(getApplicationContext(), "Contact Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_favourites:
                startActivity(new Intent(getApplicationContext(), FavouritesActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
