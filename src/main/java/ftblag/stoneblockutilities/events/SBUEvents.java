package ftblag.stoneblockutilities.events;

import java.util.Random;

import exnihilocreatio.ModItems;
import ftblag.stoneblockutilities.StoneBlockUtilities;
import ftblag.stoneblockutilities.config.SBUConfig;
import ftblag.stoneblockutilities.registry.SBURegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = StoneBlockUtilities.MODID)
public class SBUEvents {

    static Random rnd = new Random();

    @SubscribeEvent
    public static void block(BreakEvent e) {
        if (!(e.getPlayer() instanceof FakePlayer) && !e.getPlayer().capabilities.isCreativeMode
                && e.getState().getBlock() == Blocks.STONE && !e.getWorld().isRemote
                && e.getPlayer().getHeldItemMainhand() == ItemStack.EMPTY)
            spawnEI(e, new ItemStack(ModItems.pebbles,
                    rnd.nextInt(100) < SBUConfig.chance ? SBUConfig.drop_with : SBUConfig.drop_without, 0));
        else if (e.getState().getBlock() == Blocks.DIRT && !e.getWorld().isRemote
                && e.getPlayer().getHeldItemMainhand().getItem() == SBURegistry.crook) {
            e.getPlayer().getHeldItemMainhand().damageItem(1, e.getPlayer());
            e.getWorld().setBlockState(e.getPos(), Blocks.AIR.getDefaultState());
            spawnEI(e, new ItemStack(Blocks.SAPLING, 1, rnd.nextInt(6)));
        }
    }

    private static void spawnEI(BreakEvent e, ItemStack is) {
        e.getWorld()
                .spawnEntity(new EntityItem(e.getWorld(), e.getPos().getX(), e.getPos().getY(), e.getPos().getZ(), is));
    }

    @SubscribeEvent
    public static void speed(BreakSpeed e) {
        if (e.getState().getBlock() == Blocks.STONE && e.getEntityPlayer().getHeldItemMainhand().isEmpty()
                && SBUConfig.fast_break && SBUConfig.speed_break_stone > 0.001D)
            e.setNewSpeed((float) SBUConfig.speed_break_stone);
    }
}