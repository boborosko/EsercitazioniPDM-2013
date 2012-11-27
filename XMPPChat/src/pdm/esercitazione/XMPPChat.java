package pdm.esercitazione;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class XMPPChat extends Activity implements OnClickListener{
	
	private EditText et;
	private TextView tv;
	private XMPPConnection connection;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        et = (EditText)findViewById(R.id.editText);
        tv = (TextView)findViewById(R.id.TextView);
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(this);
        
        ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);//configuro la nuova comunicazione impostando
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);//l'indirizzo del server e la porta;imposto anche la Modalità Sicurezza
        
        connection = new XMPPConnection (config);//invoco la variabile d'istanza per impostare la comunicazione con il server
        
        try {
        	connection.connect();//Effettuo la connessione al server e
        	connection.login("rusco", "qwerty");// la login al server tramite <Utente> e <password>
        } catch (XMPPException e) {
        	e.printStackTrace();
        }
        
        connection.addPacketListener(new PacketListener() {//Creo il pacchetto del messaggio

			public void processPacket(Packet pkt) {
				// TODO Auto-generated method stub
				Message msg = (Message) pkt;
				final String to = msg.getTo();
				final String body = msg.getBody();
				final String[] from = msg.getFrom().split("@");
				Log.d("XMPPChat","Hai ricevuto un messaggio: " + from + " " + to +" " + body);
				tv.post(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						tv.append(from[0] + " " + body + " ");
						
					}
					
				});
				
				
			}
        	
        }, new MessageTypeFilter(Message.Type.normal));
    }
    

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("XMMPChat","Hai scritto:" + et.getText());
		
		tv.append("IO: " + et.getText() + "\n");
		
		Message msg = new Message();
		msg.setTo("all@broadcast.ppl.eln.uniroma2.it");
		msg.setBody(et.getText().toString());
		
		connection.sendPacket(msg);
		
	}
}