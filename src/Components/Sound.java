package Components;

/**
 * Sound component.
 *	Used to play sound effects, ONLY support .wav files because Java sound
 *API
 *
 * Date: March 24, 2019
 * @author Haiyang
 * @version 1.0
 */
import poj.Component.Component;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// took the basic structures from
// https://www.geeksforgeeks.org/play-audio-file-using-java/
public class Sound implements Component
{

	private AudioInputStream audioInputStream;
	private Clip clip;
	private volatile boolean isPlaying = false;

	/**
	 * Constructor.
	 *
	 * @param audioPath the path of .wav (and .wav file ONLY!) file
	 */
	public Sound(String audioPath) throws UnsupportedAudioFileException,
					      IOException,
					      LineUnavailableException
	{
		audioInputStream = AudioSystem.getAudioInputStream(
			new File(audioPath).getAbsoluteFile());
		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioInputStream);
	}

	public Clip getClip()
	{
		return this.clip;
	}

	public void play()
	{
		clip.setFramePosition(0);
		clip.start();
		this.isPlaying = true;
		// this.isPlaying = clip.isActive();
	}

	public void playContinuously()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void end()
	{
		clip.stop();
		clip.close();
		isPlaying = false;
	}

	public void restart()
	{
		clip.stop();
		clip.close();
		clip.start();
		play();
		isPlaying = true;
	}

	public boolean getIsPlaying()
	{
		return this.isPlaying;
	}

	public void print()
	{
	}
}
