package projects.morse;

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class MorseController {
    private HashMap<Character, String> code;

    public MorseController() {

        code = new HashMap<>();

        code.put('A', ".-");
        code.put('B', "-...");
        code.put('C', "-.-.");
        code.put('D', "-..");
        code.put('E', ".");
        code.put('F', "..-.");
        code.put('G', "--.");
        code.put('H', "....");
        code.put('I', "..");
        code.put('J', ".---");
        code.put('K', "-.-");
        code.put('L', ".-..");
        code.put('M', "--");
        code.put('N', "-.");
        code.put('O', "---");
        code.put('P', ".--.");
        code.put('Q', "--.-");
        code.put('R', ".-.");
        code.put('S', "...");
        code.put('T', "-");
        code.put('U', "..-");
        code.put('V', "...-");
        code.put('W', ".--");
        code.put('X', "-..-");
        code.put('Y', "-.--");
        code.put('Z', "--..");

        // lowercase
        code.put('a', ".-");
        code.put('b', "-...");
        code.put('c', "-.-.");
        code.put('d', "-..");
        code.put('e', ".");
        code.put('f', "..-.");
        code.put('g', "--.");
        code.put('h', "....");
        code.put('i', "..");
        code.put('j', ".---");
        code.put('k', "-.-");
        code.put('l', ".-..");
        code.put('m', "--");
        code.put('n', "-.");
        code.put('o', "---");
        code.put('p', ".--.");
        code.put('q', "--.-");
        code.put('r', ".-.");
        code.put('s', "...");
        code.put('t', "-");
        code.put('u', "..-");
        code.put('v', "...-");
        code.put('w', ".--");
        code.put('x', "-..-");
        code.put('y', "-.--");
        code.put('z', "--..");

        // numbers
        code.put('0', "-----");
        code.put('1', ".----");
        code.put('2', "..---");
        code.put('3', "...--");
        code.put('4', "....-");
        code.put('5', ".....");
        code.put('6', "-....");
        code.put('7', "--...");
        code.put('8', "---..");
        code.put('9', "----.");

        // special characters
        code.put(' ', "/");
        code.put(',', "--..--");
        code.put('.', ".-.-.-");
        code.put('?', "..--..");
        code.put(';', "-.-.-.");
        code.put(':', "---...");
        code.put('(', "-.--.");
        code.put(')', "-.--.-");
        code.put('[', "-.--.");
        code.put(']', "-.--.-");
        code.put('{', "-.--.");
        code.put('}', "-.--.-");
        code.put('+', ".-.-.");
        code.put('-', "-....-");
        code.put('_', "..--.-");
        code.put('"', ".-..-.");
        code.put('\'', ".----.");
        code.put('/', "-..-.");
        code.put('\\', "-..-.");
        code.put('@', ".--.-.");
        code.put('=', "-...-");
        code.put('!', "-.-.--");
    }

    public String translate(String text) {
        StringBuilder translated = new StringBuilder();
        for (Character c : text.toCharArray()) {
            translated.append(code.get(c) + " ");
        }
        return translated.toString();
    }

    public void sound(String[] msg) throws LineUnavailableException, InterruptedException {
        AudioFormat audio = new AudioFormat(44100, 16, 1, true, false);
        DataLine.Info line = new DataLine.Info(SourceDataLine.class, audio);
        SourceDataLine source = (SourceDataLine) AudioSystem.getLine(line);
        source.open(audio);
        source.start();

        int duration = 200;
        int dashDuration = (int) (1.5 * duration);
        int slashDuration = 2 * duration;

        for (String word : msg) {
            for (char c : word.toCharArray()) {
                if (c == '.') {
                    playBeep(source, duration);
                    Thread.sleep(duration);
                } else if (c == '-') {
                    playBeep(source, dashDuration);
                    Thread.sleep(duration);
                } else if (c == '/') {
                    Thread.sleep(slashDuration);
                }
            }

            Thread.sleep(duration);
        }

        source.drain();
        source.stop();
        source.close();
    }

    private void playBeep(SourceDataLine line, int duration) {
        byte[] data = new byte[duration * 44100 / 1000];

        for (int i = 0; i < data.length; i++) {
            double angle = i / (44100.0 / 440) * 2.0 * Math.PI;

            data[i] = (byte) (Math.sin(angle) * 127.0);
        }

        line.write(data, 0, data.length);
    }
}
