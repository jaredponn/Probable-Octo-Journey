package Components;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Resources.GameResources;
/**
 * Sound asset component.
 *	Used to store sound effects for a entity set
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
			} catch (UnsupportedAudioFileException e) {
				System.out.println(
					"UnsupportedAudioFileException has occured inside sound asset");
				soundAsset.add(GameResources.gunSound);
				continue;
			} catch (IOException a) {
				System.out.println(
					"IOException has occured inside sound asset");
				soundAsset.add(GameResources.gunSound);
				continue;
			} catch (LineUnavailableException b) {
				System.out.println(
					"LineUnavailableException has occured inside sound asset");
				System.out.println(b.toString());
				soundAsset.add(GameResources.gunSound);
				continue;
			}
		}
	}
	public void playSoundAt(int i)
	{
		soundAsset.get(i).play();
	}

	public void playContinuouslyAt(int i)
	{
		soundAsset.get(i).playContinuously();
	}
	public void endPlayingAt(int i)
	{
		soundAsset.get(i).end();
	}
	public void restartPlayingAt(int i)
	{
		soundAsset.get(i).restart();
	}
	public boolean getIsPlayingAt(int i)
	{
		return soundAsset.get(i).getIsPlaying();
	}

	public void print()
	{
	}
}
