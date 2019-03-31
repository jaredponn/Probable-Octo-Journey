package Components;

/**
 * Sound component.
 *	Used to create sound effects, ONLY support .wav files because Java sound
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
	private String audioPath;

	/**
	 * Constructor.
	 *
	 * @param audioPath the path of .wav (and .wav file ONLY!) file
	 */
	public Sound(String audioPath) throws UnsupportedAudioFileException,
					      IOException,
					      LineUnavailableException
	{
		this.audioPath = audioPath;
		audioInputStream = AudioSystem.getAudioInputStream(
			new File(audioPath).getAbsoluteFile());
		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioInputStream);
	}

	public Sound(Sound anotherSound) throws UnsupportedAudioFileException,
						IOException,
						LineUnavailableException
	{
		this.audioPath = anotherSound.getAudioPath();
		this.audioInputStream = anotherSound.getAIS();
		this.clip = anotherSound.getClip();
	}

	public Clip getClip() throws UnsupportedAudioFileException, IOException,
				     LineUnavailableException
	{

		AudioInputStream ais = AudioSystem.getAudioInputStream(
			new File(this.audioPath).getAbsoluteFile());
		// create clip reference
		Clip tempClip = AudioSystem.getClip();

		// open audioInputStream to the clip
		tempClip.open(ais);
		return tempClip;
	}
	public AudioInputStream getAIS() throws UnsupportedAudioFileException,
						IOException,
						LineUnavailableException
	{
		AudioInputStream ais = AudioSystem.getAudioInputStream(
			new File(this.audioPath).getAbsoluteFile());
		return ais;
	}
	public String getAudioPath()
	{
		return this.audioPath;
	}

	// If there occurs an exception it will
	// not play the sound and will not crashs the game
	public void play()
	{
		try {
			clip.setFramePosition(0);
			clip.start();
			this.isPlaying = true;
			// this.isPlaying = clip.isActive();
		} catch (NullPointerException e) {
			// if the sound is null
			poj.Logger.Logger.logMessage(
				"NullPointerException has occured when playing the sound with sound path "
					+ this.audioPath,
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}

	public void playContinuously()
	{
		try {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (NullPointerException e) {
			// if the sound is null
			poj.Logger.Logger.logMessage(
				"NullPointerException has occured when playing the sound CONTINUOUSLY with sound path "
					+ this.audioPath,
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}


	public void end()
	{
		clip.stop();
		clip.close();
		resetClip();
		isPlaying = false;
	}

	public void restart()
	{
		try {
			end();
			clip.start();
			play();
			isPlaying = true;
		} catch (NullPointerException e) {
			// if the sound is null
			poj.Logger.Logger.logMessage(
				"NullPointerException has occured when restarting the sound with sound path "
					+ this.audioPath,
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}

	public void resetClip()
	{
		try {
			this.audioInputStream = AudioSystem.getAudioInputStream(
				new File(audioPath).getAbsoluteFile());
			clip = AudioSystem.getClip();
			// open audioInputStream to the clip
			clip.open(this.audioInputStream);
		} catch (NullPointerException e) {
			poj.Logger.Logger.logMessage(
				"NullPointerException has occured when reseting the sound with sound path "
					+ this.audioPath,
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			poj.Logger.Logger.logMessage(
				"LineUnavailableException has occured when reseting the sound with sound path "
					+ this.audioPath,
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		} catch (IOException e) {
			poj.Logger.Logger.logMessage(
				"IOException has occured when reseting the sound with sound path "
					+ this.audioPath,
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			poj.Logger.Logger.logMessage(
				"UnsupportedAudioFileException has occured when reseting the sound with sound path "
					+ this.audioPath,
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}

	public boolean getIsPlaying()
	{
		return this.isPlaying;
	}

	public void print()
	{
	}
}
