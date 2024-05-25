package com.example.readkami_beta.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.MenuInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.readkami_beta.Activities.Journals_to_Articles.Gomanan;
import com.example.readkami_beta.Activities.Journals_to_Articles.Journal_of_Economy_and_Enterprise;
import com.example.readkami_beta.Activities.Journals_to_Articles.La_Ricerca;
import com.example.readkami_beta.Activities.Journals_to_Articles.The_Pendulum;
import com.example.readkami_beta.Activities.Journals_to_Articles.Tinubdan;
import com.example.readkami_beta.Activities.Journals_to_Articles.UM_Digos_Research_Journal;
import com.example.readkami_beta.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JournalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JournalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JournalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JournalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JournalFragment newInstance(String param1, String param2) {
        JournalFragment fragment = new JournalFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        CardView journal1 = (CardView) view.findViewById(R.id.journal1);
        CardView journal2 = (CardView) view.findViewById(R.id.journal2);
        CardView journal3 = (CardView) view.findViewById(R.id.journal3);
        CardView journal4 = (CardView) view.findViewById(R.id.journal4);
        CardView journal5 = (CardView) view.findViewById(R.id.journal5);
        CardView journal6 = (CardView) view.findViewById(R.id.journal6);

        //Clicking card view
        journal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Journal_of_Economy_and_Enterprise.class);
                startActivity(intent);
            }
        });
        journal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UM_Digos_Research_Journal.class);
                startActivity(intent);
            }
        });
        journal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Gomanan.class);
                startActivity(intent);

                String journalContent = getString(R.string.Journal3);
                Log.d("JournalContent", journalContent);

            }
        });
        journal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), The_Pendulum.class);
                startActivity(intent);
            }
        });
        journal5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), La_Ricerca.class);
                startActivity(intent);
            }
        });
        journal6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Tinubdan.class);
                startActivity(intent);
            }
        });

        return view;
    }

}