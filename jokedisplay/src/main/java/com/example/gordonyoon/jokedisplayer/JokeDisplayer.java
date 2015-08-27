package com.example.gordonyoon.jokedisplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;


public class JokeDisplayer extends AppCompatActivity {
    private static final String EXTRA_JOKE = "jokeExtra";

    public static void start(Context context, String joke) {
        Intent intent = new Intent(context, JokeDisplayer.class);
        intent.putExtra(EXTRA_JOKE, joke);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_displayer);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String joke = extras.getString(EXTRA_JOKE);
            if (joke != null) {
                TextView jokeText = ButterKnife.findById(this, R.id.joke_text);
                jokeText.setText(joke);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_joke_displayer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
