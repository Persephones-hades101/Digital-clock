import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyWindow extends JFrame {
  private Font font = new Font("", Font.BOLD, 35);

  private JPanel headingPanel;
  private JLabel heading;

  private JPanel clockLabelPanel;
  private JLabel clockLabel;

  private JPanel setAlarmPanel;
  private JButton setAlarmButton;

  MyWindow() {
    super.setTitle("My Clock");
    super.setSize(400, 600);
    super.setLocation(300, 50);
    this.createGUI();
    this.startClock();
    super.setVisible(true);
    super.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void createGUI() {
    headingPanel = new JPanel();
    heading = new JLabel("My Clock");
    headingPanel.add(heading);
    heading.setFont(font);

    clockLabelPanel = new JPanel();
    clockLabel = new JLabel("Clock");
    clockLabelPanel.add(clockLabel);
    clockLabel.setFont(font);

    setAlarmPanel = new JPanel();
    setAlarmButton = new JButton("Set Alarm");
    setAlarmPanel.add(setAlarmButton);
    setAlarmButton.setFont(font);

    this.setLayout(new GridLayout(3, 1));
    this.add(headingPanel);
    this.add(clockLabelPanel);
    this.add(setAlarmPanel);

    setAlarmButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        new SetAlarmFrame(MyWindow.this);
      }

    });

  }

  public void startClock() {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      public void run() {
        // String dateTime = new Date().toString();
        // String dateTime = new Date().toLocaleString();

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh : mm : ss a");
        String dateTime = sdf.format(d);
        clockLabel.setText(dateTime);
      }
    };
    timer.schedule(task, 0, 1000);
  }

  // private void setAlarm() {
  // // Get user-input alarm time from hourField and minuteField
  // int hour = Integer.parseInt(JOptionPane.showInputDialog("Enter the Hour
  // field"));
  // int min = Integer.parseInt(JOptionPane.showInputDialog("Enter the Minute
  // field"));

  // JOptionPane.showMessageDialog(null, "ALARM SET SUCCESSFULLY FOR" + hour + ":"
  // + min);

  // // Use Timer or ScheduledExecutorService to schedule the alarm
  // // For simplicity, here's a basic example using Timer

  // // private void triggerAlarm() {
  // // // Implement the logic to trigger the alarm (e.g., play a sound, show
  // // apop-up)
  // // JOptionPane.showMessageDialog(this, "Alarm!");
  // // }
  // }
}
