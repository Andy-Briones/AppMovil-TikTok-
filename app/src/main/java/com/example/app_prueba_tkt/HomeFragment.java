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
import android.widget.SearchView;

import com.example.app_prueba_tkt.adapters.FriendAdapter;
import com.example.app_prueba_tkt.adapters.HomeAdapter;
import com.example.app_prueba_tkt.adapters.PerfilAdapter;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    DatabaseReference refusers;
    HomeAdapter adapter;
    Button btnRegresar3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SearchView buscar = view.findViewById(R.id.sbuscador);
        RecyclerView rvusers = view.findViewById(R.id.rvamigos);
        rvusers.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Usuario> listaususarios = new ArrayList<>();
        adapter = new HomeAdapter(listaususarios, getParentFragmentManager(), getContext());
        rvusers.setAdapter(adapter);

        btnRegresar3 = view.findViewById(R.id.btnRegresar3);
        btnRegresar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        refusers = FirebaseDatabase.getInstance().getReference("usuarios");
        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscarusuarios(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarusuarios(newText);
                return true;
            }
        });
        return view;
    }
    private void buscarusuarios(String nombre)
    {
        refusers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Usuario> resultado = new ArrayList<>();
                for (DataSnapshot user: snapshot.getChildren())
                {
                    Usuario usuario = user.getValue(Usuario.class);
                    if (usuario != null && usuario.nombreUsuario.toLowerCase().contains(nombre.toLowerCase()))
                    {
                        resultado.add(usuario);
                    }
                }
                adapter.updateList(resultado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}