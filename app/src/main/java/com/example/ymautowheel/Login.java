package com.example.ymautowheel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.UserModel;
import com.example.ymautowheel.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    TextView tvRegister;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        Session session = new Session(Login.this);
        String email = session.getEmail();

        if(email!=null){
            Intent pindah = new Intent(Login.this, MainActivity.class);
            startActivity(pindah);
        }

        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Login.this,Register.class);
                startActivity(pindah);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty()) {
                    etEmail.setError("Email harus diisi!");
                }
                if (password.isEmpty()) {
                    etPassword.setError("Password harus diisi!");
                }
                if (!email.isEmpty() && !password.isEmpty()) {

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> login = api.login(email, password);
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    login.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.body().getStatus().equals("1")) {
                                UserModel userModel = response.body().getUser();

                                Session session = new Session(Login.this);
                                session.setId(userModel.getId());
                                session.setNama(userModel.getNama());
                                session.setEmail(userModel.getEmail());

                                Intent pindah = new Intent(Login.this, MainActivity.class);
                                startActivity(pindah);
                                progressDialog.dismiss();
                            } else if (response.body().getStatus().equals("0")) {
                                Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(Login.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}