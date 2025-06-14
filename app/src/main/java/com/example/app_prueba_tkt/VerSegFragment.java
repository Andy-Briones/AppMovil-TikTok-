package com.example.app_prueba_tkt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.app_prueba_tkt.adapters.UsuarioAdapter;
import com.example.app_prueba_tkt.adapters.VerSegAdapter;
import com.example.app_prueba_tkt.entities.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerSegFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerSegFragment extends Fragment {
    private RecyclerView rview;
    private List<Usuario>listaseguidores;
    private VerSegAdapter adapter;
    private String iduser, tipo;
    Button btnRegresar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VerSegFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerSegFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerSegFragment newInstance(String param1, String param2) {
        VerSegFragment fragment = new VerSegFragment();
        Bundle args = new Bundle();
        args.putString("idUsuario", param1);
        args.putString("tipo", param2);
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
        View view = inflater.inflate(R.layout.fragment_ver_seg, container, false);

        rview = view.findViewById(R.id.recyclerUsuarios);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));
        listaseguidores = new ArrayList<>();
        adapter = new VerSegAdapter(listaseguidores);
        rview.setAdapter(adapter);

        btnRegresar = view.findViewById(R.id.btnRegresar2);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });


        if (getArguments() != null)
        {
            iduser = getArguments().getString("idUsuario");
            tipo = getArguments().getString("tipo");
            if (tipo.equals("siguiendo"))
            {
                CargarSiguiendo(iduser);
            } else if (tipo.equals("seguidores")) {
                CargarSeguidores(iduser);
            }
        }

        return view;
    }
    public void CargarSiguiendo(String iduser)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Follows").child(iduser);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren())
                {
                    String id = data.getKey();
                    CargarUsuarios(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void CargarSeguidores(String id)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Follows");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren())
                {
                    if (data.hasChild(iduser))
                    {
                        String id = data.getKey();
                        CargarUsuarios(id);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void CargarUsuarios(String id)
    {
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("usuarios").child(id);
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                if (usuario!= null)
                {
                    listaseguidores.add(usuario);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}