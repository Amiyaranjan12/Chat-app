package com.example.meetup.ProfilePackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetup.ChatPackage.Chat_Activity;
import com.example.meetup.FriendsPackage.FindFrindes_Activity;
import com.example.meetup.R;
import com.example.meetup.SettingPackage.Setting_Activity;
import com.example.meetup.userModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class Profile_Activity extends AppCompatActivity {

    private EditText userName,userIntrest;
    private Button userProfileBtn;
    private TextView userImageBtn;
    private ImageView userImage;

    private LinearLayout PChat_btn,PfindF_Btn,psetting_btn;

    private FirebaseDatabase fDatabase;
    private StorageReference storageReference;

    private Uri imageUri;

    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);


        userName=findViewById(R.id.userName);
        userIntrest=findViewById(R.id.userIntrest);
        userProfileBtn=findViewById(R.id.userProfileBtn);
        userImageBtn=findViewById(R.id.userImageBtn);
        userImage=findViewById(R.id.userImage);
        PChat_btn=findViewById(R.id.PChat_btn);
        PfindF_Btn=findViewById(R.id.PfindF_Btn);
        psetting_btn=findViewById(R.id.psetting_btn);

        ///////////

        PChat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile_Activity.this, Chat_Activity.class);
                startActivity(intent);
            }
        });

        PfindF_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile_Activity.this, FindFrindes_Activity.class);
                startActivity(intent);
            }
        });

        psetting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile_Activity.this, Setting_Activity.class);
                startActivity(intent);
            }
        });


        fDatabase= FirebaseDatabase.getInstance();
        DatabaseReference dReference=fDatabase.getReference("users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());

        /////////

        dReference.child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link=snapshot.getValue(String.class);
                Picasso.get().load(link).resize(150, 150).
                        into(userImage);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //////image
        storageReference= FirebaseStorage.getInstance().getReference();
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"lollll",Toast.LENGTH_LONG).show();

                Dexter.withContext(Profile_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                                CropImage.activity()
                                        .setGuidelines(CropImageView.Guidelines.ON)
                                        .start(Profile_Activity.this);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                // Toast.makeText(getApplicationContext(),"lollll",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();




            }
        });




        userImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageUri !=null){

                    updateimage();
                }

                if (imageUri==null){
                    Toast.makeText(Profile_Activity.this,"Please click on image box",Toast.LENGTH_SHORT).show();

                }

            }
        });



        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userModel model=dataSnapshot.getValue(userModel.class);


                userName.setText(model.getName());
                userIntrest.setText(model.getHobby());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(EmployHome_Activity.this, "Errror", Toast.LENGTH_SHORT).show();

            }
        });






        userProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String name= userName.getText().toString();
                String intrest=userIntrest.getText().toString();



                /////////
                if (temp==null){

                    HashMap editProfilehashMap=new HashMap();

                    editProfilehashMap.put("name",name);
                    editProfilehashMap.put("hobby",intrest);


                    dReference.updateChildren(editProfilehashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(Profile_Activity.this,"Successfully updated",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (temp != null)
                {
                    HashMap editProfilehashMap=new HashMap();

                    editProfilehashMap.put("name",name);
                    editProfilehashMap.put("hobby",intrest);
                    editProfilehashMap.put("image",temp);


                    dReference.updateChildren(editProfilehashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(Profile_Activity.this,"Successfully updated",Toast.LENGTH_SHORT).show();
                        }
                    });
                }


                /////////



                //





            }
        });








        /////////
    }


    //////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                userImage.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
//

    public void updateimage() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Wait......");
        progressDialog.show();

        StorageReference uploader = storageReference.child("img" + System.currentTimeMillis());
        uploader.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                temp=String.valueOf(uri);

                                System.out.println(temp);
                                progressDialog.dismiss();

                                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                                // Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                            }
                        });




                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded :"+(int)percent+"%");
            }
        });


    } //



    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Profile_Activity.this, FindFrindes_Activity.class);
        startActivity(intent);
    }


    /////////
}