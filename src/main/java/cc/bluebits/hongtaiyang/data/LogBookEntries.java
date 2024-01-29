package cc.bluebits.hongtaiyang.data;

import net.minecraft.util.RandomSource;

import java.util.List;

/**
 * A class holding all the log book entries.
 */
public class LogBookEntries {
    /**
     * The list of entries
     */
    private static final List<LogBookEntry> ENTRIES = List.of(
            new LogBookEntry("God", List.of("This is a test entry")),
            new LogBookEntry("Not God", List.of("This is not a test entry")),
            new LogBookEntry("Maybe God", List.of("This might be a test entry", "Or it might not be")),
            new LogBookEntry("Hi Mom", List.of("How did§4§l this§r get here?"))
    );

    

    /**
     * Get a specific entry
     * @param index The index of the entry
     * @return The requested entry
     */
    public static LogBookEntry getEntry(int index) {
        if(index < 0 || index >= ENTRIES.size()) return getError("Index out of bounds");
        return ENTRIES.get(index);
    }

    /**
     * Get the number of entries
     * @return The number of entries
     */
    public static int getEntryCount() {
        return ENTRIES.size();
    }

    /**
     * Get a random entry
     * @param randomSource The random source to use 
     * @return A random entry
     */
    public static LogBookEntry getRandomEntry(RandomSource randomSource) {
        int entryCount = getEntryCount();
        if(entryCount <= 0) { return getError("No entries have been loaded"); }
        
        return getEntry(randomSource.nextInt(entryCount));
    }

    /**
     * Get an error entry
     * @param message The error message
     * @return The error entry
     */
    private static LogBookEntry getError(String message) {
        return new LogBookEntry("Error", List.of("Error: " + message));
    }


    /**
     * A record holding the data for a log book entry
     * @param author The author of the entry
     * @param pages The text contained in the entry, split into pages
     */
    public record LogBookEntry(String author, List<String> pages) { }
}

