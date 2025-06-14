package com.example.app_prueba_tkt;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.app_prueba_tkt.adapters.FriendAdapter;
import com.example.app_prueba_tkt.adapters.UsuarioAdapter;
import com.example.app_prueba_tkt.adapters.VideoProfileAdapter;
import com.example.app_prueba_tkt.entities.Usuario;
import com.example.app_prueba_tkt.entities.Video;
import com.example.app_prueba_tkt.entities.Video_Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsFragment extends Fragment {
    List<Video_Profile> Videosamigos = new ArrayList<>();
    FriendAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
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
        View view = inflater.inflate(R.layout.fragment_friends, container, false);


        RecyclerView rvideosamigos = view.findViewById(R.id.rvVideoAmigos);
        rvideosamigos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FriendAdapter(getContext(), Videosamigos);
        rvideosamigos.setAdapter(adapter);

        CargarVideos();

        return view;
    }
    public void CargarVideos()
    {
        String idActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Follows").child(idActual);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Videosamigos.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String idSiguiendo = dataSnapshot.getKey();
                    DatabaseReference myref = FirebaseDatabase.getInstance()
                            .getReference("videos_por_user")
                            .child(idSiguiendo);
                    myref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren())
                            {
                                Video_Profile video = data.getValue(Video_Profile.class);
                                if (video != null)
                                {
                                    video.userId = idSiguiendo;
                                    Videosamigos.add(video);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}