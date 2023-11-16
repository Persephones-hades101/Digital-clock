import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;

public class SetAlarmFrame extends JFrame {
  private JTextField hourField, minuteField, secondField;

  public SetAlarmFrame(JFrame parentFrame) {
    super("Set Alarm");

    // Create components
    JLabel hourLabel = new JLabel("Hour:");
    JLabel minuteLabel = new JLabel("Minute:");
    JLabel secondLabel = new JLabel("Second:");

    hourField = new JTextField(5);
    minuteField = new JTextField(5);
    secondField = new JTextField(5);

    JButton setAlarmButton = new JButton("Set Alarm");

    // Create layout
    JPanel panel = new JPanel();
    panel.setBackground(Color.green);
    panel.add(hourLabel);
    panel.add(hourField);
    panel.add(minuteLabel);
    panel.add(minuteField);
    panel.add(secondLabel);
    panel.add(secondField);
    panel.add(setAlarmButton);

    // Add action listener to the Set Alarm button
    setAlarmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setAlarm(); // Call a method to handle setting the alarm
        dispose(); // Close the SetAlarmFrame after setting the alarm
        parentFrame.setVisible(true); // Show the main frame
      }
    });

    getContentPane().add(panel);
    setSize(500, 500);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(parentFrame);
    setVisible(true);
  }

  private void setAlarm() {
    // Get user-input alarm time from hourField, minuteField, and secondField
    int hour = Integer.parseInt(hourField.getText());
    int minute = Integer.parseInt(minuteField.getText());
    int second = Integer.parseInt(secondField.getText());

    // Use Timer or ScheduledExecutorService to schedule the alarm
    // For simplicity, here's a basic example using Timer
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        // Implement the logic to trigger the alarm (e.g., play a sound, show apop-up)
        // For now, displaying a message as an example
        JOptionPane.showMessageDialog(SetAlarmFrame.this, "Alarm!");
      }
    }, calculateDelay(hour, minute, second) * 1000);
  }

  private long calculateDelay(int hour, int minute, int second) {
    // Calculate the delay until the alarm should trigger in seconds
    long currentTime = System.currentTimeMillis() / 1000;
    long alarmTime = hour * 3600 + minute * 60 + second;
    System.out.println(alarmTime - currentTime);
    return Math.max(0, alarmTime - currentTime);

  }
}
