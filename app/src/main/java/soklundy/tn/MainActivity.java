package soklundy.tn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //region oncclick to login

    //endregion
    @Override
    public void onClick(View v) {

        //declaration to get values from view control
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        HashMap data = new HashMap();
        data.put("txtUsername",etUsername.getText().toString());
        data.put("txtPassword",etPassword.getText().toString());

        PostResponseAsyncTask poReAs = new PostResponseAsyncTask(MainActivity.this, data,this);

        poReAs.execute("http://10.0.3.2:8080/TNews/login.php");
    }


    //region getresponse
        /*get response from server*/
    //endregion
    @Override
    public void processFinish(String result) {

        if (result.contains("success")){
            Intent intent = new Intent(MainActivity.this,SubCon.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_LONG).show();
        }
    }
}
