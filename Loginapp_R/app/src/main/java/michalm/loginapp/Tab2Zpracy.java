package michalm.loginapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static michalm.loginapp.R.layout.tab2zpracy_szczegoly;

/**
 * Created by Michał on 2017-01-01.
 */

public class Tab2Zpracy extends Fragment {
    private Context context;
    public static int id_szczegoly;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                     Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2zpracy, container, false);
        context = getActivity();
        // Create DatabaseHelper instance
          DatabaseHelper dataHelper = new DatabaseHelper(context);


        // Reference to TableLayout
        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.tablelayout3);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"Opis zadania", "Lokalizacja","Status"};
        for (String c : headerText) {
            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15);
            tv.setPadding(50, 8, 50, 8);
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

        try {
            String selectQuery = "SELECT id_zadania, opis_zadania, zadanie_lokalizacja, status_zadania FROM lista_zadan ORDER BY id_zadania DESC";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    // Read columns data
                    int id_zadania = cursor.getInt(cursor.getColumnIndex("id_zadania"));
                    String opis_zadania = cursor.getString(cursor.getColumnIndex("opis_zadania"));
                    String zadanie_lokalizacja = cursor.getString(cursor.getColumnIndex("zadanie_lokalizacja"));
                    String status_zadania = cursor.getString(cursor.getColumnIndex("status_zadania"));

                    // dara rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    String[] colText = {opis_zadania, zadanie_lokalizacja, status_zadania};
                    for (String text : colText) {
                        TextView tv = new TextView(getActivity());
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(15);
                        tv.setPadding(50, 8, 50, 8);
                        tv.setText(text);
                        tv.setBackgroundResource(R.drawable.back);
                        row.addView(tv);
                    }
                    row.setId(id_zadania);
                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            id_szczegoly= v.getId();
                            Fragment mFragment = new Tab2ZpracySzczegoly();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            //Replacing using the id of the container and not the fragment itself
                            ft.replace(R.id.main_content, mFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    });
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

}
