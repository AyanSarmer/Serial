import javax.swing.*;   
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import com.fazecast.jSerialComm.SerialPort;

public class MyFrame implements ActionListener, ChangeListener {
    
    JFrame MyFrame;   
    Font MyFont;
    JComboBox<String> comPortBox, baudRateBox, dataBitsBox, stopBitsBox, parityBitsBox;
    JButton openCloseButton;
    boolean OPEN = true;
    boolean CLOSE = false;
    boolean openCloseButtonFunction = OPEN;
    boolean openCloseButtonFlag = false;
    JLabel temperatureValueLabel, celsiusLabel;
    JSlider redColorSlider, greenColorSlider, blueColorSlider;
    JTextField colorField;
    SerialPort []portList;
    boolean slidersFlag = false;

    MyFrame() {
        MyFrame = new JFrame();
        MyFrame.setResizable(false);
        MyFrame.setLayout(null);
        MyFrame.setSize(440, 500);

        baudRateComponentsInit();
        dataBitsComponentsInit();
        stopBitsComponentsInit();
        parityBitsComponentsInit();
        comPortComponentsInit();
        openCloseButtonInit();
        redSliderComponentsInit();
        greenSliderComponentsInit();
        blueSliderComponentsInit();
        colorFieldInit();
        temperatureComponentsInit();

        MyFrame.setVisible(true);
    }

    private void baudRateComponentsInit() {
        JLabel baudRateLabel;
        baudRateLabel = new JLabel("Baud rate");  
        baudRateLabel.setFont(MyFont);
        baudRateLabel.setBounds(90, 20, 100,30); 
        MyFrame.add(baudRateLabel);
    
        String baudRate[]={"4800", "9600", "14400", "19200", "28800", "38400", "57600", "115200"};  
        baudRateBox = new JComboBox<>(baudRate);  
        baudRateBox.setBounds(73, 45, 100, 30);   
        baudRateBox.setEnabled(true);
        baudRateBox.setFont(MyFont);
        baudRateBox.setSelectedItem("9600");
        MyFrame.add(baudRateBox);
    }

    private void dataBitsComponentsInit() {
        JLabel dataBitsLabel;
        dataBitsLabel = new JLabel("Data bits");  
        dataBitsLabel.setFont(MyFont);
        dataBitsLabel.setBounds(223, 20, 100,30);
        MyFrame.add(dataBitsLabel);
    
        String dataBits[]={"5", "6", "7", "8"};  
        dataBitsBox = new JComboBox<>(dataBits); 
        dataBitsBox.setBounds(205, 45, 100, 30); 
        dataBitsBox.setEnabled(true);
        dataBitsBox.setFont(MyFont);
        dataBitsBox.setSelectedItem("8");
        MyFrame.add(dataBitsBox);
    }

    private void stopBitsComponentsInit() {
        JLabel stopBitsLabel;
        stopBitsLabel = new JLabel("Stop Bits");  
        stopBitsLabel.setFont(MyFont);
        stopBitsLabel.setBounds(90, 95, 100,30);
        MyFrame.add(stopBitsLabel);
    
        String stopBits[]={"1", "1.5", "2"};
        stopBitsBox = new JComboBox<>(stopBits);
        stopBitsBox.setBounds(73, 120, 100, 30);
        stopBitsBox.setFont(MyFont);
        stopBitsBox.setEnabled(true);
        MyFrame.add(stopBitsBox);
    }

    private void parityBitsComponentsInit() {
        JLabel parityBitsLabel;
        parityBitsLabel = new JLabel("Parity Bits");  
        parityBitsLabel.setFont(MyFont);
        parityBitsLabel.setBounds(220, 95, 100,30);
        MyFrame.add(parityBitsLabel);
    
        String parityBits[]={"none", "odd", "even", "mark", "space"};  
        parityBitsBox = new JComboBox<>(parityBits);
        parityBitsBox.setBounds(205, 120, 100,30);    
        parityBitsBox.setFont(MyFont); 
        parityBitsBox.setEnabled(true);
        MyFrame.add(parityBitsBox);
    }

    private void comPortComponentsInit() {
        JLabel comPortLabel;
        comPortLabel = new JLabel("COM port");  
        comPortLabel.setFont(MyFont);
        comPortLabel.setBounds(90, 170, 100,30);
        MyFrame.add(comPortLabel);
    
        comPortBox = new JComboBox<>(); 
        comPortBox.setFont(MyFont);
        comPortBox.setBounds(73, 195, 100,30);  
        portList = SerialPort.getCommPorts();
        for (SerialPort port: portList) {
            comPortBox.addItem(port.getSystemPortName());
        }
        MyFrame.add(comPortBox);
    }

    private void openCloseButtonInit() {
        openCloseButton = new JButton("Open");
        openCloseButton.setBounds(205, 195, 100,30);
        openCloseButton.setFocusable(false);
        openCloseButton.setFont(MyFont);
        openCloseButton.addActionListener(this);
        openCloseButton.setEnabled(true);
        MyFrame.add(openCloseButton);
    }

    private void redSliderComponentsInit() {
        JLabel redSliderLabel = new JLabel("R");
        redSliderLabel.setBounds(55, 250, 15, 15);
        redSliderLabel.setForeground(Color.RED);
        redSliderLabel.setFont(MyFont);
        MyFrame.add(redSliderLabel);

        redColorSlider = new JSlider(0, 255, 0);
        redColorSlider.setBounds(73, 240, 150, 40);
        redColorSlider.setValue(0);
        redColorSlider.setPaintLabels(true);
        redColorSlider.addChangeListener(this);
        MyFrame.add(redColorSlider);
    }

    private void greenSliderComponentsInit() {
        JLabel greenSliderLabel = new JLabel("G");
        greenSliderLabel.setBounds(55, 280, 15, 15);
        greenSliderLabel.setForeground(Color.GREEN);
        greenSliderLabel.setFont(MyFont);
        MyFrame.add(greenSliderLabel);

        greenColorSlider = new JSlider(0, 255, 0);
        greenColorSlider.setBounds(73, 270, 150, 40);
        greenColorSlider.setValue(0);
        greenColorSlider.setPaintLabels(true);
        greenColorSlider.addChangeListener(this);
        MyFrame.add(greenColorSlider);
    }

    private void blueSliderComponentsInit() {
        JLabel blueSliderLabel = new JLabel("B");
        blueSliderLabel.setBounds(55, 310, 15, 15);
        blueSliderLabel.setForeground(Color.BLUE);
        blueSliderLabel.setFont(MyFont);
        MyFrame.add(blueSliderLabel);

        blueColorSlider = new JSlider(0, 255, 0);
        blueColorSlider.setBounds(73, 300, 150, 40);
        blueColorSlider.setValue(0);
        blueColorSlider.setPaintLabels(true);
        blueColorSlider.addChangeListener(this);
        MyFrame.add(blueColorSlider);
    }

    private void colorFieldInit() {
        colorField = new JTextField();
        colorField.setBounds(235, 255, 70, 70);
        colorField.setBackground(new ColorUIResource(redColorSlider.getValue(), greenColorSlider.getValue(), blueColorSlider.getValue()));
        MyFrame.add(colorField);
    }

    private void temperatureComponentsInit() {
        temperatureValueLabel = new JLabel("00.0", SwingConstants.RIGHT);
        temperatureValueLabel.setFont(new Font("Ink Free", Font.BOLD, 35));
        temperatureValueLabel.setBounds(110, 365, 110, 100);
        temperatureValueLabel.setBackground(MyFrame.getBackground());
        MyFrame.add(temperatureValueLabel);

        celsiusLabel = new JLabel();
        celsiusLabel.setText("\u00B0" + "C");
        celsiusLabel.setFont(new Font("Ink Free", Font.BOLD, 35));
        celsiusLabel.setBounds(225, 365, 40, 100);
        celsiusLabel.setBackground(MyFrame.getBackground());
        MyFrame.add(celsiusLabel);
    }

    void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(MyFrame, message);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == redColorSlider || e.getSource() == greenColorSlider || e.getSource() == blueColorSlider) {
            colorField.setBackground(new ColorUIResource(redColorSlider.getValue(), greenColorSlider.getValue(), blueColorSlider.getValue()));
            slidersFlag = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == openCloseButton) {
            openCloseButtonFlag = true;
        }
    }
}
