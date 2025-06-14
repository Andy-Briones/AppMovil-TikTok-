package com.example.app_prueba_tkt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_prueba_tkt.adapters.VideoProfileAdapter;
import com.example.app_prueba_tkt.entities.Usuario;
import com.example.app_prueba_tkt.entities.Video;
import com.example.app_prueba_tkt.entities.Video_Profile;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private List<Video_Profile> listavideos;
    private VideoProfileAdapter adapter;
    TextView Senombre;
    TextView nombreLogin;
    TextView siguiendologin;
    TextView seguidoresLogin;
    Button btnsalir;
    Button btnatras;
    MaterialButton btnAmigos;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
//
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnsalir = view.findViewById(R.id.btnSalir);
        btnatras = view.findViewById(R.id.btnRegresar);
        btnAmigos = view.findViewById(R.id.encontrarAmigos);
        nombreLogin = view.findViewById(R.id.nombreUsuario);
        siguiendologin = view.findViewById(R.id.siguiendo);
        seguidoresLogin = view.findViewById(R.id.seguidores);

        RecyclerView rvideosperfil = view.findViewById(R.id.rvVideo_profile);
        rvideosperfil.setVisibility(View.VISIBLE);
        rvideosperfil.setLayoutManager(new LinearLayoutManager(getContext()));

        listavideos = new ArrayList<>();
        adapter = new VideoProfileAdapter(listavideos);
        rvideosperfil.setAdapter(adapter);

        VerVideoPerfil();
        CargarDatosPerfil();
        Atras();
        Amigos();
        CerrarSesion();

        return view;
    }
    //Regresar atras cuando ves un perfil
    public void Atras()
    {
        btnatras.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });
    }
    //Salir
    public void CerrarSesion()
    {
        btnsalir.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireActivity(), SecondActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    public void VerVideoPerfil()
    {
        //String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String id;

        if (getArguments() != null && getArguments().containsKey("idUsuario")) {
            id = getArguments().getString("idUsuario");
        } else {
            id = FirebaseAuth.getInstance().getCurrentUser().getUid(); // fallback por si se abre desde otro lado
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("videos_por_user").child(id);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listavideos.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Video_Profile videoProfile = dataSnapshot.getValue(Video_Profile.class);
                    if (videoProfile != null)
                    {
                        listavideos.add(videoProfile);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void CargarDatosPerfil()
    {
        btnatras.setVisibility(View.GONE);
        String idlogin;

        //UID del usuario cuyo perfil se está viendo
        if (getArguments() != null && getArguments().containsKey("idUsuario"))
        {
            idlogin = getArguments().getString("idUsuario"); // el perfil que estás viendo
            btnatras.setVisibility(View.VISIBLE);
        }
        else
        {
            idlogin = FirebaseAuth.getInstance().getCurrentUser().getUid(); // tu propio perfil
        }
//        String idlogin;
//        if (getArguments() != null && getArguments().containsKey("idUsuario"))
//        {
//            idlogin = getInstance().getCurrentUser().getUid();
//        }
//        else
//        {
//            idlogin = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        }
        //Nombre en el Perfil, se pasa la referencia de firebase
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("usuarios").child(idlogin);
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String name = snapshot.child("nombreUsuario").getValue(String.class);
                    nombreLogin.setText(name != null ? name : "Usuario");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Número de siguiendo, aumenta el número si sigues a alguien
        DatabaseReference seguiref = FirebaseDatabase.getInstance().getReference("Follows").child(idlogin);
        seguiref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long numerosiguiendo = snapshot.getChildrenCount();
                siguiendologin.setText("Siguiendo\n"+ numerosiguiendo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Ver siguiendo
        siguiendologin.setOnClickListener(v -> {
            String id = getArguments() != null && getArguments().containsKey("idUsuario")
                    ? getArguments().getString("idUsuario")
                    : FirebaseAuth.getInstance().getCurrentUser().getUid();
            VerSegFragment fragment = VerSegFragment.newInstance(id, "siguiendo");
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragments, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        //Número de seguidores, aumenta el numero si te siguen
        DatabaseReference follref = FirebaseDatabase.getInstance().getReference("Follows");
        follref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long numeroseguidores = 0;
                for (DataSnapshot snapshots : snapshot.getChildren())
                {
                    if (snapshots.hasChild(idlogin))
                    {
                        numeroseguidores++;
                    }
                }
                seguidoresLogin.setText("Seguidores\n"+numeroseguidores);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Ver seguidores
        seguidoresLogin.setOnClickListener(v -> {
            String id = getArguments() != null && getArguments().containsKey("idUsuario")
                    ? getArguments().getString("idUsuario")
                    : FirebaseAuth.getInstance().getCurrentUser().getUid();

            VerSegFragment fragment = VerSegFragment.newInstance(id,"seguidores");
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragments, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
    public void Amigos()
    {
        btnAmigos.setOnClickListener(v ->
        {
            HomeFragment homeFragment = new HomeFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragments, homeFragment)
                    .addToBackStack(null)
                    .commit();
        }
        );
    }
}