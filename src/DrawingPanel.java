import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPanel extends JPanel implements ActionListener {

    private final int DELAY = 150;
    private Timer timer;
    private int setpoint;
    private Queue<Integer> randomQueue;
    private Queue<Integer> setpointQueue;
    private boolean isStarted;

    public DrawingPanel () {
        initTimer();
        isStarted = false;
        randomQueue = new LinkedBlockingQueue<Integer>(780);
        setpointQueue = new LinkedBlockingQueue<Integer>(780);
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean getIsStarted() {
        return isStarted;
    }

    public void setIsStarted(boolean val) {
        isStarted = val;
    }

    public int getSetpoint() {
        return setpoint;
    }

    public void setSetpoint(int val) {
        setpoint = val;
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setBackground(Color.white);
        int h = 300;
        int i = 0;
        int prevRandom = 0;
        int prevSetpoint = 0;

        Random r = new Random();
        Iterator<Integer> iter1 = randomQueue.iterator();
        Iterator<Integer> iter2 = setpointQueue.iterator();
        while (iter1.hasNext()) {
            int currentRandom = iter1.next();
            int currentSetpoint = iter2.next();
            g2d.setPaint(Color.red);
            g2d.drawLine(i, h - prevRandom, i + 1, h - currentRandom);
            g2d.setPaint(Color.blue);
            g2d.drawLine(i, h - prevSetpoint, i + 1, h - currentSetpoint);
            i += 1;
            prevRandom = currentRandom;
            prevSetpoint = currentSetpoint;
        }
        if(randomQueue.size() == 780) {
            randomQueue.remove();
            randomQueue.add(Math.abs(r.nextInt()) % h);
            setpointQueue.remove();
            setpointQueue.add(setpoint);
        }
        else {
            randomQueue.add(Math.abs(r.nextInt()) % h);
            setpointQueue.add(setpoint);
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}