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
    private Button sendButton, aufgabeButton;
    private TextView MNrText,outputText, arrayOuput;
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
        aufgabeButton= findViewById(R.id.aufgabeButton);
        /*aufgabeButton.setVisibility(View.INVISIBLE);*/
        arrayOuput= findViewById(R.id.arrayOutput);

        aufgabeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayOuput.setText(printArray(setArray(MNrInput.getText().toString())));

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MNrInput.getText().toString().isEmpty()){
                    outputText.setText("Bitte input MNr");
                }
                else
                {
                    sendMessage(MNrInput.getText().toString());
                  /*  aufgabeButton.setVisibility(View.VISIBLE);*/

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
            });
        t.start();
        try{
            t.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outputText.setText(response);
            }
        });
        }



        private int [] sortArray(int [] a){
        for(int i=1; i<a.length; i++){
            if(a[i]<a[i-1]){
                int temp=a[i];
                a[i]= a[i-1];
                a[i-1]= temp;
                sortArray(a);
            }
        }
            return a;
        }

    private int[] setArray(String s){
        int counter=0;
        String [] numStr = s.split("");
        int [] num = new int [numStr.length];
        for(int i=0; i<num.length;i++){
            if(checkIfPrime(Integer.parseInt(numStr[i]))==true){
                num[i]=0;
                counter++;
            }
            else{
            num[i]= Integer.parseInt(numStr[i]);}
        }

        int [] noPrime = new int[num.length-counter];
        counter=0;
        for(int i=0; i<num.length; i++){
            if(num[i] != 0){
                noPrime[counter]=num[i];
                counter++;
        }}
       return sortArray(noPrime);
    }


    private boolean checkIfPrime(int n){
        if(n==0 || n==1 || n==2){
            return true;
        }
      
        for(int i=2; i<n;i++){
            if(n%i == 0){
                return false;
            }
        }
        return true;
    }

    private String printArray(int []a){
        String s="";
        for(int i=0; i<a.length;i++){
            s=s+" "+a[i];
        }
        return s;
    }
    }





