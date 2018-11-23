package ftblag.stoneblockutilities.events;

import ftblag.stoneblockutilities.StoneBlockUtilities;
import ftblag.stoneblockutilities.gson.SBUGsonParser;
import ftblag.stoneblockutilities.registry.SBURegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = StoneBlockUtilities.MODID)
public class SBUEvents {

    public static Random rnd = new Random();

    @SubscribeEvent
    public static void block(BreakEvent e) {
        if (!(e.getPlayer() instanceof FakePlayer) && !e.getPlayer().capabilities.isCreativeMode
                && !e.getWorld().isRemote && e.getPlayer().getHeldItemMainhand().isEmpty())
            SBUGsonParser.drop(e);
        else if (e.getState().getBlock() == Blocks.DIRT && !e.getWorld().isRemote
                && e.getPlayer().getHeldItemMainhand().getItem() == SBURegistry.crook) {
            e.getPlayer().getHeldItemMainhand().damageItem(1, e.getPlayer());
            e.getWorld().setBlockState(e.getPos(), Blocks.AIR.getDefaultState());
            SBUGsonParser.spawnItem(e, new ItemStack(Blocks.SAPLING, 1, rnd.nextInt(6)));
        }
    }

    @SubscribeEvent
    public static void speed(BreakSpeed e) {
        if (SBUGsonParser.map.containsKey(e.getState()) && e.getEntityPlayer().getHeldItemMainhand().isEmpty()
            && SBUGsonParser.map.get(e.getState()).speed > 0.001D) {
            e.setNewSpeed((float) SBUGsonParser.map.get(e.getState()).speed);
        }
    }
}