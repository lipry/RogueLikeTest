import javax.sound.sampled.*;

/**
 * This class is used for play sounds and musics.
 * This class is containing a field call currentMusic which is the background music that the player should listen while playing.
 * We can play a sound, stop it, and also change the currentMusic, which will replay it if it's finish.
 * See the currentMusic description for more informations about it.
 * @author Robrock
 *
 */
public class Sound {

	/**
	 * Main background music
	 */
	public static Sound back = loadSound("/musics/back.wav");
	
	/**
	 * Game victory music
	 */
	public static Sound win = loadSound("/musics/win.wav");
	
	/**
	 * Game over music
	 */
	public static Sound end = loadSound("/musics/end.wav");
	
	/**
	 * Boss music
	 */
	public static Sound boss = loadSound("/musics/boss.wav");
	
	/**
	 * Hit sound n°1
	 */
	public static Sound hit1 = loadSound("/sounds/hit1.wav");
	
	/**
	 * Hit sound n°2
	 */
	public static Sound hit2 = loadSound("/sounds/hit2.wav");
	
	/**
	 * Hit sound n°3
	 */
	public static Sound hit3 = loadSound("/sounds/hit3.wav");
	
	/**
	 * Hit sound n°4
	 */
	public static Sound hit4 = loadSound("/sounds/hit4.wav");
	
	/**
	 * Door sound
	 */
	public static Sound door = loadSound("/sounds/door.wav");
	
	/**
	 * Current music which is currently listened
	 */
	public static Sound currentMusic;
	
	/**
	 * Clip stream containing the audio of the sound
	 */
	private Clip clip;
	
	/**
	 * Load the sound following a path and return it.
	 * @param fileName Path to the file containing the audio file
	 * @return Loaded sound
	 */
	public static Sound loadSound(String fileName) {
		Sound sound = new Sound();
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			sound.clip = clip;
		} catch (Exception e) {
			System.out.println(e);
		}
		return sound;
	}

	/**
	 * Play the sound using a thread.
	 */
	public void play() {
		try {
			if (clip != null) {	
				clip.stop();
				clip.setFramePosition(0);
				clip.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Stop the sound using a thread.
	 */
	public void stop(){
		clip.stop();
	}
	
	
	/**
	 * Return true is the currentMusic is finished.
	 * @return True if the music is finished, false in other cases.
	 */
	private boolean isFinish(){
		if (clip.getFrameLength() - clip.getFramePosition() < 1){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Set a new currentMusic. If there was a current music, it stop it and play
	 * the new music. If the parameter is the same music than the current music,
	 * it check if it's finish. In this case, it replay it again.
	 * 
	 * @param s
	 *            Music that we would like to listen during the game.
	 */
	public static void setCurrentMusic(Sound s){
		//If there is a current music
		if (Sound.currentMusic != null){
			//If this is the actual music
			if (Sound.currentMusic == s){
				//If it's finish, we play it again
				if (Sound.currentMusic.isFinish()){
					Sound.currentMusic.play();
				}
			}
			//Else, we stop the current music and delete it
			else {
				Sound.currentMusic.stop();
				Sound.currentMusic = null;
			}
		}	
		
		//If we need to change the music
		if (Sound.currentMusic==null){
			Sound.currentMusic = s;
			Sound.currentMusic.play();
		}
		
	}
	
	
}