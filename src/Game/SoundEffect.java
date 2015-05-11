package Game;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class SoundEffect {

	private AudioClip[] sounds;
	private Random rand;
	private double volume = .5d;

	/**
	 * Creates a new sound effect player
	 * 
	 * @param clip
	 *            Location(s) to clips this will handle
	 */
	public SoundEffect(String... clip) {
		rand = new Random();
		sounds = new AudioClip[clip.length];

		for (int i = 0; i < clip.length; i++)
			sounds[i] = new AudioClip(getClass().getResource("/resources/"+clip[i]).toString());
	}

	/**
	 * Set's the volume for all clips
	 * 
	 * @param volume
	 *            The new volume, valid range is [0, 1] where 0 is muted and 1
	 *            is full volume
	 */
	public void setVolume(double volume) {
		if (volume > 1.0d || volume < 0.0d)
			return;
		this.volume = volume;
	}

	/**
	 * Plays a random sound
	 */
	public void playRandom() {
		int i = rand.nextInt(sounds.length);
		sounds[i].setVolume(volume);
		sounds[i].play();
	}


}
