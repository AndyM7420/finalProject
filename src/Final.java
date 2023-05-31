import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Final extends JFrame implements ActionListener {
    private JPanel panel1;
    private JPanel panel2;
    private static Image backgroundImage;
    private boolean sliderSong;
    private Long clipTimePosition;
    private int duration;
    private int count;
    Clip clip;
    private AudioInputStream audioInputStream;
    private JPasswordField passwordField1;
    private DataLine own;
    private JButton listenButton;
    private JButton recommendButton;
    private JButton playArtistButton;
    private JList<String> list1;
    private JSlider slider1;
    private JPanel shit;
    private JButton pauseButton;
    private JButton forwardButton;
    private JButton restart;
    private JLabel Title;
    public static ArrayList<String> songList=new ArrayList<String>();
    DefaultListModel<String> model = new DefaultListModel<>();
    JLabel label3 = new JLabel();
    JPanel panel = new JPanel();
    JSplitPane splitPane = new JSplitPane();
    File songs;
    String timeStamp;

// int count=0;
//        while(count<nose.getSong().length) {
//            System.out.println(songList.get(count));
//            count++;
//        }
    public Final() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        restart.setSize(2, 3);
        JOptionPane.showMessageDialog(null, "Press listen to start song");
        Song nose=new Song(new File("src/frank").listFiles());
        nose.convertFile(songList);
        songs = new File(nose.chooseSong());
        System.out.println(nose.chooseSong());
        audioInputStream = AudioSystem.getAudioInputStream(songs.getAbsoluteFile());
        ImageIcon restarts = new ImageIcon("src/2143261-200.png");
        ImageIcon pause = new ImageIcon("src/61180.png");
        ImageIcon forward = new ImageIcon("src/images.png");
        restarts.setImage(setImageButtons(restarts));
        pause.setImage(setImageButtons(pause));
        forward.setImage(setImageButtons(forward));
        pauseButton.setIcon(pause);
        forwardButton.setIcon(forward);
        restart.setIcon((restarts));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        list1.setModel(model);
        list1.setOpaque(true);
        if (!sliderSong) {
            slider1.setVisible(false);
        }
        playArtistButton.addActionListener(this);
        restart.addActionListener(this);
        recommendButton.addActionListener(this);
        listenButton.addActionListener(this);


    }


    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new Final().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setBackground(Color.BLUE);
        frame.setVisible(true);
        frame.setSize(840, 690);
        frame.setLocationRelativeTo(null);
    }

    public void pause() {
        if (clip.isRunning() && timeStamp.equals("playing")) {
            clipTimePosition = clip.getMicrosecondPosition();
            clip.stop();
            timeStamp = "paused";
        }
    }

    public void restartAudioStream(File chosenSong) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        songs = chosenSong;
        audioInputStream = AudioSystem.getAudioInputStream(songs.getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    public void resume() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (timeStamp != null && timeStamp.equals("paused")) {
            clip.close();
            restartAudioStream(songs);
            clip.setMicrosecondPosition(clipTimePosition);
            clip.start();
        } else {
            JOptionPane.showMessageDialog(null, "HAVEN'T PLAYED SONG");
        }
    }

    public double geDesiredFrame() {
        int frame = slider1.getValue();
        double only = ((double) audioInputStream.getFormat().getFrameRate());
        return only;
    }

    public String songName(String songName) {

        if (songName.contains("(")) {
            return songName.substring(0, songName.indexOf("("));
        }
        return songName.substring(0, songName.indexOf("."));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playArtistButton) {
            timeStamp = "playing";
            Song nose=new Song(new File("src/frank").listFiles());
            nose.convertFile(songList);
            if(clipTimePosition==null){
                model.addElement(songName(songs.getName()));
            }
            songs = new File(nose.chooseSong());
            try {
                audioInputStream = AudioSystem.getAudioInputStream(songs.getAbsoluteFile());
                clip.start();
                count++;
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                slider1.setVisible(true);
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == restart) {
            try {
                resume();
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == recommendButton) {
            try {
                resume();
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == listenButton) {
            pause();
        } else if (e.getSource() == pauseButton) {
            pause();
        }
    }

    public Image setImageButtons(ImageIcon icons) {
        ImageIcon temp = icons;
        Image image = temp.getImage();
        return image.getScaledInstance(45, 45, Image.SCALE_FAST);
    }
}