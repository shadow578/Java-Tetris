package tetris.core;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyboardHelper
{
	/**
	 * a list of all currently pressed key's keycodes
	 */
	ArrayList<Integer> downKeyCodes = new ArrayList<Integer>();

	/**
	 * a list of all key's keycodes that were checked using wasPressed and weren't released since then
	 * (avoid auto- repeat of keystrokes that jNativeHook likes to do)
	 */
	ArrayList<Integer> pressBlockedKeyCodes = new ArrayList<Integer>();

	/**
	 * try to init the keyboard hook
	 * @return was init successfull?
	 */
	public boolean init()
	{
		try
		{
			disableNativeHookLogs();
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeKeyListener(new KeyListener());
			return true;
		} catch (NativeHookException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * try to disable and dispose the keyboard hook
	 * @return was disabling successfull?
	 */
	public boolean dispose()
	{
		try
		{
			GlobalScreen.unregisterNativeHook();
			return true;
		} catch (NativeHookException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * check if the given key is currently down.
	 * Stays true for as long as the key is held.
	 * @param keyCode the keycode of the key to check. See {@code NativeKeyEvent} for a list of keycodes
	 * @return is the key down?
	 */
	public boolean isDown(int keyCode)
	{
		return downKeyCodes.contains(keyCode);
	}

	/**
	 * check if the given key was pressed
	 * returns true ONLY once until the key is pressed the next time
	 * @param keyCode the keycode of the key to check. See {@code NativeKeyEvent} for a list of keycodes
	 * @return was the key pressed?
	 */
	public boolean wasPressed(int keyCode)
	{
		if (downKeyCodes.contains(keyCode) && !pressBlockedKeyCodes.contains(keyCode))
		{
			pressBlockedKeyCodes.add(keyCode);
			return true;
		}
		return false;
	}

	/**
	 * disable JNativeHook's log entries.
	 * Taken from https://github.com/kwhat/jnativehook/issues/307#issuecomment-695764248
	 */
	void disableNativeHookLogs()
	{
		Logger hookLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		hookLogger.setLevel(Level.WARNING);
		hookLogger.setUseParentHandlers(false);
	}

	/**
	 * native key listener to capture key down events and set {@code KeyboardHelper.lastKeyPressed}
	 */
	private class KeyListener implements NativeKeyListener
	{
		@Override
		public void nativeKeyPressed(NativeKeyEvent e)
		{
			if (!downKeyCodes.contains(e.getKeyCode()))
				downKeyCodes.add(e.getKeyCode());
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent e)
		{
			downKeyCodes.remove((Integer) e.getKeyCode());
			pressBlockedKeyCodes.remove((Integer) e.getKeyCode());
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent e)
		{
		}
	}
}
