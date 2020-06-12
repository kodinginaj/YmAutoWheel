package com.example.ymautowheel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText etNama,etEmail,etPassword,etKPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etKPassword = findViewById(R.id.etKPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String kpassword = etKPassword.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (nama.isEmpty()) {
                    etNama.setError("Nama  harus diisi!");
                }
                if (email.isEmpty()) {
                    etEmail.setError("Email harus diisi!");
                } else if (!email.matches(emailPattern)) {
                    etEmail.setError("Email tidak sesuai format!");
                }
                if (password.isEmpty()) {
                    etPassword.setError("Password harus diisi!");
                } else if (password.length() < 6) {
                    etPassword.setError("Password minimal 6 karakter!");
                } else if (!password.equals(kpassword)) {
                    etPassword.setError("Password tidak sama!");
                }
                if (kpassword.isEmpty()) {
                    etKPassword.setError("Konfirmasi password harus diisi!");
                } else if (password.length() < 6) {
                    etKPassword.setError("Konfirmasi password minimal 6 karakter!");
                } else if (!kpassword.equals(password)) {
                    etKPassword.setError("Konfirmasi password tidak sama!");
                }
                if (!nama.isEmpty() && !email.isEmpty() && email.matches(emailPattern) && !password.isEmpty() && password.length() >= 6 && password.equals(kpassword) && !kpassword.isEmpty() && kpassword.length() >= 6 && kpassword.equals(password)) {

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> register = api.register(email,nama,password);
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(Register.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    register.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = new Intent(Register.this, Login.class);
                            startActivity(pindah);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(Register.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}