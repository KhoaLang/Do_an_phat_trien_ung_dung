package com.example.landview;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landview.AppUser.AppUser;
import com.example.landview.AppUser.UserInfo;
import com.example.landview.UserFragmentSection.UserPlansActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;


public class UserFragment extends Fragment {

    private static final int GALLERY_IMAGE_REQUEST = 11;
    private static final int CAMERA_IMAGE_REQUEST = 22;

    private ImageView imguser;
    private TextView txtName,txtInformation;
    private Button btnLogout, btnChangePass, btnPolicy, btnPlan;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private StorageReference avatarStorageRef;
    AppUser appUser = AppUser.getInstance();

    private UserInfo userInfo;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setTitle("Loading");
        progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                                            WindowManager.LayoutParams.MATCH_PARENT);
        progressDialog.setCancelable(false);

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user, container, false);
        imguser = view.findViewById(R.id.imgUser);
        txtName = view.findViewById(R.id.userName);
        txtInformation = view.findViewById(R.id.inforUser);
        btnChangePass = view.findViewById(R.id.btnChange);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnPolicy = view.findViewById(R.id.btnPolicy);
        btnPlan = view.findViewById(R.id.btnPlan);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        String path = "avatar/"+ mAuth.getUid();
        avatarStorageRef = storage.getReference(path);

        //Lấy username từ firestore
       // takeUserName();

        //sét sự kiện ảnh
        imguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), SplashScreen.class));
            }
        });

        btnPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TermsOfServices.class));
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChangePassword.class));
            }
        });
        btnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), UserPlansActivity.class));
            }
        });

        // Lấy avatar

        //getImageAvatar();

        getUserData();




        return view;
    }

    private void getUserData(){
        progressDialog.show();
        db.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists() && document != null){
                        userInfo = document.toObject(UserInfo.class);

                        // Load ảnh vào imgUser, và gọi callback khi load xong
                        Picasso.get().load(userInfo.getAvatar()).into(imguser, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onError(Exception e) {
                                progressDialog.dismiss();
                            }
                        });

                        // Load tên user
                        txtName.setText(userInfo.getUsername());
                    }
                } else {
                    progressDialog.dismiss();
                }
            }
        });
    }


    private TextView tvSeeAvatar;
    private TextView tvNewAvatar;
    AlertDialog avatarDialog;

    private void displayDialog() {
        // Tạo AlertDialog Builder
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_custom_dialog_user, null);

        tvNewAvatar = view.findViewById(R.id.tv_new_avatar);
        tvSeeAvatar = view.findViewById(R.id.tv_see_avatar); // Hmmm chưa dùng đến
        // Set custom view cho builder
        builder.setView(view);

        tvNewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayChooseImageDialog();
            }
        });

        // Tạo AlertDialog Object
        avatarDialog = builder.create();
        // Hiển thị dialog lên
        avatarDialog.show();

    }

    private AlertDialog chooseImageDialog;
    private TextView tvGallery, tvCamera;
    private Uri mImageUri;

    private void displayChooseImageDialog(){
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_custom_choose_avatar_dialog, null);
        tvGallery = view.findViewById(R.id.tv_gallery);
        tvCamera = view.findViewById(R.id.tv_camera);
        builder.setView(view);

        chooseImageDialog = builder.create();
        chooseImageDialog.getWindow().setLayout(300, 250);
        chooseImageDialog.show();

        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Pick image from gallery", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GALLERY_IMAGE_REQUEST);
            }
        });

        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "Pick image from camera", Toast.LENGTH_SHORT).show();

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "Temp picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Temp description");
                mImageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(cameraIntent, CAMERA_IMAGE_REQUEST);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_IMAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                    mImageUri = data.getData();
                    showUploadedAvatarDialog();
                }
                break;
            case CAMERA_IMAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getContext(), "Load ảnh vào", Toast.LENGTH_SHORT).show();
                    showUploadedAvatarDialog();
                }
                break;
        }
    }

    private AlertDialog uploadAvatarDialog;
    private ImageView ivUploadedAvatar;
    private void showUploadedAvatarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.layout_custom_upload_dialog, null);

        ivUploadedAvatar = view.findViewById(R.id.iv_uploaded_avatar);
        Picasso.get().load(mImageUri).fit().into(ivUploadedAvatar);
        builder.setView(view);

        builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Upload click", Toast.LENGTH_SHORT).show();
                uploadAvatarDialog.dismiss();
                chooseImageDialog.dismiss();
                avatarDialog.dismiss();
                progressDialog.show();
                upload();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Cancel click", Toast.LENGTH_SHORT).show();

            }
        });
        uploadAvatarDialog = builder.create();
        uploadAvatarDialog.getCurrentFocus();
        uploadAvatarDialog.show();


    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }

    private void upload(){
        if(mImageUri != null){

            StorageReference fileRef = avatarStorageRef.child(System.currentTimeMillis() + "."+getFileExtension(mImageUri));

            fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Upload success", Toast.LENGTH_SHORT).show();

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());
                    Uri downloadUri = uriTask.getResult();

                    Picasso.get().load(mImageUri).into(imguser, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(Exception e) {
                            progressDialog.dismiss();
                        }
                    });

                    db.collection("users").document(mAuth.getUid()).update("avatar", downloadUri.toString());
                    appUser.setAvatar(downloadUri.toString());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            });
        }
    }



}