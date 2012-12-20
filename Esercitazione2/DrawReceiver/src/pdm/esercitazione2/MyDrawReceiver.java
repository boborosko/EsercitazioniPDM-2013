package pdm.esercitazione2;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class MyDrawReceiver extends View{
	float x,y;
	Path path = new Path();
	Paint paint = new Paint();
	String [] Msg;
	XMPPConnection connection;
	Handler handler;

	public MyDrawReceiver(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(Color.MAGENTA);
		path.lineTo(x, y);
		
		handler = new Handler();
		
		receiveDraw();
		
	}
	
	@Override
	protected void onDraw (Canvas canvas) {
		
			canvas.drawPath(path, paint);
	}
	
	public void receiveDraw() {
		Runnable run = new Runnable () {//Imposto la connessione per ricevere il msg del sender

			public void run() {
				// TODO Auto-generated method stub
			ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
			config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
			connection = new XMPPConnection(config);
			
			try {
				connection.connect();
				connection.login("rusco1", "rusco1");
				}catch(XMPPException e) {
					e.printStackTrace();
				}
			
			 connection.addPacketListener(new PacketListener() {

				public void processPacket(Packet pkt) {//pacchetto del msg;
					// TODO Auto-generated method stub
					Message msg = (Message) pkt;
					final String to = msg.getTo();
					final String body = msg.getBody();
					Msg=body.split("@");
					Log.d("","Hai ricevuto un messaggio: "+ to +" " + body);
					
					handler.post(new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
						
							path.lineTo(x, y);
							invalidate();
							
								}
						
							});
					
						}
				 
			 		},new MessageTypeFilter(Message.Type.normal));
			
				}
				
			};
		new Thread(run).start();
	}

}
