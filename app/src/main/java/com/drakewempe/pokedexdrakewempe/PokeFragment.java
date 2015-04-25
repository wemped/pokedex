package com.drakewempe.pokedexdrakewempe;

import android.app.Activity;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PokeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PokeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int id;
    MediaPlayer mp;
    public ImageView fragmentPokeImage;// = (ImageView)v.findViewById(R.id.fragmentPokeImage);
    public TextView fragmentPokeName;// = (TextView)v.findViewById(R.id.fragmentPokeName);
    public TextView fragmentPokeDescrip;// = (TextView)v.findViewById(R.id.fragmentPokeDescrip);

    public TypedArray snds;
    public TypedArray imgs;// = getResources().obtainTypedArray(R.array.poke_imgs);
    public String[] name_dict;// = getResources().getStringArray(R.array.name_dict);
    public String[] descrip_dict;// = getResources().getStringArray(R.array.descrip_dict);
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PokeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PokeFragment newInstance(int id) {
        //int id=-1;
        //this.id =id;
        PokeFragment fragment = new PokeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }

    public PokeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_poke, container, false);

        /*Bundle args = getArguments();
        if (args!= null){
            id = args.getInt(ARG_PARAM1);
        }*/

        this.fragmentPokeImage = (ImageView)v.findViewById(R.id.fragmentPokeImage);
        this.fragmentPokeName = (TextView)v.findViewById(R.id.fragmentPokeName);
        this.fragmentPokeDescrip = (TextView)v.findViewById(R.id.fragmentPokeDescrip);

        this.snds = getResources().obtainTypedArray(R.array.poke_sounds);
        this.imgs = getResources().obtainTypedArray(R.array.poke_imgs);
        this.name_dict = getResources().getStringArray(R.array.name_dict);
        this.descrip_dict = getResources().getStringArray(R.array.descrip_dict);

        if (this.id != -1){
            this.fragmentPokeImage.setImageResource(imgs.getResourceId(id,-1));
            this.fragmentPokeName.setText(name_dict[id]);
            this.fragmentPokeDescrip.setText(descrip_dict[id]);
        }
        //return inflater.inflate(R.layout.fragment_poke, container, false);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void switchPoke(int id){
        this.fragmentPokeImage.setImageResource(imgs.getResourceId(id,-1));
        this.fragmentPokeName.setText(name_dict[id]);
        this.fragmentPokeDescrip.setText(descrip_dict[id]);
        this.mp = MediaPlayer.create(this.getActivity(), snds.getResourceId(id, -1));
        this.mp.start();
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(boolean fake);
    }

}
