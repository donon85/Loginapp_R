package michalm.loginapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;
import static michalm.loginapp.DatabaseHelper.ostZlecenie;
import static michalm.loginapp.Tab2Zpracy.id_szczegoly;

/**
 * Created by Michał on 2017-01-01.
 */

public class Tab2ZpracySzczegoly extends Fragment {
    private Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2zpracy_szczegoly, container, false);
        context = getActivity();
        final EditText editTextKomentarzZadania = (EditText) rootView.findViewById(R.id.editTextKomentarzZadania);

        // Create DatabaseHelper instance
        DatabaseHelper dataHelper = new DatabaseHelper(context);

        // Reference to TableLayout
        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.tablelayoutSzczegoly);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"Opis zadania", "Zasób", "Lokalizacja", "Status"};
        for (String c : headerText) {
            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(14);
            tv.setPadding(23, 10, 23, 10);
            tv.setText(c);
            tv.setBackgroundResource(R.drawable.back2);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        String id_zad = Integer.toString(id_szczegoly);

        ImageButton imgButtonCamera;
        imgButtonCamera = (ImageButton) rootView.findViewById(R.id.imageButtonCamera);
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
        imageButtonGallery = (ImageButton) rootView.findViewById(R.id.imageButtonGallery);
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


        ImageButton imgButtonUp;
        imgButtonUp = (ImageButton) rootView.findViewById(R.id.imageButtonUp);
        imgButtonUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (id_szczegoly < ostZlecenie) {
                            id_szczegoly++;
                            Fragment mFragment = new Tab2ZpracySzczegoly();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            //Replacing using the id of the container and not the fragment itself
                            ft.replace(R.id.main_content, mFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        } else
                            Toast.makeText(context, "Brak nowszych zgloszeń!", Toast.LENGTH_LONG).show();
                    }
                }
        );


        ImageButton imgButtonDown;
        imgButtonDown = (ImageButton) rootView.findViewById(R.id.imageButtonDown);
        imgButtonDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (id_szczegoly >= 2) {
                            id_szczegoly--;
                            Fragment mFragment = new Tab2ZpracySzczegoly();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            //Replacing using the id of the container and not the fragment itself
                            ft.replace(R.id.main_content, mFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        } else
                            Toast.makeText(context, "Brak starszych zgloszeń!", Toast.LENGTH_LONG).show();
                    }
                }
        );

        Button buttonRozpocznij;
        buttonRozpocznij = (Button) rootView.findViewById(R.id.buttonRozpocznij);
        buttonRozpocznij.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper dataHelper = new DatabaseHelper(context);
                        dataHelper.updateStatusZlecenia(id_szczegoly, "Rozpoczęte", editTextKomentarzZadania.getText().toString());

                        Fragment mFragment = new Tab2ZpracySzczegoly();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //Replacing using the id of the container and not the fragment itself
                        ft.replace(R.id.main_content, mFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                }
        );

        Button buttonWstrzymaj;
        buttonWstrzymaj = (Button) rootView.findViewById(R.id.buttonWstrzymaj);
        buttonWstrzymaj.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper dataHelper = new DatabaseHelper(context);
                        dataHelper.updateStatusZlecenia(id_szczegoly, "Wstrzymane", editTextKomentarzZadania.getText().toString());

                        Fragment mFragment = new Tab2ZpracySzczegoly();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //Replacing using the id of the container and not the fragment itself
                        ft.replace(R.id.main_content, mFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                }
        );

        Button buttonWznow;
        buttonWznow = (Button) rootView.findViewById(R.id.buttonWznow);
        buttonWznow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper dataHelper = new DatabaseHelper(context);
                        dataHelper.updateStatusZlecenia(id_szczegoly, "Wznowione", editTextKomentarzZadania.getText().toString());

                        Fragment mFragment = new Tab2ZpracySzczegoly();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //Replacing using the id of the container and not the fragment itself
                        ft.replace(R.id.main_content, mFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                }
        );

        Button buttonZakoncz;
        buttonZakoncz = (Button) rootView.findViewById(R.id.buttonZakoncz);
        buttonZakoncz.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper dataHelper = new DatabaseHelper(context);
                        dataHelper.updateStatusZlecenia(id_szczegoly, "Zakończone", editTextKomentarzZadania.getText().toString());

                        Fragment mFragment = new Tab2ZpracySzczegoly();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //Replacing using the id of the container and not the fragment itself
                        ft.replace(R.id.main_content, mFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                }
        );

        Button buttonRezygnuj;
        buttonRezygnuj = (Button) rootView.findViewById(R.id.buttonRezygnuj);
        buttonRezygnuj.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper dataHelper = new DatabaseHelper(context);
                        dataHelper.updateStatusZlecenia(id_szczegoly, "Rezygnacja", editTextKomentarzZadania.getText().toString());

                        Fragment mFragment = new Tab2ZpracySzczegoly();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //Replacing using the id of the container and not the fragment itself
                        ft.replace(R.id.main_content, mFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                }
        );

        Button buttonNiepowodzenie;
        buttonNiepowodzenie = (Button) rootView.findViewById(R.id.buttonNiepowodzenie);
        buttonNiepowodzenie.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editTextKomentarzZadania.getText().length() == 0) {
                            Toast.makeText(context, "Wymagane dodanie komentarza!", Toast.LENGTH_LONG).show();
                        } else {
                            DatabaseHelper dataHelper = new DatabaseHelper(context);
                            dataHelper.updateStatusZlecenia(id_szczegoly, "Niepowodzenie", editTextKomentarzZadania.getText().toString());

                            Fragment mFragment = new Tab2ZpracySzczegoly();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            //Replacing using the id of the container and not the fragment itself
                            ft.replace(R.id.main_content, mFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    }
                }
        );

        Button buttonPowrot;
        buttonPowrot = (Button) rootView.findViewById(R.id.buttonPowrot);
        buttonPowrot.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, TabMain.class);
                        i.putExtra("tab_index", "0");
                        startActivity(i);
                    }
                }
        );
        try {

            String selectQuery = "SELECT opis_zadania, zasob_zadania, zadanie_lokalizacja, status_zadania FROM lista_zadan WHERE id_zadania= '" + id_szczegoly + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    // Read columns data

                    String opis_zadania = cursor.getString(cursor.getColumnIndex("opis_zadania"));
                    String zasob_zadania = cursor.getString(cursor.getColumnIndex("zasob_zadania"));
                    String zadanie_lokalizacja = cursor.getString(cursor.getColumnIndex("zadanie_lokalizacja"));
                    String status_zadania = cursor.getString(cursor.getColumnIndex("status_zadania"));

                    // dara rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));


                    String[] colText = {opis_zadania, zasob_zadania, zadanie_lokalizacja, status_zadania};
                    for (String text : colText) {
                        TextView tv = new TextView(getActivity());
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(14);
                        tv.setPadding(23, 40, 23, 40);
                        tv.setText(text);
                        tv.setBackgroundResource(R.drawable.back);
                        row.addView(tv);
                    }


                    tableLayout.addView(row);
                }

            }


            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        DatabaseHelper dataHelper = new DatabaseHelper(context);
        //gallery
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (intent == null) {
                Toast.makeText(context, "Wystąpił błąd podczas dodawania obrazu do bazy!", Toast.LENGTH_LONG).show();
                return;
            } else {
                final Uri uri = intent.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    byte[] image = getBytes(bitmap);
                    dataHelper.updatePhoto(id_szczegoly, image);
                    Toast.makeText(context, "Dodano obraz do bazy!", Toast.LENGTH_LONG).show();
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
                byte[] image2 = getBytes(yourImage);
                dataHelper.updatePhoto(id_szczegoly, image2);
                Toast.makeText(context, "Dodano obraz do bazy!", Toast.LENGTH_LONG).show();
            }


        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

}
