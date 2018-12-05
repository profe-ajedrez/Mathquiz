package org.database;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import org.config.Config;

import javax.annotation.Nullable;


public class FireStore_Adapter {
    private static final String COLLECTION_NAME = "settings";
    private static final String TAG = "FireStore_Adapter";

    private static Config mConfig;

    public FireStore_Adapter() {
    }

    public static Config loadConfig(@NonNull FirebaseFirestore db, @NonNull FirebaseAuth mAuth) {


        Log.d(TAG, mAuth.getCurrentUser().getEmail());
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(mAuth.getCurrentUser().getEmail());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    //Documento se encuentra
                    DocumentSnapshot document = task.getResult();
                    FireStore_Adapter.mConfig = document.toObject(Config.class);

                    if (FireStore_Adapter.mConfig == null) {

                        FireStore_Adapter.mConfig = new Config();
                        FireStore_Adapter.mConfig.setUserToken(mAuth.getCurrentUser().getEmail().trim());
                        Log.d(TAG, "Documento no hallado");
                    } else {
                        Log.d(TAG, "Documento hallado: " + document.getData());
                    }
                } else {

                    Log.d(TAG, "Documento no hallado");
                    FireStore_Adapter.mConfig = new Config();
                    FireStore_Adapter.mConfig.setUserToken(mAuth.getCurrentUser().getEmail().trim());
                }
            }

        });

        if (FireStore_Adapter.mConfig == null) {
            FireStore_Adapter.mConfig = new Config();
            FireStore_Adapter.mConfig.setUserToken(mAuth.getCurrentUser().getEmail());
        }
        return FireStore_Adapter.mConfig;
    }


    public static void writeConfig(FirebaseFirestore db, Config config) {
        Log.d(TAG, config.getUserToken());

        db.collection(COLLECTION_NAME).document(config.getUserToken())
                .set(config, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Documento grabado con EXITO! ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "ERROR grabando documento", e);
                    }
                });

        db.collection(COLLECTION_NAME).document(config.getUserToken()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Fetching after write " + String.valueOf(task.getResult().getData()));
                }
            }
        });
    }

    public static Config retrieve(FirebaseFirestore db, FirebaseAuth mAuth) {
        db.collection(COLLECTION_NAME).
                document(mAuth.getCurrentUser().getEmail()).
                addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                                            if (e != null) {
                                                // FireStore_Adapter.mConfig = new Config();
                                                // FireStore_Adapter.mConfig.setUserToken(mAuth.getCurrentUser().getEmail());
                                                Log.d("ERROR al cargar ", e.getMessage());
                                            } else {

                                                String source = documentSnapshot != null && documentSnapshot.getMetadata().hasPendingWrites()
                                                        ? "Local" : "Server";

                                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                                    FireStore_Adapter.mConfig = documentSnapshot.toObject(Config.class);
                                                    Log.d(TAG, source + " data: " + documentSnapshot.getData());
                                                } else {
                                                    Log.d(TAG, source + " data: null");
                                                }
                                            }
                                        }
                                    }
                );

        if (FireStore_Adapter.mConfig == null) {
            FireStore_Adapter.mConfig = new Config();
            FireStore_Adapter.mConfig.setUserToken(mAuth.getCurrentUser().getEmail());
        }
        return FireStore_Adapter.mConfig;

    }

}