import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Draw {
    private JPanel container;
    private JPanel Fields;
    private JTextField kp;
    private JTextField ki;
    private JTextField kd;
    private JButton startButton;
    private JCheckBox addNoiseRandomCheckBox;
    private JSpinner spinner1;
    private DrawingPanel drawingPanel;
    private JSlider slider1;
    private JLabel setpointValueLabel;
    private Queue<Integer> outputQueue;
    private int noiseProcent;

    public Draw() {
        slider1.setValue(150);
        setpointValueLabel.setText("150");
        spinner1.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!drawingPanel.getIsStarted()) {
                    PID_Controller pid = new PID_Controller(100000,10000,170);
                    // wyliczanie wartości kp, ki, kd algorytmu genetycznego
                    kp.setText(String.valueOf(0.5));
                    ki.setText(String.valueOf(0.5));
                    kd.setText(String.valueOf(0.5));

                    outputQueue = new LinkedBlockingQueue<Integer>(100000);
                    //tymczasowo losowo. powinny być outputy z PID
                   // Random r = new Random();
                    //int h = 300;
                    //int test[] = new int[100];
                    //for(int i=0; i<test.length; i++) {
                      //  drawingPanel.addOutput(Math.abs(r.nextInt()) % h);
                    //}
                  //  drawingPanel.setOutputQueue(outputQueue);
                    outputQueue = pid.runPID(150,200,1,0.01,1);

                    drawingPanel.setOutputQueue(outputQueue);

                    drawingPanel.setSetpoint(slider1.getValue());
                    drawingPanel.getTimer().start();
                    drawingPanel.setIsStarted(true);
                    startButton.setText("Stop");

                } else {
                    drawingPanel.getTimer().stop();
                    drawingPanel.setIsStarted(false);
                    startButton.setText("Start");
                }
            }
        });
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setpointValueLabel.setText(String.valueOf(slider1.getValue()));
                drawingPanel.setSetpoint(slider1.getValue());

                // wyliczanie wartości kp, ki, kd algorytmu genetycznego
                kp.setText(String.valueOf(0.5));
                ki.setText(String.valueOf(0.5));
                kd.setText(String.valueOf(0.5));

                //tymczasowo nic. powinny być outputy z PID tak jak wyżej
            }
        });

        addNoiseRandomCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
               if(addNoiseRandomCheckBox.isSelected())
                   noiseProcent = (Integer) spinner1.getValue();
               else
                   noiseProcent = 0;
            }
        });

        spinner1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(addNoiseRandomCheckBox.isSelected())
                    noiseProcent = (Integer) spinner1.getValue();
                else
                    noiseProcent = (Integer) spinner1.getValue();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Regulator PID oparty o rozproszony algorytm genetyczny");
        frame.setContentPane(new Draw().container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800,431);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}

