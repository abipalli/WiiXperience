/**
 * @author Abi S
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.utils.IRPanel;
import wiiusej.utils.OrientationExpansionEventPanel;
import wiiusej.utils.OrientationPanel;
import wiiusej.utils.OrientationWiimoteEventPanel;
import wiiusej.values.Orientation;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;


public class WiimoteStatistics extends JFrame {
	
	private final JLabel pitchLabel, rollLabel;
	private float pitchVal = 0, rotVal = 0;
	private final Wiimote remote;	
	
	private OrientationPanel orientPanel;
	
	private final WiimoteListener listener;
	
	/**
	 * Default constructor for WiimoteStatistics
	 */
	WiimoteStatistics(Wiimote remote) {
		super("Wiimote Statistics");
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		this.remote = remote;
			listener = new StatisticsListener();
			
		pitchLabel = new JLabel("PITCH: "+pitchVal);
			pitchLabel.setHorizontalAlignment(SwingUtilities.CENTER);
			pitchLabel.setVerticalAlignment(SwingUtilities.CENTER);
			pitchLabel.setBackground(Color.RED);
			pitchLabel.setOpaque(true);
		rollLabel = new JLabel("ROLL: "+rotVal);
			rollLabel.setSize(100, 400);
			rollLabel.setHorizontalAlignment(SwingUtilities.CENTER);
			rollLabel.setVerticalAlignment(SwingUtilities.CENTER);
			rollLabel.setBackground(Color.RED);
			rollLabel.setOpaque(true);
			
		orientPanel = new OrientationWiimoteEventPanel();
		add(orientPanel, BorderLayout.CENTER);
			
		add(rollLabel, BorderLayout.NORTH);
		add(pitchLabel, BorderLayout.SOUTH);
	}
	
	public WiimoteListener getWiimoteListener() {
		return listener;
	}
	
	class StatisticsListener implements WiimoteListener {
		/**
		 * @param arg0 Explicit parameter passed through method representing an instance of {@link WiimoteButtonsEvent} 
		 */
		@Override
		public void onButtonsEvent(WiimoteButtonsEvent arg0) {
			// TODO Auto-generated method stub		
			switch(arg0.getButtonsHeld()) {
				case 4: {
					remote.activateRumble();
					break;
				}
				case 8: {
					remote.deactivateRumble();
					break;
				}
			}
		}
	
		@Override
		public void onClassicControllerInsertedEvent(
				ClassicControllerInsertedEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onClassicControllerRemovedEvent(
				ClassicControllerRemovedEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onDisconnectionEvent(DisconnectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onExpansionEvent(ExpansionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onIrEvent(IREvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onMotionSensingEvent(MotionSensingEvent arg0) {
			// TODO Auto-generated method stub
			Orientation orient = arg0.getOrientation();
			
			
			rotVal = orient.getRoll();
			rollLabel.setText("ROLL: " + rotVal);
			pitchVal = orient.getPitch();
			pitchLabel.setText("PITCH: " + pitchVal);
			
			if(rotVal > 0) {
				rollLabel.setBackground(Color.GREEN);
				if(pitchVal < 0) {
					pitchLabel.setBackground(Color.RED);
				} else {
					pitchLabel.setBackground(Color.GREEN);
				}
			} else {
				rollLabel.setBackground(Color.RED);
				if(pitchVal < 0) {
					pitchLabel.setBackground(Color.RED);
				} else {
					pitchLabel.setBackground(Color.GREEN);
				}
			}
			
			orientPanel.onMotionSensingEvent(arg0);
			
			repaint();
		}
	
		@Override
		public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onStatusEvent(StatusEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

}
