package com.dementev.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private static final String LOGIN_FILE = "login.txt";
    private static final String PASS_FILE = "pass.txt";

    private EditText enterLogin;
    private EditText enterPass;
    private Button loginBtn;
    private Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        enterLogin = findViewById(R.id.enterLogin);
        enterPass = findViewById(R.id.enterPass);
        loginBtn = findViewById(R.id.loginBtn);
        regBtn = findViewById(R.id.regBtn);

        regBtn.setOnClickListener(v -> {
            String editLogin = enterLogin.getText().toString();
            String editPass = enterPass.getText().toString();
            saveIntoInternalStorage(editLogin, editPass);
        });

        loginBtn.setOnClickListener(v -> {
            String editLogin = enterLogin.getText().toString();
            String editPass = enterPass.getText().toString();
            String saveLogin = readFromInternalStorage(LOGIN_FILE);
            String savePass = readFromInternalStorage(PASS_FILE);

            if (editLogin.equals(saveLogin) && editPass.equals(savePass)){
                Toast.makeText(this, "Данные верны", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Неверные данные", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String readFromInternalStorage(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void saveIntoInternalStorage(String editLogin, String editPass) {
        BufferedWriter loginWriter = null;
        BufferedWriter passWriter = null;
        try {
            loginWriter = new BufferedWriter(new OutputStreamWriter(openFileOutput(LOGIN_FILE, Context.MODE_PRIVATE)));
            passWriter = new BufferedWriter(new OutputStreamWriter(openFileOutput(PASS_FILE, Context.MODE_PRIVATE)));
            loginWriter.write(editLogin);
            passWriter.write(editPass);
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        } finally {
            if (loginWriter != null || passWriter != null){
                try {
                    loginWriter.close();
                    passWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}