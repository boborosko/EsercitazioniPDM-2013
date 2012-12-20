package pdm.esercitazione2;

import android.app.Activity;
import android.os.Bundle;

public class DrawReceiverActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MyDrawReceiver receive = new MyDrawReceiver(this);
        setContentView(receive);
    }
}