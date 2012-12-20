package pdm.esercitazione2;


import android.app.Activity;
import android.os.Bundle;

public class DrawPathSenderActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        MyDrawPath dP = new MyDrawPath(this);
        setContentView(dP);
        

        
        
    }
}