package com.example.selfpills;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private Set<BluetoothDevice> devices;
    private static final String TAG = "MainActivity";
    private Handler handler = null;
    private LiaisonBluetooth liaisonESP;
    private ProtocolSelPills protocolSelPills;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create activity and define used layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        protocolSelPills = new ProtocolSelPills();

        initialiserHandler();
        initialiserLiaisonBluetooth();

        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                liaisonESP.envoyer("AHAHAHAHAHAH");
            }
        });
        }

    private void initialiserLiaisonBluetooth()
    {

        liaisonESP = new LiaisonBluetooth(ProtocolSelPills.NOM_MODULE, ProtocolSelPills.ADRESSE_MODULE, handler);
        liaisonESP.connecter();

    }

    private void initialiserHandler()
    {
        this.handler = new Handler(this.getMainLooper())
        {
            @Override
            public void handleMessage(@NonNull Message message)
            {
                Log.d(TAG, "[Handler] id du message = " + message.what);
                Log.d(TAG, "[Handler] contenu du message = " + message.obj.toString());

                switch (message.what)
                {
                    case LiaisonBluetooth.CREATION_SOCKET:
                        Log.d(TAG, "[Handler] CREATION_SOCKET = " + message.obj.toString());
                        break;
                    case LiaisonBluetooth.CONNEXION_SOCKET:
                        Log.d(TAG, "[Handler] CONNEXION_SOCKET = " + message.obj.toString());
                        break;
                    case LiaisonBluetooth.DECONNEXION_SOCKET:
                        Log.d(TAG, "[Handler] DECONNEXION_SOCKET = " + message.obj.toString());
                        break;
                    case LiaisonBluetooth.RECEPTION_TRAME:
                        break;
                }
            }
        };
    }

    }
