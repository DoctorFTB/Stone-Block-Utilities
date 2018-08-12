package ftblag.stoneblockutilities.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;

public class SBUConfig {

    public static int     chance            = 5, drop_with = 4, drop_without = 2;
    public static boolean fast_break        = true, active_render = true;
    public static double  speed_break_stone = 1.0D;
	public static int     clear_list_chance = 25, add_to_list_chance = 15;
    public static boolean disable_asm       = false;

    public static void setupConfig(Configuration cfg, Logger logger) {
        try {
            chance = getIntRange("chance", cfg, "chance", chance, "Chance to drop more pebbles. # min 0, max 100", 0,
                    100);
            drop_with = getInt("chance", cfg, "drop_with_chance", drop_with,
                    "How many drops with pebbles with chance.");
            drop_without = getInt("chance", cfg, "drop_without_chance", drop_without,
                    "How many drops without pebbles with chance.");
            fast_break = getBoolean("speed", cfg, "fast_break", fast_break,
                    "If true player get more speed to break stone. newspeed = \"speed break stone\" (cfg).");
            speed_break_stone = cfg.get("speed", "speed break stone", speed_break_stone,
                    "If fast break true - change speed. # min 0.1, max 100.0", 0.1, 100.0).getDouble();
            active_render = getBoolean("render", cfg, "active_render", active_render,
                    "If true render items on stone workbench is on.");
            clear_list_chance = getIntRange("chance", cfg, "clear_list_chance", chance,
                "(work only if \"disable_asm\" is false) Chance to clear list with mob spawn positions. # min 0, max 101", 0, 101);
            add_to_list_chance = getIntRange("chance", cfg, "add_to_list_chance", add_to_list_chance,
                "(work only if \"disable_asm\" is false) Chance to add mob spawn position to list. # min 0, max 101", 0, 101);
            disable_asm = getBoolean("asm", cfg, "disable_asm", disable_asm,
                "If true asm with change getter mob spawn not enabled.");
        } catch (Exception e) {
            logger.log(Level.ERROR, "An error occured loading SBU config!");
            e.printStackTrace();
        } finally {
            if (cfg.hasChanged())
                cfg.save();
        }
    }

    private static boolean getBoolean(String category, Configuration cfg, String name, boolean def, String desc) {
        return cfg.get(category, name, def, desc).getBoolean();
    }

    private static int getInt(String category, Configuration cfg, String name, int def, String desc) {
        return cfg.get(category, name, def, desc).getInt();
    }

    private static int getIntRange(String cat, Configuration cfg, String name, int def, String desc, int min, int max) {
        return cfg.get(cat, name, def, desc, min, max).getInt();
    }
}