package ftblag.stoneblockutilities.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;

public class SBUConfig {

	public static int chance = 5, drop_with = 4, drop_without = 2;

	public static void setupConfig(Configuration cfg, Logger logger) {
		try {
			chance = getInt("chance", cfg, "chance", chance, "Chance to drop more pebbles.");
			drop_with = getInt("chance", cfg, "drop_with_chance", drop_with,
			        "How many drops with pebbles with chance.");
			drop_without = getInt("chance", cfg, "drop_without_chance", drop_without,
			        "How many drops without pebbles with chance.");
		} catch (Exception e) {
			logger.log(Level.ERROR, "An error occured loading SBU config!");
			e.printStackTrace();
		} finally {
			if (cfg.hasChanged())
				cfg.save();
		}
	}

	private static int getInt(String category, Configuration cfg, String name, int def, String desc) {
		return cfg.get(category, name, def, desc).getInt();
	}
}