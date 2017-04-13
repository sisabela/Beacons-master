package estg.ipvc.beacons;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricoFragment extends Fragment {

    List<Map<String, String>> entradas;

    public HistoricoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_historico, container, false);

        entradas = new ArrayList<Map<String, String>>();

        clickbtn();

        return v;
    }

    public void clickbtn(){


        String url = "https://sisabela.000webhostapp.com/wshistorico.php";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                try{

                    JSONArray arr = response.getJSONArray("DATA");
                    for(int i = 0; i < arr.length();i++){
                        JSONObject obj = arr.getJSONObject(i);
                        //entradas.put(obj.getString("data"),obj.getString("hora"));


                        Map<String, String> datum = new HashMap<String, String>(i);

                        datum.put("First Line", obj.getString("data"));
                        datum.put("Second Line",obj.getString("hora"));
                        entradas.add(datum);

                        fillLista();
                    }
                } catch(JSONException ex){}

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                ((TextView)getView().findViewById(R.id.erro)).setText(error.getMessage());
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);


    }

    public void fillLista(){
        //ListView lista=(ListView)getView().findViewById(R.id.lista);

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), entradas,
                android.R.layout.simple_list_item_2,
                new String[] {"First Line", "Second Line" },
                new int[] {android.R.id.text1, android.R.id.text2 });
    }

}
