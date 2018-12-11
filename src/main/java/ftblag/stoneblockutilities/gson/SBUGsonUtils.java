package ftblag.stoneblockutilities.gson;

import ftblag.stoneblockutilities.events.SBUEvents;
import ftblag.stoneblockutilities.registry.SBURegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.LoaderException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SBUGsonUtils {

    public static void parse() {
        SBUGsonParser.cfg.handDrops.forEach(i -> {
            try {
                String[] block1 = i.block.split(":");
                Block block = Block.getBlockFromName(block1[0] + ":" + block1[1]);
                int blockMeta = Integer.parseInt(block1[2]);
                IBlockState state = block.getStateFromMeta(blockMeta);

                String[] drop1 = i.drop.split(":");
                String modidName = (drop1[0] + ":" + drop1[1]);
                Item drop;
                if (modidName.equals("exnihilocreatio:item_pebble") && !SBURegistry.isExNihiloLoaded)
                    drop = Item.getByNameOrId("stoneblockutilities:stonepebble");
                else
                    drop = Item.getByNameOrId(modidName);

                ItemStack drop_with = new ItemStack(drop, i.drop_with, Integer.parseInt(drop1[2]));
                ItemStack drop_without = new ItemStack(drop, i.drop_without, Integer.parseInt(drop1[2]));

                handDrops.put(state, new HandDrop(new ItemStack(block, 1, blockMeta), drop_with, drop_without, i.chance, i.speed));
            } catch (Exception e) {
                throw new LoaderException("Failed to parse cfg! May be wrong elements!", e);
            }
        });
        SBUGsonParser.cfg.crookDrops.forEach(i -> {
            try {
                String[] block1 = i.block.split(":");
                Block block = Block.getBlockFromName(block1[0] + ":" + block1[1]);
                int blockMeta = Integer.parseInt(block1[2]);
                IBlockState state = block.getStateFromMeta(blockMeta);

                List<ItemStack> drops = new ArrayList<>();
                i.drops.forEach(d -> {
                    String [] drop1 = d.drop.split(":");
                    Item drop = Item.getByNameOrId(drop1[0] + ":" + drop1[1]);
                    drops.add(new ItemStack(drop, d.drop_size, Integer.parseInt(drop1[2])));
                });
                crookDrops.put(state, new CrookDrop(new ItemStack(block, 1, blockMeta), /*i.randomDropAmount > drops.size() ? false : */i.randomDrop, i.randomDropAmount, drops));
            } catch (Exception e) {
                throw new LoaderException("Failed to parse cfg! May be wrong elements!", e);
            }
        });
    }

    public static void spawnItem(BlockEvent.BreakEvent e, ItemStack stack) {
        e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getPos().getX() + .5, e.getPos().getY() + .5, e.getPos().getZ() + .5, stack));
    }

    public static HashMap<IBlockState, HandDrop> handDrops = new HashMap<>();
    public static HashMap<IBlockState, CrookDrop> crookDrops = new HashMap<>();

    public static void dropHand(BlockEvent.BreakEvent e) {
        if (handDrops.containsKey(e.getState())) {
            HandDrop drop = handDrops.get(e.getState());
            if (SBUEvents.rnd.nextInt(100) < drop.chance) {
                spawnItem(e, drop.dropWith.copy());
            } else {
                spawnItem(e, drop.dropWithout.copy());
            }
        }
    }

    public static void dropCrook(BlockEvent.BreakEvent e, EntityPlayer player) {
        if (crookDrops.containsKey(e.getState())) {
            CrookDrop drop = crookDrops.get(e.getState());

            if (drop.randomDrop) {
                for (int i = 0; i < drop.randomDropAmount; i++)
                    spawnItem(e, drop.drops.get(SBUEvents.rnd.nextInt(drop.drops.size())));
            } else {
                for (ItemStack stack : drop.drops)
                    spawnItem(e, stack.copy());
            }
            e.getWorld().setBlockToAir(e.getPos());
            player.getHeldItemMainhand().damageItem(1, player);
        }
    }

    public static HandDrop getHandDropFromOriginal(ItemStack original) {
        for (HandDrop drop : handDrops.values())
            if (ItemStack.areItemsEqual(drop.original, original))
                return drop;
        return null;
    }

    public static CrookDrop getCrookDropFromOriginal(ItemStack original) {
        for (CrookDrop drop : crookDrops.values())
            if (ItemStack.areItemsEqual(drop.original, original))
                return drop;
        return null;
    }

    public static class HandDrop {
        public ItemStack original, dropWith, dropWithout;
        public int chance;
        public double speed;

        public HandDrop(ItemStack original, ItemStack dropWith, ItemStack dropWithout, int chance, double speed) {
            this.original = original;
            this.dropWith = dropWith;
            this.dropWithout = dropWithout;
            this.chance = chance;
            this.speed = speed;
        }
    }

    public static class CrookDrop {
        public ItemStack original;
        public boolean randomDrop;
        public int randomDropAmount;
        public List<ItemStack> drops;

        public CrookDrop(ItemStack original, boolean randomDrop, int randomDropAmount, List<ItemStack> drops) {
            this.original = original;
            this.randomDrop = randomDrop;
            this.randomDropAmount = randomDropAmount;
            this.drops = drops;
        }
    }
}
