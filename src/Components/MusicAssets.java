package Components;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Resources.GameResources;
/**
 * Sound asset component for music and sounds that NEED TO BE STOPPED AND
 *REPLAYED.
 Used to store sound for a entity set
 *
 * Date: March 24, 2019
 * @author Haiyang
 * @version 1.0
 */
import poj.Component.Component;
public class SoundAssets implements Component
{
	private final ArrayList<Sound> soundAsset = new ArrayList<Sound>();
	public SoundAssets(final ArrayList<Sound> soundBuffer)
	{
		for (int i = 0; i < soundBuffer.size(); ++i) {
			try { // deep copy
				soundAsset.add(new Sound(soundBuffer.get(i)));
			} catch (NullPointerException e) {
				poj.Logger.Logger.logMessage(
					"NullPointerException has occured when deep copying the sounds inside sound asset. The program should continue to work, but at index "
						+ i
						+ " inside soundAsset the sound is replaced by the gunSound",
					poj.Logger.LogLevels.VERBOSE);
				e.printStackTrace();
				soundAsset.add(GameResources.gunSound);
				continue;
			} catch (UnsupportedAudioFileException e) {
				poj.Logger.Logger.logMessage(
					"UnsupportedAudioFileException has occured when deep copying the sounds inside sound asset. The program should continue to work, but at index "
						+ i
						+ " inside soundAsset the sound is replaced by the gunSound",
					poj.Logger.LogLevels.VERBOSE);
				e.printStackTrace();
				soundAsset.add(GameResources.gunSound);
				continue;
			} catch (IOException e) {
				poj.Logger.Logger.logMessage(
					"IOException has occured when deep copying the sounds inside sound asset. The program should continue to work, but at index "
						+ i
						+ " inside soundAsset the sound is replaced by the gunSound",
					poj.Logger.LogLevels.VERBOSE);
				e.printStackTrace();
				soundAsset.add(GameResources.gunSound);
				continue;
			} catch (LineUnavailableException e) {
				poj.Logger.Logger.logMessage(
					"LineUnavailableException has occured when deep copying the sounds inside sound asset. The program should continue to work, but at index "
						+ i
						+ " inside soundAsset the sound is replaced by the gunSound",
					poj.Logger.LogLevels.VERBOSE);
				e.printStackTrace();
				soundAsset.add(GameResources.gunSound);
				continue;
			}
		}
	}
	public void playSoundAt(int i)
	{
		try {
			soundAsset.get(i).play();
		} catch (ArrayIndexOutOfBoundsException e) {
			poj.Logger.Logger.logMessage(
				"Array out of bounds in playSoundAt in sound assets! the index number was "
					+ i
					+ " but the largest index in the asset is "
					+ (soundAsset.size() - 1),
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}

	public void playContinuouslyAt(int i)
	{
		try {
			soundAsset.get(i).playContinuously();
		} catch (ArrayIndexOutOfBoundsException e) {
			poj.Logger.Logger.logMessage(
				"Array out of bounds in playContinuouslyAt in sound assets! the index number was "
					+ i
					+ " but the largest index in the asset is "
					+ (soundAsset.size() - 1),
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}
	public void endPlayingAt(int i)
	{
		try {
			soundAsset.get(i).end();
		} catch (ArrayIndexOutOfBoundsException e) {
			poj.Logger.Logger.logMessage(
				"Array out of bounds in endPlayingAt in sound assets! the index number was "
					+ i
					+ " but the largest index in the asset is "
					+ (soundAsset.size() - 1),
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}
	public void restartPlayingAt(int i)
	{
		try {
			soundAsset.get(i).restart();
		} catch (ArrayIndexOutOfBoundsException e) {
			poj.Logger.Logger.logMessage(
				"Array out of bounds in restartPlayingAt in sound assets! the index number was "
					+ i
					+ " but the largest index in the asset is "
					+ (soundAsset.size() - 1),
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}
	public boolean getIsPlayingAt(int i)
	{
		try {
			return soundAsset.get(i).getIsPlaying();
		} catch (ArrayIndexOutOfBoundsException e) {
			poj.Logger.Logger.logMessage(
				"Array out of bounds in getIsPlayingAt in sound assets! the index number was "
					+ i
					+ " but the largest index in the asset is "
					+ (soundAsset.size() - 1),
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();

			// if array out of bound exception happens
			return false;
		}
	}

	public void print()
	{
	}
}
