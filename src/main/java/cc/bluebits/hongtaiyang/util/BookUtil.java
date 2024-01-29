package cc.bluebits.hongtaiyang.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A utility class for dealing with books.
 */
@SuppressWarnings("unused")
public final class BookUtil {
	/**
	 * The maximum width of a text on a page in pixels. Hardcoded as {@code private} in {@link BookEditScreen}.
	 */
	public static final int MAX_TEXT_WIDTH_PER_PAGE = 114;
	/**
	 * The maximum height of a text on a page in pixels. Hardcoded as {@code private} in {@link BookEditScreen}.
	 */
	public static final int MAX_TEXT_HEIGHT_PER_PAGE = 128;


	/**
	 * Split a text into book pages.
	 * @param text The text to split
	 * @return The list of pages
	 */
	public static List<String> splitIntoPages(String text) {
		ArrayList<String> pages = new ArrayList<>();
		Font font = Minecraft.getInstance().font;
		
		StringBuilder buffer = new StringBuilder();
		List<Character> chars = text.chars().mapToObj(c -> (char) c).toList();
		Iterator<Character> iterator = chars.iterator();
		
		while(iterator.hasNext()) {
			char c = iterator.next();
			
			buffer.append(c);
			if(iterator.hasNext() && font.wordWrapHeight(buffer.toString(), MAX_TEXT_WIDTH_PER_PAGE) <= MAX_TEXT_HEIGHT_PER_PAGE) continue;
			
			pages.add(buffer.toString());
			buffer = new StringBuilder();
			buffer.append(c);
		}
		
		return pages;
	}
}
