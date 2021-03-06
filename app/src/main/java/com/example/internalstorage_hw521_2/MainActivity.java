package com.example.internalstorage_hw521_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
public class MainActivity extends AppCompatActivity {
    public final static String loginFileName = "login";
    public final static String passwordFileName = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        final EditText mLoginEdTxt = findViewById(R.id.et_login);
        final EditText mPasswordEdTxt = findViewById(R.id.et_password);
        Button mLogin = findViewById(R.id.btn_login);
        Button mRegistration = findViewById(R.id.btn_registration);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nLogin = mLoginEdTxt.getText().toString();
                final String nPassword = mPasswordEdTxt.getText().toString();
                if (nLogin.isEmpty() || nPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean isLoginWritten = writeToFile(nLogin, loginFileName);
                boolean isPasswordWritten = writeToFile(nPassword, passwordFileName);
                if (isLoginWritten && isPasswordWritten) {
                    Toast.makeText(MainActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Данные не были записаны", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nLogin = mLoginEdTxt.getText().toString();
                final String nPassword = mPasswordEdTxt.getText().toString();
                if (nLogin.isEmpty() || nPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
                    return;
                }
                String savedLogin = readFromFile(loginFileName);
                String savedPassword = readFromFile(passwordFileName);
                if (nLogin.equals(savedLogin) && nPassword.equals(savedPassword)) {
                    Toast.makeText(MainActivity.this, "Логин и пароль правильный", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Логин и пароль неправильный", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean writeToFile(String str, String fileName) {
        // Создадим файл и откроем поток для записи данных
        // Обеспечим переход символьных потока данных к байтовым потокам.
        // Запишем текст в поток вывода данных, буферизуя символы так, чтобы обеспечить эффективную запись отдельных символов.
        // Осуществим запись данных
        // Закроем поток
        try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {
            bw.write(str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private String readFromFile(String fileName) {
        // Получим входные байты из файла которых нужно прочесть.
        // Декодируем байты в символы
        // Читаем данные из потока ввода, буферизуя символы так, чтобы обеспечить эффективную запись отдельных символов.
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);
        ) {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }
}