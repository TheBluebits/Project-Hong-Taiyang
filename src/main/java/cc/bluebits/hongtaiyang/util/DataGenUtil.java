package cc.bluebits.hongtaiyang.util;

import net.minecraft.util.Tuple;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataGenUtil {
	/**
	 * Converts a texture list, consisting of a key string and a name string, into a flagged map, setting the {@code isModded} flag for all textures
	 * @param list The texture list that will be converted
	 * @param defaultFlagValue The value that will be assigned to each texture for the {@code isModded} flag   
	 * @return A map of textures, consisting of key and name, as the keys and boolean flags with the value of {@code defaultFlagValue} as the values
	 */
	public static Map<Tuple<String, String>, Boolean> convertTextureListToFlaggedTextureMap(List<Tuple<String, String>> list, boolean defaultFlagValue) {
		Map<Tuple<String, String>, Boolean> map = null;

		if(list != null) {
			map = new LinkedHashMap<>();
			for (Tuple<String, String> texture : list) {
				map.put(texture, defaultFlagValue);
			}
		}
		
		return map;
	}

	/**
	 * Converts a texture list, consisting of a key string and a name string, into a flagged map, setting the {@code isModded} flag for all textures to true
	 * @param list The texture list that will be converted
	 * @return A map of textures, consisting of key and name, as the keys and boolean flags set to {@code true} as the values
	 */
	public static Map<Tuple<String, String>, Boolean> convertTextureListToFlaggedTextureMap(List<Tuple<String, String>> list) {
		return convertTextureListToFlaggedTextureMap(list, true);
	}
}
