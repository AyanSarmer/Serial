import java.io.IOException;
import java.io.OutputStream;
import com.fazecast.jSerialComm.SerialPort;  
import com.fazecast.jSerialComm.SerialPortDataListener;  
import com.fazecast.jSerialComm.SerialPortEvent; 

public class MySerial {

    String dataBuffer = "";
    SerialPort currentComPort;
    final byte NO_FLAG = 0;
    final byte SUCCESS_TO_OPEN = 1;
    final byte FAIL_TO_OPEN = 2;
    final byte PLEASE_CHOOSE_COM_PORT = 3;
    final byte PORT_IS_CLOSED = 4;
    byte comPortPane = NO_FLAG;
    OutputStream stripStream;

    enum comPortPane {
        NO_FLAG,
        SUCCESS_TO_OPEN,
        FAIL_TO_OPEN,
        PLEASE_CHOOSE_COM_PORT,
        PORT_IS_CLOSED
    }

    void openPort() {
        try { 
            currentComPort.openPort();
            if(currentComPort.isOpen()) {
                comPortPane = SUCCESS_TO_OPEN;
                SerialEventBasedReading(currentComPort);
            }
            else {
                comPortPane = FAIL_TO_OPEN;
            }
        }
        catch(ArrayIndexOutOfBoundsException a) {
            comPortPane = PLEASE_CHOOSE_COM_PORT;
        }
        catch(Exception b) {
    
        }
    }

    void closePort() {
        if(currentComPort.isOpen()) {
            currentComPort.closePort();
            comPortPane = PORT_IS_CLOSED;
        }
    }

    void sendColorData(int redValue, int greenValue, int blueValue) {
        stripStream = currentComPort.getOutputStream();
        int[] dataToSend = new int[4];
        dataToSend[0] = 87;
        dataToSend[1] = redValue;
        dataToSend[2] = greenValue;
        dataToSend[3] = blueValue;
        try {
            stripStream.write(dataToSend[0]);
            stripStream.write(dataToSend[1]);
            stripStream.write(dataToSend[2]);
            stripStream.write(dataToSend[3]);
        }
        catch(IOException noSendException) {

        }
    }

    private void SerialEventBasedReading(SerialPort currentComPort2) {
        currentComPort2.addDataListener(new SerialPortDataListener() {

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent arg0) {
                byte []newData = arg0.getReceivedData();  
                if(newData.length == 4) {
                    dataBuffer = "";
                    if(newData[0] == 0) {
                        dataBuffer = "+";
                    }
                    else if(newData[0] == 1) {
                        dataBuffer = "-";
                    }
                    dataBuffer += newData[1];
                    dataBuffer += newData[2];
                    dataBuffer += ".";
                    dataBuffer += newData[3];
                }
            }  
        });
    }
}
