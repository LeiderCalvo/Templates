package co.edu.icesi.ecosistemas.multicastmoncadaexcersice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    String mensajito;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et_mensajito);
        button = findViewById(R.id.btn_enviar);
        mensajito="";

        if (mensajito==null){
            return;

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mensajito = editText.getText().toString();

            if (editText.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(),"Escriba alguito mij@",Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
                System.out.println(mensajito);
                Sender sender = new Sender(mensajito);
                sender.start();
                editText.setText("");
            }
        });

    }
    public class Sender extends Thread{

        String msj;

        public Sender(String msj){
            this.msj=msj;
        }

        @Override
        public void run() {
            try {

                System.out.println(msj+"esto se supone que envia");

                DatagramSocket socket = new DatagramSocket();
                int puerto= 5000;
                InetAddress adress = InetAddress.getByName("10.0.2.2");
                DatagramPacket packet = new DatagramPacket(msj.getBytes(),msj.getBytes().length,adress,puerto);
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

