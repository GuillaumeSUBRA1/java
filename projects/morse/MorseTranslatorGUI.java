package projects.morse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

class MorseTranslatorGUI extends JFrame implements KeyListener {
    private JTextArea textArea = new JTextArea();
    private JTextArea morseArea = new JTextArea();
    private MorseController morseController = new MorseController();

    public MorseTranslatorGUI() {
        super("Morse code translator");

        setSize(new Dimension(600, 780));
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.decode("#091427"));
        setLocationRelativeTo(null);

        title();
        input("Text : ", 100);
        textInputArea(textArea, true, true, 140);
        input("Morse : ", 400);
        textInputArea(morseArea, false, false, 440);
        playSoundButton();
    }

    private void title() {
        JLabel title = new JLabel("Morse translator");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 0, 600, 100);
        add(title);
    }

    private void input(String t, int y) {
        JLabel input = new JLabel(t);
        input.setFont(new Font("Dialog", Font.BOLD, 16));
        input.setForeground(Color.WHITE);
        input.setBounds(20, y, 200, 30);
        add(input);
    }

    private void textInputArea(JTextArea t, boolean e, boolean listen, int y) {
        t.setFont(new Font("Dialog", Font.PLAIN, 18));
        t.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        t.setEditable(e);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);

        if (listen && t != null) {
            t.addKeyListener(this);
        }

        JScrollPane scroll = new JScrollPane(t);
        scroll.setBounds(20, y, 540, 240);

        add(scroll);
    }

    private void playSoundButton() {
        JButton play = new JButton("Play sound");
        play.setBounds(250, 690, 100, 30);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play.setEnabled(false);
                Thread playMorse = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("test");
                        try {
                            String[] morseCode = morseArea.getText().split(" ");
                            morseController.sound(morseCode);
                        } catch (LineUnavailableException l) {
                            l.printStackTrace();
                        } catch (InterruptedException i) {
                            i.printStackTrace();
                        } finally {
                            play.setEnabled(true);
                        }
                    }
                });
                playMorse.start();
            }
        });

        add(play);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_SHIFT) {
            String t = textArea.getText();
            morseArea.setText(morseController.translate(t));
        }
    }

}