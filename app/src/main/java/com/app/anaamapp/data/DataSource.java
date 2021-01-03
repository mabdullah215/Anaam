package com.app.anaamapp.data;
import android.content.Context;
import androidx.annotation.NonNull;
import com.app.anaamapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DataSource
{
    private static DataSource single_instance = null;
    RequestListener listener;
    User currentUser;
    Context mContext;

    public interface RequestListener
    {
        void onUpdate(String response);
    }
    public static DataSource getInstance(Context context)
    {
        if (single_instance == null) single_instance = new DataSource(context);
        return single_instance;
    }
    private DataSource(Context context)
    {
        mContext=context;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public void getUserInformation()
    {
        DatabaseReference mUserRef= FirebaseDatabase.getInstance().getReference().child("Users");
        String id= FirebaseAuth.getInstance().getUid();
        mUserRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                currentUser=snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
