import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Container;
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

public class SetAlarmFrame extends JFrame {
  private Container c;
  private JLabel jLabel1, jLabel2, jLabel3, jLabel4;
  private Font f, f1, f2, f3, f4;
  private JTextField tfh, tfm, tfam;
  private JButton btnOk, btnStop, btncl;
  public int temp_h, temp_m;
  public String temp_am;
  private int flag = 1;

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

  public static void PlayMusic(String location) {
    try {
      File musicPath = new File(location);
      if (musicPath.exists()) {

        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
      } else {
        System.out.println("Cant find the file");
      }
    } catch (Exception e) {

    }
  }

  public static void stopMusic() {
    if (clip != null && clip.isRunning()) {
      clip.stop();
    }
  }

  public void initComponents() {

    c = this.getContentPane();
    c.setLayout(null);
    c.setBackground(Color.BLACK);

    f1 = new Font("Arial", Font.BOLD, 20);
    f2 = new Font("Digital-7 Mono", Font.BOLD, 46);
    f3 = new Font("Digital-7", Font.PLAIN, 90);
    f4 = new Font("Tahoma", Font.BOLD, 36);

    jLabel1 = new JLabel();
    jLabel1.setBounds(60, 5, 360, 130);
    jLabel1.setFont(f3);
    jLabel1.setForeground(new Color(0, 204, 51));
    c.add(jLabel1);

    jLabel3 = new JLabel();
    jLabel3.setBounds(435, 35, 100, 110);
    jLabel3.setFont(f2);
    jLabel3.setForeground(new Color(0, 204, 51));
    c.add(jLabel3);

    jLabel2 = new JLabel();
    jLabel2.setBounds(55, 130, 260, 50);
    jLabel2.setFont(f4);
    jLabel2.setForeground(new Color(0, 204, 51));
    c.add(jLabel2);

    jLabel4 = new JLabel();
    jLabel4.setBounds(335, 130, 230, 50);
    jLabel4.setFont(f4);
    jLabel4.setForeground(new Color(0, 204, 51));
    c.add(jLabel4);

    tfh = new JTextField();
    tfh.setBounds(100, 215, 50, 40);
    tfh.setFont(new Font("Tahoma", Font.BOLD, 20));
    tfh.setHorizontalAlignment(JTextField.CENTER);
    c.add(tfh);

    tfm = new JTextField();
    tfm.setBounds(165, 215, 50, 40);
    tfm.setFont(new Font("Tahoma", Font.BOLD, 20));
    tfm.setHorizontalAlignment(JTextField.CENTER);
    c.add(tfm);

    tfam = new JTextField();
    tfam.setBounds(230, 215, 50, 40);
    tfam.setFont(new Font("Tahoma", Font.BOLD, 20));
    tfam.setHorizontalAlignment(JTextField.CENTER);
    c.add(tfam);

    btnOk = new JButton("Ok");
    btnOk.setBounds(295, 215, 60, 40);
    btnOk.setFont(new Font("Tahoma", Font.BOLD, 16));
    c.add(btnOk);

    btncl = new JButton("CL");
    btncl.setBounds(370, 215, 60, 40);
    btncl.setFont(new Font("Tahoma", Font.BOLD, 16));
    c.add(btncl);

    btnStop = new JButton("Stop");
    btnStop.setBounds(445, 215, 75, 40);
    btnStop.setFont(new Font("Tahoma", Font.BOLD, 16));
    c.add(btnStop);

    btnOk.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent ae) {

        temp_h = Integer.parseInt(tfh.getText());
        temp_m = Integer.parseInt(tfm.getText());
        temp_am = tfam.getText();
        flag = 1;
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
        flag = 0;
        tfh.setText("");
        tfm.setText("");
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

          if (temp_h == hour && temp_m == minute && temp_am.equals(am_pm) && flag == 1) {
            PlayMusic(filePath);
            flag = 0;
            // JOptionPane.showMessageDialog(null, "Stop Playing the song");

            if (second == 59 || flag == 0) {

            }
          }

          try {
            sleep(1000);
          } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
          }

        }
      }
    };
    clock.start();
  }

}
