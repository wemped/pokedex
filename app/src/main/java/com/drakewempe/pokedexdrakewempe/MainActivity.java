package com.drakewempe.pokedexdrakewempe;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;




public class MainActivity extends ActionBarActivity implements PokeFragment.OnFragmentInteractionListener {

    public String[] name_dict;
    int name_dict_len;
    PokeListItem[] pokemon;
    //public PokeFragment currFragment = null;
    MediaPlayer mp;

    public class PokeListItem{
        public String name;
        public int image_resource;
        public int id;

        public PokeListItem(String name, int image, int id){
            this.name = name;
            this.id = id;
            this.image_resource = image;
        }
    }

    public class PokeListItemAdapter extends ArrayAdapter<PokeListItem>{
        public PokeListItemAdapter(Context context, ArrayList<PokeListItem> pokeListItems){
            super(context,0,pokeListItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            PokeListItem item = getItem(position);
            if(convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.poke_list_item,parent,false);
            }

            TextView pkname = (TextView) convertView.findViewById(R.id.pkname);
            ImageView pkimage = (ImageView) convertView.findViewById(R.id.pkimage);

            pkname.setText(item.name);
            pkimage.setImageResource(item.image_resource);

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getSharedPreferences("MyPrefs", Context.MODE_WORLD_WRITEABLE);
        int length = prefs.getInt("media_length",0);
        this.mp = MediaPlayer.create(this, R.raw.theme_song);
        //if(mp.isPlaying()){
            //nuthin
        //}else{
            if (length == 0){
                Log.v("media_length", "is 0");
                mp.start();
            }else{
                mp.seekTo(length);
                mp.start();
            }
        //}

        //mp.start();

        //savedInstanceState.get
        /*if(currFragment == null){
            Log.v("currFragment", "is null");
        }else{
            Log.v("currFragment", "is NOT null");

        }*/



        Resources res = getResources();
        /*if (res.getBoolean(R.bool.dual_pane)){
            RelativeLayout fragView = (RelativeLayout)findViewById(R.id.fragmentPokeView);
            fragView.removeAllViews();
        }*/


        ArrayList<PokeListItem> arrayOfPoke = new ArrayList<PokeListItem>();
        PokeListItemAdapter adapter = new PokeListItemAdapter(this,arrayOfPoke);
        ListView listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);

        name_dict = getResources().getStringArray(R.array.name_dict);
        name_dict_len = name_dict.length;
        TypedArray imgs = getResources().obtainTypedArray(R.array.poke_imgs);

        for (int i=0;i<name_dict_len;i++){
            PokeListItem newPoke = new PokeListItem(name_dict[i], imgs.getResourceId(i,-1), i);
            adapter.add(newPoke);
        }

        /*if (res.getBoolean(R.bool.dual_pane)){
            RelativeLayout fragView = (RelativeLayout)findViewById(R.id.fragmentPokeView);
            fragView.removeAllViews();
        }*/
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                final PokeListItem item = (PokeListItem) parent.getItemAtPosition(position);
                Log.v("??", item.name.toString());
                Resources res = getResources();
                if(res.getBoolean(R.bool.dual_pane) == false){
                    Intent intent = new Intent();
                    intent.setClass(parent.getContext(),PokeActivity.class);
                    intent.putExtra("id", item.id);
                    startActivity(intent);
                }else{
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //PokeFragment newPokeFragment = new PokeFragment();

                    RelativeLayout fragView = (RelativeLayout)findViewById(R.id.fragmentPokeView);
                    //fragView.removeAllViews();
                    if (fragView.getChildCount() > 0){
                        //RelativeLayout oldFrag = (RelativeLayout)findViewById(R.id.fragmentPokeView);
                        PokeFragment newPokeFragment = (PokeFragment)fragmentManager.findFragmentByTag(res.getString(R.string.poke_frag_id));
                        newPokeFragment.switchPoke(item.id);
                    } else{
                        PokeFragment newPokeFragment = new PokeFragment();
                        Bundle fragBundle = new Bundle();
                        fragBundle.putInt("id",item.id);
                        newPokeFragment.setArguments(fragBundle);

                        fragmentTransaction.add(R.id.fragmentPokeView,newPokeFragment,res.getString(R.string.poke_frag_id));
                    }
                    fragmentTransaction.commit();
                }




            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onFragmentInteraction(boolean fake){
        //nuthin?
    }

    @Override
    protected void onPause(){
        super.onPause();
        this.mp.pause();
        int length = mp.getCurrentPosition();
        SharedPreferences prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        prefs.edit().putInt("media_length",length).apply();
        Log.v("onPause", "pausing and saving length..");
    }
    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences prefs = this.getSharedPreferences("MyPrefs", Context.MODE_WORLD_WRITEABLE);
        int length = prefs.getInt("media_length",0);
        //this.mp = MediaPlayer.create(this, R.raw.theme_song);
        if (this.mp.isPlaying()){
            Log.v("onResume", "isplaying is true");
        }else{
            Log.v("onResume", "isplaying is false");
            if (length == 0){
                mp.start();
            }else{
                Log.v("onResume", "length has a nonzero value");

                mp.seekTo(length);
                mp.start();
            }
        }


    }
}
