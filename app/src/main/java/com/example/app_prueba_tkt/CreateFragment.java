package com.example.app_prueba_tkt;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {

    public static final int REQUEST_VIDEO_CAPTURE = 1;
    public static final int REQUEST_PERMISSIONS = 101;
    private Uri vireouri;
    public View view;
    public String userid;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
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

    //<com.google.android.material.button.MaterialButton

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_create, container, false);
        ButtonGrabar();

        return  view;
    }
    public void ButtonGrabar()
    {
        Button btnGrabarVideo = view.findViewById(R.id.grabar);
        btnGrabarVideo.setOnClickListener(v -> PermisosyGraba());
    }
    //para ver la galeria, pero esta sin configurar
//    public void ButtonStorage()
//    {
//        Button btnGalería = view.findViewById(R.id.btnGalería);
//
//    }
    private void PermisosyGraba() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
            }, REQUEST_PERMISSIONS);
        } else {
            launchCamera();
        }
    }
    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == getActivity().RESULT_OK) {
            vireouri = data.getData();
            uploadVideoToFirebase(vireouri);
        }
    }

    private void uploadVideoToFirebase(Uri uri) {

        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference videoRef = storageRef.child("videos/" + UUID.randomUUID().toString() + ".mp4");

        videoRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> videoRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
//                    FirebaseDatabase.getInstance().getReference("videos").push().setValue(downloadUri.toString());
                    String url = downloadUri.toString();

                    DatabaseReference fbref = FirebaseDatabase.getInstance().getReference("videos_por_user").child(userid);
                    String idvideo = fbref.push().getKey();

                    Map<String, Object> videoData = new HashMap<>();
                    videoData.put("videoURL", downloadUri.toString());
                    videoData.put("timestamp", System.currentTimeMillis());//esto no es necesario/depende
                    fbref.child(idvideo).setValue(videoData);

                    FirebaseDatabase.getInstance().getReference("videos_por_user").push().setValue(url);

                    Toast.makeText(getContext(), "Video subido correctamente", Toast.LENGTH_SHORT).show();
                }))
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al subir video", Toast.LENGTH_SHORT).show();
                });
    }
}