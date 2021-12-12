
package main.java.model;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Background Music is a global audio player class.
 */
public class BackgroundMusic {
	
	private static HashMap<String, Clip> clips;
	private static int gap;
	
	
	/**
	 * Creates new clips HashMap.
	 */
	public static void init() {
		clips = new HashMap<String, Clip>();
		gap = 0;
	}
	

	/**
	 * Loads up audio located at path "s" and stores
	 * it in the HashMap with key "n".
	 * 
	 * @param s		Source of the music
	 * @param n		Name for the music
	 */
	public static void load(String s, String n) {
		if(clips.get(n) != null) return;
		Clip clip;
		try {
			InputStream in = BackgroundMusic.class.getResourceAsStream(s);
			InputStream bin = new BufferedInputStream(in);
			AudioInputStream ais =
				AudioSystem.getAudioInputStream(bin);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			clips.put(n, clip);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Play the music loaded.
	 * 
	 * @param s 	Name for the music
	 */
	public static void play(String s) {
		play(s, gap);
	}
	
	/**
	 * Play the music with time limit.
	 * 
	 * @param s		Name of the music.
	 * @param i		Integer value for duration to play.
	 */
	public static void play(String s, int i) {
		Clip c = clips.get(s);
		if(c == null) return;
		if(c.isRunning()) c.stop();
		c.setFramePosition(i);
		while(!c.isRunning()) c.start();
	}
	
	/**
	 * Stop the music playing.
	 * 
	 * @param s		Name of the music.
	 */
	public static void stop(String s) {
		if(clips.get(s) == null) return;
		if(clips.get(s).isRunning()) clips.get(s).stop();
	}
	
	/**
	 * Resume the music.
	 * 
	 * @param s		Name of the music.
	 */
	public static void resume(String s) {
		if(clips.get(s).isRunning()) return;
		clips.get(s).start();
	}
	
	/**
	 * Resume the looped music.
	 * 
	 * @param s		Name of the music.
	 */
	public static void resumeLoop(String s) {
		Clip c = clips.get(s);
		if(c == null) return;
		c.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * Loop the music loaded.
	 * 
	 * @param s		Name of the music.
	 */
	public static void loop(String s) {
		loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
	}
	
	/**
	 * Loop the music with frame set.
	 * 
	 * @param s			Name of the music.
	 * @param frame		Integer value for frame.
	 */
	public static void loop(String s, int frame) {
		loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
	}
	
	/**
	 * Loop the music with start and end time.
	 * 
	 * @param s			Name of the music.
	 * @param start		Start music time.
	 * @param end		End music time.
	 */
	public static void loop(String s, int start, int end) {
		loop(s, gap, start, end);
	}
	
	/**
	 * Loop the music with frame, start and end.
	 * 
	 * @param s			Name of the music.
	 * @param frame		Integer value for frame.
	 * @param start		Start music time.
	 * @param end		End music time.
	 */
	public static void loop(String s, int frame, int start, int end) {
		Clip c = clips.get(s);
		if(c == null) return;
		if(c.isRunning()) c.stop();
		c.setLoopPoints(start, end);
		c.setFramePosition(frame);
		c.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void setPosition(String s, int frame) {
		clips.get(s).setFramePosition(frame);
	}
	
	public static int getFrames(String s) { return clips.get(s).getFrameLength(); }
	public static int getPosition(String s) { return clips.get(s).getFramePosition(); }
	
	public static void close(String s) {
		stop(s);
		clips.get(s).close();
	}
	
	/**
	 * Set the volume of the music loaded.
	 * 
	 * @param s		Name of the music.
	 * @param f		Integer value for music's volume.
	 */
	public static void setVolume(String s, float f) {
		Clip c = clips.get(s);
		if(c == null) return;
		FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		vol.setValue(f);
	}
	
	public static boolean isPlaying(String s) {
		Clip c = clips.get(s);
		if(c == null) return false;
		return c.isRunning();
	}
	
}
