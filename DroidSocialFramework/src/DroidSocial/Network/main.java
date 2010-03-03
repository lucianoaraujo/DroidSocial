package DroidSocial.Network;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import DroidSocial.Network.*;
import java.util.*;

public class main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        SocialNetworkRequest req = new SocialNetworkRequest("http://api.facebook.com/restserver.php");
        Map<String, String> params = new HashMap<String, String>();
        params.put("method", "Auth.createToken");
        params.put("api_key", "aaf81111730a8fa14eeabee5d18c3f91");
        
        try 
        {
        	String out = req.SendGetRequest(params);
        	TextView v = (TextView)this.findViewById(R.id.output);
        	v.setText(out);
        }
        catch (Exception e) {
        	String m = e.getMessage();
        }
        
    }
}