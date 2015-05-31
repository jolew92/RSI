import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Draw {
    private JPanel container;
    private JPanel Fields;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton startButton;
    private JCheckBox addNoiseRandomCheckBox;
    private JSpinner spinner1;
    private DrawingPanel drawingPanel;
    private JSlider slider1;
    private JLabel setpointValueLabel;

    public Draw() {
        slider1.setValue(150);
        setpointValueLabel.setText("150");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!drawingPanel.getIsStarted()) {
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
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Regulator PID oparty o rozproszony algorytm genetyczny");
        frame.setContentPane(new Draw().container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800,400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}

