package Game;
import java.util.EnumSet;
import java.util.Set;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Keyboard {

	private Set<KeyCode> pressedKeys = EnumSet.noneOf(KeyCode.class);
	private KeyCode[] keys; // 0 = up, 1 = down, 2 = left, 3 = right

	/**
	 * Creates a new keyboard with specified keys
	 * 
	 * @param up
	 *            The KeyCode representing up
	 * @param down
	 *            The KeyCode representing down
	 * @param left
	 *            The KeyCode representing left
	 * @param right
	 *            The KeyCode representing right
	 * @param fire
	 *            The KeyCode representing fire
	 */
	public Keyboard(KeyCode up, KeyCode down, KeyCode left, KeyCode right,
			KeyCode fire) {
		keys = new KeyCode[5];
		keys[0] = up;
		keys[1] = down;
		keys[2] = left;
		keys[3] = right;
		keys[4] = fire;
	}

	/**
	 * DEV
	 * 
	 * @return a string of the keys pressed down
	 */
	public String getKeysDown() {
		return pressedKeys.toString();
	}

	/**
	 * KeyEventHandler for multiple inputs on the keys on this keyboard
	 * 
	 * @param event
	 *            The current KeyEvent
	 * @return An KeyCode array of every key currently pressed down that is also
	 *         on the keyboard
	 */
	public Set<KeyCode> handle(KeyEvent event) {

		if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
			if (hasKey(event.getCode()))
				pressedKeys.add(event.getCode());
		} else if (KeyEvent.KEY_RELEASED.equals(event.getEventType()))
			pressedKeys.remove(event.getCode());

		return pressedKeys;
	}

	/**
	 * Return true if the key is on the keyboard
	 * 
	 * @param key
	 *            The KeyCode for the key to check
	 * @return true if the key is on the keyboard
	 */
	private boolean hasKey(KeyCode key) {
		for (int i = 0; i < keys.length; i++)
			if (keys[i] == key)
				return true;
		return false;
	}

	/**
	 * @return The number of keys on this keyboard
	 */
	public int size() {
		return keys.length;
	}

	/**
	 * Gets the key asigned to this index
	 * 
	 * @param idx
	 *            The index for the key
	 * @return The key on index idx
	 */
	public KeyCode get(int idx) {
		return keys[idx];
	}

	/**
	 * @return The KeyCode representing up
	 */
	public KeyCode getUp() {
		return keys[0];
	}

	/**
	 * @return The KeyCode representing down
	 */
	public KeyCode getDown() {
		return keys[1];
	}

	/**
	 * @return The KeyCode representing left
	 */
	public KeyCode getLeft() {
		return keys[2];
	}

	/**
	 * @return The KeyCode representing right
	 */
	public KeyCode getRight() {
		return keys[3];
	}

	/**
	 * @return The KeyCode representing fire
	 */
	public KeyCode getFire() {
		return keys[4];
	}
}
