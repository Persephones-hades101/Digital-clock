import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Component;

public class SetAlarmFrame extends JFrame {
  private Container c;
  private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5;
  private JPanel panel1, panel2, panel3, panel4, panel5;
  private Font f, f1, f2, f3, f4;
  private JTextField tfh, tfm, tfs, tfam;
  private JButton btnOk, btnStop, btncl;
  public int temp_h, temp_m, temp_s;
  public String temp_am;
  private int flag = 1;

  // ArrayList to store alarms
  private ArrayList<String> alarms = new ArrayList<>();
  private ArrayList<String> triggeredAlarms = new ArrayList<>();

  public SetAlarmFrame(JFrame parentFrame) {
    super("Set Alarm");

    initComponents();
    currentTime();
    // Add action listener to the Set Alarm button
    // setAlarmButton.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // setAlarm(); // Call a method to handle setting the alarm
    // dispose(); // Close the SetAlarmFrame after setting the alarm
    // parentFrame.setVisible(true); // Show the main frame
    // }
    // });

    // getContentPane().add(panel);
    setSize(600, 500);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(parentFrame);
    setVisible(true);

  }

  String filePath = "Death-Bed-Powfu.wav";

  // PlayMusic(filePath);
  private static Clip clip;
  private static Clip alarmClip;

  // Initialize the Clip instance in the static block
  static {
    try {
      alarmClip = AudioSystem.getClip();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  public static void PlayMusic(String location) {
    try {
      File musicPath = new File(location);
      if (musicPath.exists()) {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

        // Stop the previous alarmClip if it is still running
        stopMusic();

        // Open and start the new alarmClip
        alarmClip = AudioSystem.getClip();
        alarmClip.open(audioInput);
        alarmClip.start();
      } else {
        System.out.println("Can't find the file");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void stopMusic() {
    if (alarmClip != null && alarmClip.isRunning()) {
      alarmClip.stop();
      alarmClip.flush();
      alarmClip.setMicrosecondPosition(0);
    }
  }

  private void storeAlarm(String alarmInfo) {
    alarms.add(alarmInfo);
    // System.out.println("Alarms: " + alarms);
  }

  // Method to display all alarms in panel5
  private void displayAlarms() {
    panel5.removeAll(); // Clear existing labels

    for (String alarm : alarms) {
      JLabel label = new JLabel(alarm);
      label.setFont(new Font("Tahoma", Font.BOLD, 16));
      label.setForeground(new Color(0, 204, 51));
      // Create a Box to center the label horizontally
      Box box = Box.createHorizontalBox();
      box.add(Box.createHorizontalGlue()); // Add glue to push label to the center
      box.add(label);
      box.add(Box.createHorizontalGlue());

      panel5.add(box);
      panel5.add(Box.createVerticalStrut(5));
    }
    // panel5.add(new JLabel("<html><br></html>"));

    // Refresh the panel to reflect the changes
    panel5.revalidate();
    panel5.repaint();
  }

  // Method to check if two time strings match within a certain tolerance
  private boolean isTimeMatch(String currentTime, String alarmTime, int toleranceSeconds) {
    String[] currentTimeParts = currentTime.split(" ");
    String[] alarmTimeParts = alarmTime.split(" ");

    // Compare the time parts
    String[] currentTimeDigits = currentTimeParts[0].split(":");
    String[] alarmTimeDigits = alarmTimeParts[0].split(":");

    int currentHour = Integer.parseInt(currentTimeDigits[0]);
    int currentMinute = Integer.parseInt(currentTimeDigits[1]);
    int currentSecond = Integer.parseInt(currentTimeDigits[2]);

    int alarmHour = Integer.parseInt(alarmTimeDigits[0]);
    int alarmMinute = Integer.parseInt(alarmTimeDigits[1]);
    int alarmSecond = Integer.parseInt(alarmTimeDigits[2]);

    // Compare hours, minutes, and seconds
    boolean hoursMatch = Math.abs(currentHour - alarmHour) <= toleranceSeconds;
    boolean minutesMatch = Math.abs(currentMinute - alarmMinute) <= toleranceSeconds;
    boolean secondsMatch = Math.abs(currentSecond - alarmSecond) <= toleranceSeconds;

    // Check if AM/PM matches
    boolean amPmMatch = currentTimeParts[1].equals(alarmTimeParts[1]);

    // Return true if all components match within the tolerance
    return hoursMatch && minutesMatch && secondsMatch && amPmMatch;
  }

  // Method to check if an alarm has already been triggered
  private boolean alarmAlreadyTriggered(String alarm) {
    // Check if the alarm is in the list of triggered alarms
    // You may need to adjust this logic based on how you store triggered alarms
    return triggeredAlarms.contains(alarm);
  }

  // Method to mark an alarm as triggered
  private void markAlarmAsTriggered(String alarm) {
    // Add the triggered alarm to the list
    // You may need to adjust this logic based on how you store triggered alarms
    triggeredAlarms.add(alarm);
  }

  public void initComponents() {

    c = this.getContentPane();
    c.setLayout(new GridLayout(5, 1));
    c.setBackground(Color.BLACK);

    f1 = new Font("Arial", Font.BOLD, 20);
    f2 = new Font("Digital-7 Mono", Font.BOLD, 40);
    f3 = new Font("Digital-7", Font.PLAIN, 50);
    f4 = new Font("Tahoma", Font.BOLD, 36);

    panel1 = new JPanel();
    panel2 = new JPanel();
    panel3 = new JPanel();
    panel4 = new JPanel();
    panel5 = new JPanel();
    panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
    panel5.setAlignmentX(Component.CENTER_ALIGNMENT);

    jLabel1 = new JLabel();
    // jLabel1.setBounds(60, 5, 360, 130);
    jLabel1.setFont(f3);
    jLabel1.setForeground(new Color(0, 204, 51));
    panel1.add(jLabel1);

    jLabel3 = new JLabel();
    // jLabel3.setBounds(435, 35, 100, 110);
    jLabel3.setFont(f2);
    jLabel3.setForeground(new Color(0, 204, 51));
    panel1.add(jLabel3);
    c.add(panel1);

    jLabel2 = new JLabel();
    // jLabel2.setBounds(55, 130, 260, 50);
    jLabel2.setFont(f4);
    jLabel2.setForeground(new Color(0, 204, 51));
    panel2.add(jLabel2);

    jLabel4 = new JLabel();
    // jLabel4.setBounds(335, 130, 230, 50);
    jLabel4.setFont(f4);
    jLabel4.setForeground(new Color(0, 204, 51));
    panel2.add(jLabel4);
    c.add(panel2);

    tfh = new JTextField();
    tfh.setPreferredSize(new Dimension(50, 50));
    // tfh.setBounds(100, 215, 50, 40);
    tfh.setFont(new Font("Tahoma", Font.BOLD, 20));
    tfh.setHorizontalAlignment(JTextField.CENTER);
    panel3.add(tfh);

    tfm = new JTextField();
    tfm.setPreferredSize(new Dimension(50, 50));
    // tfm.setBounds(165, 215, 50, 40);
    tfm.setFont(new Font("Tahoma", Font.BOLD, 20));
    tfm.setHorizontalAlignment(JTextField.CENTER);
    panel3.add(tfm);

    tfs = new JTextField();
    tfs.setPreferredSize(new Dimension(50, 50));
    tfs.setFont(new Font("Tahoma", Font.BOLD, 20));
    tfs.setHorizontalAlignment(JTextField.CENTER);
    panel3.add(tfs);

    tfam = new JTextField();
    // tfam.setBounds(230, 215, 50, 40);
    tfam.setPreferredSize(new Dimension(50, 50));
    tfam.setFont(new Font("Tahoma", Font.BOLD, 20));
    tfam.setHorizontalAlignment(JTextField.CENTER);
    panel3.add(tfam);
    c.add(panel3);

    btnOk = new JButton("Ok");
    // btnOk.setBounds(295, 215, 60, 40);
    btnOk.setFont(new Font("Tahoma", Font.BOLD, 16));
    panel4.add(btnOk);

    btncl = new JButton("CL");
    // btncl.setBounds(370, 215, 60, 40);
    btncl.setFont(new Font("Tahoma", Font.BOLD, 16));
    panel4.add(btncl);

    btnStop = new JButton("Stop");
    // btnStop.setBounds(445, 215, 75, 40);
    btnStop.setFont(new Font("Tahoma", Font.BOLD, 16));
    panel4.add(btnStop);
    c.add(panel4);

    jLabel5 = new JLabel();
    jLabel5.setText("Your Alarm will display here");
    jLabel4.setFont(f4);
    jLabel4.setForeground(new Color(0, 204, 51));
    panel5.add(jLabel5);
    c.add(panel5);

    btnOk.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent ae) {

        temp_h = Integer.parseInt(tfh.getText());
        temp_m = Integer.parseInt(tfm.getText());
        temp_am = tfam.getText();
        temp_s = Integer.parseInt(tfs.getText());
        flag = 1;

        // Store information about the set alarm in the array
        String alarmInfo = temp_h + ":" + temp_m + ":" + temp_s + " " + temp_am;
        storeAlarm(alarmInfo);

        displayAlarms();

      }
    });

    // Stop Button
    btnStop.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent ae) {
        flag = 0;
        // mp3.stop();
        stopMusic();
      }
    });

    btncl.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent ae) {
        // flag = 0;
        tfh.setText("");
        tfm.setText("");
        tfs.setText("");
        tfam.setText("");

      }
    });

  }

  public void currentTime() {

    Thread clock;
    clock = new Thread() {

      public void run() {

        for (;;) {
          Calendar cal = new GregorianCalendar();

          int second = cal.get(Calendar.SECOND);
          int minute = cal.get(Calendar.MINUTE);
          int hour = cal.get(Calendar.HOUR);
          int day = cal.get(Calendar.DAY_OF_MONTH);
          int month = cal.get(Calendar.MONTH) + 1;
          int year = cal.get(Calendar.YEAR);

          // AM_PM
          Calendar datetime = Calendar.getInstance();
          String am_pm = "";
          if (datetime.get(Calendar.AM_PM) == Calendar.AM) {
            am_pm = "AM";
          } else if (datetime.get(Calendar.AM_PM) == Calendar.PM) {
            am_pm = "PM";
          }

          // week day
          String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thusday",
              "Friday", "Saturday" };
          String wd;
          wd = strDays[datetime.get(Calendar.DAY_OF_WEEK) - 1];

          // setting to label
          jLabel1.setText(hour + " : " + minute + " : " + second);
          jLabel2.setText(day + " - " + month + " - " + year);
          jLabel3.setText(am_pm);
          jLabel4.setText(" " + wd);

          // Alarm ---------------
          String str = hour + ":" + minute + ":" + second + " " + am_pm;

          for (String alarm : alarms) {
            // System.out.println(str);
            // System.out.println(alarm);
            if (isTimeMatch(str, alarm, 1) && !alarmAlreadyTriggered(alarm)) {
              PlayMusic(filePath);
              markAlarmAsTriggered(alarm);
            }
          }

          // if (temp_h == hour && temp_m == minute && temp_s == second &&
          // temp_am.equals(am_pm) && flag == 1) {
          // PlayMusic(filePath);
          // flag = 0;
          // // JOptionPane.showMessageDialog(null, "Alarm !!!!");

          // if (second == 10 || flag == 0) {
          // stopMusic();
          // }
          // }

          // try {
          // sleep(1000);
          // } catch (Exception e) {
          // JOptionPane.showMessageDialog(null, e);
          // }

        }
      }
    };
    clock.start();
  }

}
