package code.javacode;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class SoundPlayer {

    private AudioInputStream audioStream;
    private Clip clip;
    public SoundPlayer(String soundFile) {
        try {
            // 获取音频输入流
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundFile));

            // 获取音频剪辑对象
            this.clip = AudioSystem.getClip();
            // 打开音频剪辑并加载音频数据
            clip.open(audioStream);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play(boolean loop) {
        if (clip != null) {
            if(loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        }
    }
    public void play() {
        try {
            if (clip != null && !clip.isActive()) {
                new Thread(() -> {
                    synchronized (clip) {
                        clip.stop();
                        clip.setFramePosition(0);
                        clip.start();
                        clip.addLineListener(event -> {
                            if (event.getType() == LineEvent.Type.STOP) {
                                clip.close();
                            }
                        });
                    }
                }).start();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }
}
