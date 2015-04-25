package com.drakewempe.pokedexdrakewempe;

import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class PokeActivity extends ActionBarActivity {

    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke);

        ImageView pokeImage = (ImageView)findViewById(R.id.pokeImage);
        TextView pokeName = (TextView)findViewById(R.id.pokeName);
        TextView pokeDescrip = (TextView)findViewById(R.id.pokeDescrip);

        TypedArray imgs = getResources().obtainTypedArray(R.array.poke_imgs);
        TypedArray snds = getResources().obtainTypedArray(R.array.poke_sounds);
        String[] name_dict = getResources().getStringArray(R.array.name_dict);
        String[] descrip_dict = getResources().getStringArray(R.array.descrip_dict);



        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int id = extras.getInt("id");
            pokeImage.setImageResource(imgs.getResourceId(id,-1));
            pokeName.setText(name_dict[id]);
            pokeDescrip.setText(descrip_dict[id]);
            this.mp = MediaPlayer.create(this, snds.getResourceId(id,-1));
            this.mp.start();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_poke, menu);
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
