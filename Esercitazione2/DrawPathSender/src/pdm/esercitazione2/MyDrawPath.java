package pdm.esercitazione2;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyDrawPath extends View{
	float x,y;
    XMPPConnection connection;
    private String Msg = new String();
    Path path = new Path();
    Paint paint = new Paint();
	 
	public MyDrawPath(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Sendraw();
			
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
			path.lineTo(x, y);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(3);      //ho definito le caratteristiche della linea:tipo,spessore,colore
			paint.setColor(Color.WHITE);
		
			canvas.drawPath(path, paint);	
	 }
	@Override
	public boolean onTouchEvent(MotionEvent event) {  //gestisco l'evento di tocco dello schermo;
		int action = event.getAction();
		int touchX = (int) event.getX();
		int touchY = (int) event.getY();
		
		switch(action) {
		case MotionEvent.ACTION_MOVE:		//gestisco lo spostamento sullo schermo;
			x = touchX;
		    y = touchY;            //assegno ai float i valori presi nella gestione dell'evento di touch;
			path.lineTo(touchX, touchY);       //metto i valori nel path del disegno;
			Msg="MOVE"+touchX+"@"+touchY;
			invalidate();
			onClick();
			break;
		case MotionEvent.ACTION_UP:     //gestisco lo stacco del dito dallo schermo;
			path.close();              //una volta staccato il dito viene terminato il disegno;
			Msg="UP"+touchX+"@"+touchY;
			invalidate();
			onClick();
			break;
		default:
				break;
		}
			
		return true;
			
		}		
		public void Sendraw () {  //Imposto la connessione 
			Runnable run = new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
				ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
				config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
				connection = new XMPPConnection (config);
				
				try {
					connection.connect();
					connection.login("rusco", "qwerty");
					}catch (XMPPException e) {
						e.printStackTrace();
					}
				}
				
			};
			
			new Thread(run).start();
		}
		public void onClick() {
			
			Runnable run = new Runnable() { //Creo il messaggio da inviare al destinatario

				public void run() {
					// TODO Auto-generated method stub
					Message msg = new Message();
					msg.setTo("rusco1@ppl.eln.uniroma2.it");//destinatario msg
					msg.setBody(Msg);
					
					System.out.println(Msg.toString());//payload
					connection.sendPacket(msg);//invio pacchetto
					Log.d("XMPPChat","Hai scritto: "+Msg);
					}
				};
				
				Thread th = new Thread(run);
				th.start();
		
			
		}
  	}


