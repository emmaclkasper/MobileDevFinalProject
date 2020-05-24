package edu.uga.cs.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity {

    EditText username,password,confirmPassword,email,whichRoomate;
    long maxID;
    Button register;
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        password= (EditText)findViewById(R.id.password);
        confirmPassword= (EditText)findViewById(R.id.confirmPass);
        email= (EditText)findViewById(R.id.email);
        register= (Button)findViewById(R.id.register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!(confirmPassword.getText().toString().equalsIgnoreCase(password.getText().toString()))){
                    Toast.makeText(Registration.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Registered user: " + email, Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(Registration.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(Registration.this, "Registration failed. Email is most likely in use or wrong format.", Toast.LENGTH_SHORT).show();
                                }
                            }
                });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                Intent intent = new Intent(this, MainActivity.class );
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


