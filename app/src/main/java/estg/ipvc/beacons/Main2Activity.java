package estg.ipvc.beacons;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    EditText text;
    EditText p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    public void clickbtn(View v){

        text = (EditText)findViewById(R.id.user);
         p = (EditText)findViewById(R.id.pass);


        String url = "https://sisabela.000webhostapp.com/wslogin.php";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                try{
                    JSONArray arr = response.getJSONArray("DATA");
                    for(int i = 0; i < arr.length();i++){
                        JSONObject obj = arr.getJSONObject(i);
                        if(text.getText().toString().equals(obj.getString("username"))&& p.getText().toString().equals(obj.getString(("pass")))){
                            Toast.makeText(Main2Activity.this, "DEUUUU", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Main2Activity.this, "NAOOOO DEUUUU", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch(JSONException ex){}
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                ((TextView) findViewById(R.id.texto)).setText(error.getMessage());
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

        Intent intent = new Intent(this, NavActivity.class);
        startActivity(intent);
    }
}
