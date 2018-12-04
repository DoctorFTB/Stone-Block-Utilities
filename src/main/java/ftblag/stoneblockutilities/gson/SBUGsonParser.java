package ftblag.stoneblockutilities.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import net.minecraftforge.fml.common.LoaderException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SBUGsonParser {

    public static SBUGson.Cfg cfg;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void parseFile(File file) {
        if (!file.exists()) {
            try {
                createDefault(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            cfg = gson.fromJson(new BufferedReader(new FileReader(file)), SBUGson.Cfg.class);
        } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
            throw new LoaderException("Failed to load cfg!", e);
        }
    }

    private static void createDefault(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        SBUGson.Cfg cfg = new SBUGson.Cfg();
        cfg.active_render = true;
        cfg.handDrops = new ArrayList<>(Arrays.asList(new SBUGson.HandDrops("minecraft:stone:0", "exnihilocreatio:item_pebble:0", 4, 2, 5, 3.5)));
        cfg.crookDrops = new ArrayList<>(Arrays.asList(new SBUGson.CrookDrops("minecraft:dirt:0", true, 1, Arrays.asList(new SBUGson.CrookDropGson("minecraft:sapling:0", 1), new SBUGson.CrookDropGson("minecraft:sapling:1", 1), new SBUGson.CrookDropGson("minecraft:sapling:2", 1), new SBUGson.CrookDropGson("minecraft:sapling:3", 1), new SBUGson.CrookDropGson("minecraft:sapling:4", 1), new SBUGson.CrookDropGson("minecraft:sapling:5", 1)))));
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(file));
            fw.write(gson.toJson(cfg));
            fw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
