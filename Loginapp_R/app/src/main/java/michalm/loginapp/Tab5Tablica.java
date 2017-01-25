package michalm.loginapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



/**
 * Created by Michał on 2017-01-01.
 */

public class Tab5Tablica extends Fragment {

    private Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab5tablica, container, false);


        context = getActivity();
        // Create DatabaseHelper instance
        DatabaseHelper dataHelper = new DatabaseHelper(context);


        // Reference to TableLayout
        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.tablelayout);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"Temat", "Komunikat","Data waznosci"};
        for (String c : headerText) {
            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15);
            tv.setPadding(30, 5, 30, 5);
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
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NAME_OGLOSZENIA;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    // Read columns data

                    String temat = cursor.getString(cursor.getColumnIndex("temat"));
                    String komunikat = cursor.getString(cursor.getColumnIndex("komunikat"));
                    String data_waznosci = cursor.getString(cursor.getColumnIndex("data_waznosci"));

                    // dara rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {temat, komunikat, data_waznosci};
                    for (String text : colText) {
                        TextView tv = new TextView(getActivity());
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(15);
                        tv.setPadding(30, 5, 30, 5);
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


}