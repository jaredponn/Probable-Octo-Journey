package Components;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Resources.GameResources;
import poj.Component.Component;

/**
 * Sound effects asset component mainly for sound effects.
 * Used to store sound effects for a entity set
 *
 * Date: March 31, 2019
 * @author Haiyang
 * @version 1.0
 */
public class SoundEffectAssets implements Component
{
	private final ArrayList<String> soundEffectAsset;

	/**
	 * Constructor
	 * @param soundEffectBuffer -- sound effect buffer
	 */
	public SoundEffectAssets(ArrayList<String> soundEffectBuffer)
	{
		soundEffectAsset = soundEffectBuffer;
	}

	/**
	 * plays sound effect at
	 * @param i -- index
	 */
	public void playSoundEffectAt(int i)
	{
		try {
			Components.Sound.playSoundEffect(
				soundEffectAsset.get(i));
		} catch (ArrayIndexOutOfBoundsException e) {
			poj.Logger.Logger.logMessage(
				"Array out of bounds in playSoundEffectAt in sound EFFECT assets! the index number was "
					+ i
					+ " but the largest index in the asset is "
					+ (soundEffectAsset.size() - 1),
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		} catch (NullPointerException e) {
			poj.Logger.Logger.logMessage(
				"NullPointerException in playSoundEffectAt in sound EFFECT assets! the index number was "
					+ i
					+ " and the sound effect path at that index is "
					+ soundEffectAsset.get(i),
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}

	/**
	 * print
	 */
	public void print()
	{
		System.out.println("sound component");
	}
}
