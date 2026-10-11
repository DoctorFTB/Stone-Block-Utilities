package ftblag.stoneblockutilities.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Ex Compressum's auto sieves eat the food they are fed (by hand or by an Extra Utilities 2 Mechanical User)
 * as Forge's shared fake player, which has no network connection. When the food applies a potion effect
 * (rotten flesh, menril berries) EntityPlayerMP.onNewPotionEffect sends a packet over that missing connection
 * and crashes. Refuse potion effects for connection-less fake players; the sieve still gets its speed boost.
 * Registered only when Ex Compressum is loaded.
 */
public class FakePlayerPotionFix {

    @SubscribeEvent
    public static void fakePlayerPotion(PotionApplicableEvent e) {
        if (e.getEntityLiving() instanceof FakePlayer && ((EntityPlayerMP) e.getEntityLiving()).connection == null)
            e.setResult(Event.Result.DENY);
    }
}
