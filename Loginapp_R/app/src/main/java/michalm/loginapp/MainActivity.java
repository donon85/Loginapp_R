package michalm.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.*;

public class MainActivity extends Activity {

    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    public void onButtonClick(View v){

    if(v.getId() == R.id.Blogin)
    {
            EditText a = (EditText) findViewById(R.id.TFusername);
            String str = a.getText().toString();
            EditText b = (EditText) findViewById(R.id.TFpassword);
            String pass = b.getText().toString();

            String password = helper.searchPass(str);
            if(pass.equals(password))
            {
                Intent i = new Intent(MainActivity.this, TabMain.class);
                startActivity(i);
            }
            else
            {
                Toast temp = makeText(MainActivity.this, "Błędny login lub hasło!", LENGTH_SHORT);
                temp.show();
            }

    }


    }

}
