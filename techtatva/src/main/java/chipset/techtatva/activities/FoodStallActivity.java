package chipset.techtatva.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

import chipset.techtatva.R;

/**
 * Developer: chipset
 * Package : chipset.techtatva.activities
 * Project : Techtatva15
 * Date : 3/10/15
 */
public class FoodStallActivity extends AppCompatActivity {

    private int images[] = {R.drawable.pizza, R.drawable.shawarma, R.drawable.gola, R.drawable.chaat, R.drawable.flavours, R.drawable.teaze};
    private String titles[] = {"Pizza Hut", "Shawarma", "Gola", "Chaat", "Flavours 24", "Teaze"};
    private String description[] = {"Pizza Hut", "Shawarma", "Gola", "Chaat", "Flavours 24", "Teaze"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_stall);
        Random random = new Random();
        int selected = random.nextInt(6);

        ImageView foodImageView = (ImageView) findViewById(R.id.food_image_view);
        TextView titleTextView = (TextView) findViewById(R.id.title_text_view);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_text_view);

        Picasso.with(getApplicationContext()).load(images[selected]).into(foodImageView);
        titleTextView.setText(titles[selected]);
        descriptionTextView.setText(description[selected]);
    }
}
