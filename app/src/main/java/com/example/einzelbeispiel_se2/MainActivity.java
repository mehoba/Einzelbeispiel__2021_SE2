package com.example.einzelbeispiel_se2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private Button sendButton;
    private TextView MNrText,outputText;
    private EditText MNrInput;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private OutputStream os;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendButton=findViewById(R.id.sendButton);
        MNrInput= findViewById(R.id.MNrInput);
        MNrText = findViewById(R.id.MNrText);
        outputText=findViewById(R.id.outputText);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MNrInput.getText().toString().isEmpty()){
                    outputText.setText("Bitte input MNr");
                }
                else
                {

                }
            }
        });


    }

    public void sendMessage(final String msg){
        outputText= findViewById(R.id.outputText);

        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket= new Socket("se2-isys.aau.at", 53212);
                    os= socket.getOutputStream();
                    pw= new PrintWriter(os,true);
                    pw.println(msg);
                    pw.flush();

                    br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    response= br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        })
    }


}