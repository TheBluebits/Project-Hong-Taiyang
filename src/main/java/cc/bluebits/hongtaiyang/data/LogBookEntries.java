package cc.bluebits.hongtaiyang.data;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;

import java.util.List;

public class LogBookEntries {
    public static List<LogBookEntry> entries;

    public static void initEntries(int nEntries) {

        for (int i = 0; i < nEntries; i++) {
            entries.add(new LogBookEntry(
                    Component.translatable("logbook.author." + i).getString(),
                    Component.translatable("logbook.entry." + i).getString()));
        }
    }

    public static LogBookEntry getEntry(int index) {
        return entries.get(index);
    }

    public static int getEntryCount() {
        return entries.size();
    }

    public static LogBookEntry getRandomEntry(RandomSource randomSource) {
        return getEntry(randomSource.nextInt(getEntryCount()));
    }

    public record LogBookEntry(String author, String text) {
    }

}

