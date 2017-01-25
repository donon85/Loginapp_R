package michalm.loginapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.data;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Michał on 2017-01-01.
 */

public class Tab4Skaner extends Fragment {
    private Context context;
    private int i=0;
    public static String id_zasob;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4skaner, container, false);



        context = getActivity();
        // Create DatabaseHelper instance
        DatabaseHelper dataHelper = new DatabaseHelper(context);



        // Reference to TableLayout
        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.tablelayout2);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        ImageButton imgButtonSkaner;
        imgButtonSkaner = (ImageButton) rootView.findViewById(R.id.imageButtonSkaner);
        imgButtonSkaner.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                        intent.setPackage("com.google.zxing.client.android");
                        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                        startActivityForResult(intent, 0);

                    }
                }


        );






        try {
            String selectQuery = "SELECT * FROM paszport_techniczny2 WHERE id_paszport= '" + id_zasob + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    // Read columns data
                    int id_paszport = cursor.getInt(cursor.getColumnIndex("id_paszport"));
                    String nazwa = cursor.getString(cursor.getColumnIndex("nazwa"));
                    String osrodek = cursor.getString(cursor.getColumnIndex("osrodek"));
                    String status = cursor.getString(cursor.getColumnIndex("status"));
                    String element_nadrzedny = cursor.getString(cursor.getColumnIndex("element_nadrzedny"));
                    String typ_model = cursor.getString(cursor.getColumnIndex("typ_model"));
                    String lokalizacja_paszport = cursor.getString(cursor.getColumnIndex("lokalizacja_paszport"));

                    String Tid_paszport = "Zasob ID";
                    String Tnazwa = "Nazwa";
                    String Tosrodek = "Osrodek";
                    String Tstatus = "Status";
                    String Telement_nadrzedny = "Element nadrzedny";
                    String Ttyp_model = "Typ/model";
                    String Tlokalizacja_paszport = "Lokalizacja";



                    String[] colText = {id_paszport + "", nazwa, osrodek, status, element_nadrzedny, typ_model, lokalizacja_paszport};
                    String[] colNames = {Tid_paszport, Tnazwa, Tosrodek, Tstatus, Telement_nadrzedny, Ttyp_model, Tlokalizacja_paszport};
                    for (String text : colText) {

                        TableRow row = new TableRow(context);
                        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));

                        TextView tvC = new TextView(getActivity());
                        tvC.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tvC.setGravity(Gravity.LEFT);
                        tvC.setTextSize(15);
                        tvC.setPadding(30, 5, 62, 10);
                        tvC.setText(colNames[i]);
                        tvC.setBackgroundResource(R.drawable.back2);
                        row.addView(tvC);

                        TextView tv = new TextView(getActivity());
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.LEFT);
                        tv.setTextSize(15);
                        tv.setPadding(30, 5, 62, 10);

                        tv.setText(text);
                        tv.setBackgroundResource(R.drawable.back);
                        row.addView(tv);
                        i++;
                        tableLayout.addView(row);
                    }


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
    public void onActivityResult(int requestCode, int resultCode, Intent intent )    {
        super.onActivityResult(requestCode,resultCode, intent );
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                id_zasob = contents;
                Intent i = new Intent(context, TabMain.class);
                i.putExtra("tab_index","2");
                startActivity(i);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(context, "Nie udalo sie odczytac barkodu", Toast.LENGTH_LONG).show();
            }
        }
    }
}
