package com.example.xxx77xxzxcvmmnbbvvcx.charactersheetsproject;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link characters.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link characters#newInstance} factory method to
 * create an instance of this fragment.
 */
public class characters extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public characters() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment characters.
     */
    // TODO: Rename and change types and number of parameters
    public static characters newInstance(String param1, String param2) {
        characters fragment = new characters();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
    private ListView lv;
    public View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_characters, container, false);

        AdView adView = (AdView)myView.findViewById(R.id.adView);
        adView.setAdSize(AdSize.FULL_BANNER);

        populateListView();
        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCharacterCreation();
            }
        });
        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void populateListView(){
        lv = (ListView) myView.findViewById(R.id.list);
        List<String> myList = new ArrayList<>();

        try {
            AssetManager am = getContext().getAssets();
            InputStream is = am.open("characterNameList.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));

            String currName = bf.readLine();
            while (currName != null) {
                myList.add(currName);
                currName = bf.readLine();
            }
            myList.add("Foo");
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.show_names, R.id.listItem, myList);
            lv.setAdapter(myAdapter);

        }catch(IOException e){

        }


    }

    public void startCharacterCreation(){
        Intent intent = new Intent(getContext(), characterForm.class);
        startActivity(intent);
    }
}