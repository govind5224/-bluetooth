package com.example.simplebluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DISCOVER_BT = 2;
    TextView blutoothstatechange, paireddevices;
    ImageView bluetoothimage;
    Button onbutton, offbutton, discoverablebutton, showpaireddevices;

    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blutoothstatechange = (TextView) findViewById(R.id.textViewname);
        paireddevices = (TextView) findViewById(R.id.showpaireddevices);

        bluetoothimage = (ImageView) findViewById(R.id.imageView);

        onbutton = (Button) findViewById(R.id.buttonon);
        offbutton = (Button) findViewById(R.id.buttonoff);
        discoverablebutton = (Button) findViewById(R.id.buttondiscoverable);
        showpaireddevices = (Button) findViewById(R.id.buttonpaired);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        blutoothstatechange.setText("Bluetooth is Off");


        if (bluetoothAdapter.isEnabled()) {
            bluetoothimage.setImageResource(R.drawable.ic_bluetooth_black_24dp);
        } else {
            bluetoothimage.setImageResource(R.drawable.ic_bluetooth_disabled_black_24dp);
        }

        onbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Turning On Bluetooth", Toast.LENGTH_SHORT).show();
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth is Already On!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        offbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter.isEnabled()){

                    bluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(),"Turning Off Bluetooth", Toast.LENGTH_SHORT).show();
                    blutoothstatechange.setText("Bluetooth is Off");
                    bluetoothimage.setImageResource(R.drawable.ic_bluetooth_disabled_black_24dp);

                } else {
                    Toast.makeText(getApplicationContext(),"Bluetooth is Already Off!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        discoverablebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!bluetoothAdapter.isDiscovering()){
                    Toast.makeText(getApplicationContext(),"Making Your Device Discoverable!", Toast.LENGTH_SHORT).show();
                    Intent discoverin = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(discoverin, REQUEST_DISCOVER_BT);
                    blutoothstatechange.setText("Bluetooth is On");
                    bluetoothimage.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Already Discoverable!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        showpaireddevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter.isEnabled()){
                    paireddevices.setText("Paired Devices");
                    Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                    for(BluetoothDevice device: devices){
                        paireddevices.append("\nDevices : " + device.getName()+","+device);
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Turn on Bluetooth First!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    blutoothstatechange.setText("Bluetooth is On");
                    bluetoothimage.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                    Toast.makeText(getApplicationContext(),"Bluetooth is On", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Could'nt able to Turn On Bluetooth!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "This Device Does not Supports Bluetooth", Toast.LENGTH_SHORT).show();
        }
    }
}

