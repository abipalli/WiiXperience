import javax.swing.JOptionPane;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;


public class Engine {
	public static void main(String[] args) {
		Wiimote[] wiimotes = new Wiimote[1];
		do {
			JOptionPane.showMessageDialog(null, "Turn on 1 Wiimote", "Connection Setup GG", JOptionPane.INFORMATION_MESSAGE, null);
			wiimotes = WiiUseApiManager.getWiimotes(1, false);
		} while(wiimotes.length < 1);
			wiimotes[0].activateContinuous();
			wiimotes[0].activateMotionSensing();
			wiimotes[0].activateSmoothing();
			wiimotes[0].activateIRTRacking();
			wiimotes[0].setLeds(true, false, false, false);
			
		JOptionPane.showMessageDialog(null, "Wiimote Connection Successful", "Connection Setup", JOptionPane.INFORMATION_MESSAGE, null);
		
		WiiHome home = new WiiHome(wiimotes[0]);
		home.run();
	}
}
