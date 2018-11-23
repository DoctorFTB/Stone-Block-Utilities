package ftblag.stoneblockutilities.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import ftblag.stoneblockutilities.events.SBUEvents;
import ftblag.stoneblockutilities.gson.SBUGson.Drops;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.LoaderException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SBUGsonParser {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static SBUGson.Cfg cfg;

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
        cfg.drops = new ArrayList<>(Arrays.asList(new Drops("minecraft:stone:0", "exnihilocreatio:item_pebble:0:4", "exnihilocreatio:item_pebble:0:2", 5, 3.5)));
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(file));
            fw.write(gson.toJson(cfg));
            fw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void parse() {
        cfg.drops.forEach(i -> {
            try {
                String[] block1 = i.block.split(":");
                Block block = Block.getBlockFromName(block1[0] + ":" + block1[1]);
                int blockMeta = Integer.parseInt(block1[2]);
                IBlockState state = block.getStateFromMeta(blockMeta);

                String[] item1 = i.drop_with.split(":");
                Item with = Item.getByNameOrId(item1[0] + ":" + item1[1]);
                ItemStack drop_with = new ItemStack(with, Integer.parseInt(item1[3]), Integer.parseInt(item1[2]));

                String[] item2 = i.drop_without.split(":");
                Item without = Item.getByNameOrId(item2[0] + ":" + item2[1]);
                ItemStack drop_without = new ItemStack(without, Integer.parseInt(item2[3]), Integer.parseInt(item2[2]));

                map.put(state, new CustomDrop(new ItemStack(block, 1, blockMeta), drop_with, drop_without, i.chance, i.speed));
            } catch (Exception e) {
                throw new LoaderException("Failed to parse cfg!", e);
            }
        });
    }

    public static HashMap<IBlockState, CustomDrop> map = new HashMap<>();

    public static CustomDrop getFromOriginal(ItemStack original) {
        for (CustomDrop drop : map.values())
            if (ItemStack.areItemsEqual(drop.original, original))
                return drop;
        return null;
    }

    public static void drop(BreakEvent e) {
        if (map.containsKey(e.getState())) {
            CustomDrop drop = map.get(e.getState());
            if (SBUEvents.rnd.nextInt(100) < drop.chance) {
                spawnItem(e, drop.dropWith.copy());
            } else {
                spawnItem(e, drop.dropWithout.copy());
            }
        }
    }

    public static void spawnItem(BreakEvent e, ItemStack stack) {
        e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getPos().getX(), e.getPos().getY(), e.getPos().getZ(), stack));
    }

    public static class CustomDrop {
        public ItemStack original, dropWith, dropWithout;
        public int chance;
        public double speed;

        public CustomDrop(ItemStack original, ItemStack dropWith, ItemStack dropWithout, int chance, double speed) {
            this.original = original;
            this.dropWith = dropWith;
            this.dropWithout = dropWithout;
            this.chance = chance;
            this.speed = speed;
        }
    }
}
