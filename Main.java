import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String filePath = "your filePath";
        String text = readTextFromFile(filePath);
        simulateKeyboardInput(text);
    }

    private static String readTextFromFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void simulateKeyboardInput(String text) {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c == '\n') {
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                } else if (c == '%') {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_5);
                    robot.keyRelease(KeyEvent.VK_5);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                } else {
                    int keyCode;
                    if (Character.isUpperCase(c)) {
                        keyCode = KeyEvent.VK_SHIFT;
                        robot.keyPress(keyCode);
                    }
                    keyCode = KeyEvent.getExtendedKeyCodeForChar(Character.toLowerCase(c));
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                    if (Character.isUpperCase(c)) {
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                    }
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}