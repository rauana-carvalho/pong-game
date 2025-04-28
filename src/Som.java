package src;
import javax.sound.sampled.*;

public final class Som {

    private Som() {}
    
    public static void tocar(String recurso) {
        tocarClip(recurso);
    }

    public static Clip tocarClip(String recurso) {
        try (AudioInputStream in = AudioSystem.getAudioInputStream(
            Som.class.getResource(recurso))) {
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Clip backgroundClip;
    private static long pausedPosition = 0;


    public static void playBackground(String recurso) {
        
        if (backgroundClip != null && backgroundClip.isOpen()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
        try (AudioInputStream in = AudioSystem.getAudioInputStream(
                Som.class.getResource(recurso))) {
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(in);
            backgroundClip.setMicrosecondPosition(pausedPosition);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();
            pausedPosition = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pauseBackground() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            pausedPosition = backgroundClip.getMicrosecondPosition();
            backgroundClip.stop();
        }
    }

    public static void resumeBackground() {
        if (backgroundClip != null) {
            backgroundClip.setMicrosecondPosition(pausedPosition);
            backgroundClip.start();
        }
    }
}