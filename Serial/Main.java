import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        MySerial serial = new MySerial();

        TimerTask timerTask = new TimerTask() {
            public void run() {
                if(frame.openCloseButtonFlag == true) {
                    frame.openCloseButtonFlag = false;
                    if(frame.openCloseButtonFunction == frame.OPEN) {
                        serial.currentComPort = frame.portList[frame.comPortBox.getSelectedIndex()];
                        serial.currentComPort.setBaudRate(Integer.parseInt(frame.baudRateBox.getSelectedItem().toString()));
                        serial.currentComPort.setNumDataBits(Integer.parseInt(frame.dataBitsBox.getSelectedItem().toString()));
                        serial.currentComPort.setNumStopBits(Integer.parseInt(frame.stopBitsBox.getSelectedItem().toString()));
                        serial.currentComPort.setParity(frame.parityBitsBox.getSelectedIndex());
                        serial.openPort();
                        if(serial.comPortPane == serial.SUCCESS_TO_OPEN) {
                            frame.openCloseButtonFunction = frame.CLOSE;
                            frame.openCloseButton.setText("Close");
                            frame.showMessageDialog("        Success to OPEN");                       
                        }
                        else {
                            frame.showMessageDialog("            Fail to OPEN");
                        }
                    }
                    else if(frame.openCloseButtonFunction == frame.CLOSE) {
                        frame.openCloseButtonFunction = frame.OPEN;
                        frame.openCloseButton.setText("Open");
                        serial.closePort();
                        frame.showMessageDialog("             Port closed");
                    }
                }

                if(serial.dataBuffer != "") {
                    frame.temperatureValueLabel.setText(serial.dataBuffer);
                    serial.dataBuffer = "";           
                }

                if(frame.slidersFlag == true) {
                    frame.slidersFlag = false;
                    serial.sendColorData(frame.redColorSlider.getValue(), frame.greenColorSlider.getValue(), frame.blueColorSlider.getValue());
                }
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 200);
    }
}