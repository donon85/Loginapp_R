package michalm.loginapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static michalm.loginapp.Tab2Zpracy.id_szczegoly;
import static michalm.loginapp.TabMain.mViewPager;


/**
 * Created by Michał on 2017-01-01.
 */

public class Tab3Zproblem extends Fragment  {
    private Context context;
    public byte[] image;
    public byte[] image2;
    int i=0;
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3zproblem, container, false);
        context = getActivity();
        final DatabaseHelper dataHelper = new DatabaseHelper(context);
        //pola tekstowe
        final EditText editPodsumowanie, editSzczegoly;

        editPodsumowanie = (EditText) rootView.findViewById(R.id.editTextPodsumowanie);
        editSzczegoly = (EditText) rootView.findViewById(R.id.editTextSzczegoly);

        //spinnery

        final Spinner spinner1 = (Spinner) rootView.findViewById(R.id.spinnerTZgloszenia);
        final Spinner spinner2 = (Spinner) rootView.findViewById(R.id.spinnerLokalizacja);
        final Spinner spinner3 = (Spinner) rootView.findViewById(R.id.spinnerZasob);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(context, R.array.typ_zgloszenia, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context, R.array.lokalizacja, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(context, R.array.zasob, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);



        ImageButton imgButtonCamera;
        imgButtonCamera = (ImageButton) rootView.findViewById(R.id.imageBtnAparatZP);
        imgButtonCamera.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePictureIntent, 2);
                        takePictureIntent.setType("image/*");
                        takePictureIntent.putExtra("crop", "true");
                    }
                }
        );

        ImageButton imageButtonGallery;
        imageButtonGallery = (ImageButton) rootView.findViewById(R.id.imageBtnGaleriaZP);
        imageButtonGallery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, 1);

                    }
                }
        );

        Button btnAddData;
        btnAddData = (Button) rootView.findViewById(R.id.buttonDodajZgl);
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i == 1) {
                            boolean isInserted = dataHelper.insertZlecenie(spinner1.getSelectedItem().toString(), spinner2.getSelectedItem().toString(),
                                    spinner3.getSelectedItem().toString(), image, editPodsumowanie.getText().toString(), editSzczegoly.getText().toString());
                            if (isInserted == true) {
                                Toast.makeText(context, "Dodano zgłoszenie", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(context, TabMain.class);
                                i.putExtra("tab_index", "1");
                                startActivity(i);

                            } else
                                Toast.makeText(context, "Dodawanie zgloszenia nie powiodlo się!", Toast.LENGTH_LONG).show();

                        }
                        else if (i == 2) {
                            boolean isInserted2 = dataHelper.insertZlecenie(spinner1.getSelectedItem().toString(), spinner2.getSelectedItem().toString(),
                                    spinner3.getSelectedItem().toString(), image2, editPodsumowanie.getText().toString(), editSzczegoly.getText().toString());
                            if (isInserted2 == true) {
                                Toast.makeText(context, "Dodano zgłoszenie", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(context, TabMain.class);
                                i.putExtra("tab_index", "1");
                                startActivity(i);

                            } else
                                Toast.makeText(context, "Dodawanie zgloszenia nie powiodlo się!", Toast.LENGTH_LONG).show();

                        }
                         else {
                            boolean isInserted3 = dataHelper.insertZlecenie(spinner1.getSelectedItem().toString(), spinner2.getSelectedItem().toString(),
                                    spinner3.getSelectedItem().toString(), null , editPodsumowanie.getText().toString(), editSzczegoly.getText().toString());
                            if (isInserted3 == true) {
                                Toast.makeText(context, "Dodano zgłoszenie", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(context, TabMain.class);
                                i.putExtra("tab_index", "1");
                                startActivity(i);

                            } else
                                Toast.makeText(context, "Dodawanie zgloszenia nie powiodlo się!", Toast.LENGTH_LONG).show();

                        }
                    }
                }
        );



        return rootView;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        DatabaseHelper dataHelper = new DatabaseHelper(context);
        //gallery
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (intent == null) {

                return;
            } else {
                final Uri uri = intent.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    image = getBytes(bitmap);
                    i=1;
                    dataHelper.updatePhoto(id_szczegoly, image);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        //camera
        else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Bundle extras = intent.getExtras();

            if (extras != null) {

                Bitmap yourImage = extras.getParcelable("data");
                // convert bitmap to byte
                image2 = getBytes(yourImage);
                i=2;
                dataHelper.updatePhoto(id_szczegoly, image2);

            }


        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

}

