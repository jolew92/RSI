import javax.swing.*;

public class Draw {
    private JPanel container;
    private JEditorPane editorPane1;
    private JPanel Fields;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton startButton;
    private JCheckBox addNoiseRandomCheckBox;
    private JSpinner spinner1;

    public Draw() {

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

