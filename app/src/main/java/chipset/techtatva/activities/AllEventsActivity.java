package chipset.techtatva.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import chipset.techtatva.R;
import chipset.techtatva.fragments.AllEvents;

public class AllEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AllEvents())
                    .commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_results:
                startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                break;
            case R.id.action_developers:
                Toast.makeText(getApplicationContext(), "Developers", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_contact:
                Toast.makeText(getApplicationContext(), "Contact Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_insta:
                startActivity(new Intent(getApplicationContext(),InstaFeedActivity.class));
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
