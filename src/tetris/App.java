package tetris;

import java.util.Scanner;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import tetris.core.TetrisGame;
import tetris.core.renderers.console.ConsoleRendererFactory;

public class App
{
	public static void main(String[] args) throws InterruptedException
	{
		// create and init renderer factory
		ConsoleRendererFactory rendererFactory = new ConsoleRendererFactory();

		// ask user if color should be enabled
		Scanner sc = new Scanner(System.in);
		rendererFactory.setColorEnabled(askEnableColor(sc));
		sc.close();
		
		// start the game
		new TetrisGame(rendererFactory).play();
	}

	/**
	 * ask the user if he wants to enable colored rendering
	 * @param sc the scanner to use for reading user input
	 * @return enable color rendering? default no/false
	 */
	static boolean askEnableColor(Scanner sc)
	{
		// ask user
		// include a sample colored line to test if color actually works
		char choice;
		System.out.printf("%n" + Ansi.colorize("Color", Attribute.RED_TEXT()) + " " + Ansi.colorize("Sample%n", Attribute.GREEN_TEXT()) + "%n");
		do
		{
			System.out.printf(
					"%ndo you want to enable ANSI Color rendering? %nif the above line is NOT colored correctly, do not enable this option.%n (yes/NO): ");
			choice = Character.toLowerCase(sc.next().charAt(0));
		} while (choice != 'y' && choice != 'n');

		// default to NO if choice is invalid
		return choice == 'y';
	}
}
