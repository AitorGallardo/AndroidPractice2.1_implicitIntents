package com.example.aitor.implicit_intents;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;


    EditText input;
    EditText input2;
    String intputText;

    Button WebButton;
    Button CallButton;
    Button MapsButton;
    Button CameraButton;
    Button MailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        intputText = "";


        // intent = new Intent(Intent.ACTION_DIAL);



        WebButton = (Button) findViewById(R.id.button_web);
        WebButton.setOnClickListener(this);
        CallButton = (Button) findViewById(R.id.button_phone);
        CallButton.setOnClickListener(this);
        MapsButton = (Button) findViewById(R.id.button_maps);
        MapsButton.setOnClickListener(this);
        CameraButton = (Button) findViewById(R.id.button_camera);
        CameraButton.setOnClickListener(this);
        MailButton = (Button) findViewById(R.id.button_mail);
        MailButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_web:
                navigate();
                break;
            case R.id.button_phone:
                phoneCall();
                break;
            case R.id.button_maps:
                openMap();
                break;
            case R.id.button_camera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
                break;
            case R.id.button_mail:

                    mailForm();

                break;
        }
    }

    public void navigate(){
        AlertDialog.Builder innputAlert = new AlertDialog.Builder(this);

        innputAlert.setTitle("WEB BROWSER");
        innputAlert.setMessage("URL: ");

        input = new EditText(this);
        input.setText("http://");
        input.setSelection(input.getText().length());
        innputAlert.setView(input);
        // innputAlert.setView(input);
        innputAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intputText = input.getText().toString();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(intputText));
                startActivity(intent);
            }
        });
        innputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                // System.runFinalization();
            }
        });
        innputAlert.show();
    }

    public void phoneCall(){
        AlertDialog.Builder innputAlert = new AlertDialog.Builder(this);

        innputAlert.setTitle("PHONE CALL");
        innputAlert.setMessage("Number: ");

        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        intputText = "tel:";
        innputAlert.setView(input);
        // innputAlert.setView(input);
        innputAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intputText = intputText.concat(input.getText().toString());
                intent = new Intent(Intent.ACTION_CALL,Uri.parse(intputText));
                startActivity(intent);
            }
        });
        innputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                // System.runFinalization();
            }
        });
        innputAlert.show();
    }

    public void openMap(){
        AlertDialog.Builder innputAlert = new AlertDialog.Builder(this);
        View form = getLayoutInflater().inflate(R.layout.map_coord,null);
        final EditText LATITUDE = (EditText)form.findViewById(R.id.latitude_input);
        LATITUDE.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        final EditText LONGITUDE = (EditText)form.findViewById(R.id.longitude_input);
        LONGITUDE.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        innputAlert.setTitle("MAPS");
        innputAlert.setView(form);

        innputAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    String latitude =LATITUDE.getText().toString();
                    String longitude = LONGITUDE.getText().toString();
                    String info = "geo:";
                    info = info.concat(latitude).concat(", ").concat(longitude);

                    intent = new Intent(Intent.ACTION_VIEW,Uri.parse(info));
                    startActivity(intent);
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }


        });
        innputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        innputAlert.show();
    }



    public void mailForm(){
        AlertDialog.Builder innputAlert = new AlertDialog.Builder(this);
        View form = getLayoutInflater().inflate(R.layout.dialog,null);
        final EditText SUBJECT = (EditText)form.findViewById(R.id.subjectText);
        final EditText BODY = (EditText)form.findViewById(R.id.bodyText);
        final EditText MAIL = (EditText)form.findViewById(R.id.mailText);
        innputAlert.setTitle("SEND A MAIL");
        innputAlert.setView(form);

        innputAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    String subject =SUBJECT.getText().toString();
                    String body = BODY.getText().toString();
                    String[] mail = {MAIL.getText().toString()}; // MAIL.getText().toString()

                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("message/rfc822"); // SETTING TYPE TO MAIL TO REDUCE APPS SUGGESTIONS TO OPEN
                        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                        intent.putExtra(Intent.EXTRA_TEXT, body);
                        intent.putExtra(Intent.EXTRA_EMAIL, mail);
                        // Intent chooserIntent = Intent.createChooser(intent,"Send Email"); //CHOSE BETWEEN MORE APPS TO OPEN

                            startActivity(intent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();

                        } catch (Exception e){
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }


        });
        innputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        innputAlert.show();
    }
}
