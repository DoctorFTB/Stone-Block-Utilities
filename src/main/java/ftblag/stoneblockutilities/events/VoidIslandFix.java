package ftblag.stoneblockutilities.events;

import com.bartz24.voidislandcontrol.VoidIslandControlSaveData;
import com.bartz24.voidislandcontrol.api.IslandManager;
import com.bartz24.voidislandcontrol.api.IslandPos;
import ftblag.stoneblockutilities.StoneBlockUtilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

/**
 * Void Island Control keeps its island list in data/VICData.dat. When that file is lost or older than the
 * player files (a crash while saving), VIC treats returning players as new: it pastes the spawn structure
 * again and hands out the next free island slot, which can be the player's own base.
 * Each player's VIC entry is mirrored into their own player data and put back before VIC looks at it.
 * Registered only when Void Island Control is loaded.
 */
public class VoidIslandFix {

    private static final Logger LOG = LogManager.getLogger(StoneBlockUtilities.MODID);
    private static final String TAG = StoneBlockUtilities.MODID + ":vic";

    /** Restore everyone who has played here, so a new player can't be handed an offline player's island. */
    public static void restoreAll(MinecraftServer server) {
        File dir = new File(server.getEntityWorld().getSaveHandler().getWorldDirectory(), "playerdata");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".dat"));
        if (files == null)
            return;
        for (File f : files) {
            try (InputStream in = Files.newInputStream(f.toPath())) {
                NBTTagCompound forgeData = CompressedStreamTools.readCompressed(in).getCompoundTag("ForgeData");
                restore(f.getName().substring(0, f.getName().length() - 4), forgeData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG));
            } catch (IOException | IllegalArgumentException ex) {
                LOG.error("Could not read {} while checking Void Island Control data", f.getName(), ex);
            }
        }
    }

    // Runs before VIC's own login handler (NORMAL priority).
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void beforeVIC(PlayerLoggedInEvent e) {
        if (!e.player.world.isRemote)
            restore(e.player.getGameProfile().getId().toString(), e.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG));
    }

    // Runs after VIC, so a new player's island is recorded straight away.
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void afterVIC(PlayerLoggedInEvent e) {
        remember(e.player);
    }

    // Islands can change during a session (/island create, join, leave).
    @SubscribeEvent
    public static void logout(PlayerLoggedOutEvent e) {
        remember(e.player);
    }

    private static void restore(String uuid, NBTTagCompound persisted) {
        if (!persisted.hasKey(TAG) || IslandManager.spawnedPlayers.contains(uuid))
            return;
        NBTTagCompound vic = persisted.getCompoundTag(TAG);
        // VIC expects the spawn island at index 0.
        if (IslandManager.CurrentIslandsList.isEmpty())
            IslandManager.CurrentIslandsList.add(new IslandPos(0, 0));
        if (vic.hasKey("island")) {
            IslandPos saved = new IslandPos(0, 0);
            saved.readFromNBT(vic.getCompoundTag("island"));
            IslandPos existing = IslandManager.getIslandAtPos(saved.getX(), saved.getY());
            if (existing == null)
                IslandManager.CurrentIslandsList.add(saved);
            else
                existing.addNewPlayer(UUID.fromString(uuid));
            LOG.warn("Void Island Control had lost player {}, restored their island at x {}, z {}", uuid, blocks(saved.getX()), blocks(saved.getY()));
        } else {
            LOG.warn("Void Island Control had lost player {}, restored them without an island", uuid);
        }
        // VIC keeps islands nobody is on any more, so their slot is never handed out and pasted over.
        NBTTagList left = vic.getTagList("left", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < left.tagCount(); i++) {
            NBTTagCompound pos = left.getCompoundTagAt(i);
            int x = pos.getInteger("posX"), z = pos.getInteger("posY");
            if (IslandManager.getIslandAtPos(x, z) == null) {
                IslandManager.CurrentIslandsList.add(new IslandPos(pos.getString("type"), x, z));
                LOG.warn("Restored the island player {} left at x {}, z {} as taken", uuid, blocks(x), blocks(z));
            }
        }
        IslandManager.spawnedPlayers.add(uuid);
        VoidIslandControlSaveData.setDirty(0);
    }

    // ponytail: islands left and replaced within one session between two saves are missed; hook VIC's leave command if that matters.
    private static void remember(EntityPlayer player) {
        UUID id = player.getGameProfile().getId();
        if (player.world.isRemote || !IslandManager.spawnedPlayers.contains(id.toString()))
            return;
        NBTTagCompound persisted = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        NBTTagCompound before = persisted.getCompoundTag(TAG);
        NBTTagList left = before.getTagList("left", Constants.NBT.TAG_COMPOUND);
        IslandPos island = IslandManager.getPlayerIsland(id);
        if (before.hasKey("island")) {
            NBTTagCompound old = before.getCompoundTag("island");
            int x = old.getInteger("posX"), z = old.getInteger("posY");
            if ((island == null || island.getX() != x || island.getY() != z) && !contains(left, x, z)) {
                NBTTagCompound gone = new NBTTagCompound();
                gone.setInteger("posX", x);
                gone.setInteger("posY", z);
                gone.setString("type", old.getString("type"));
                left.appendTag(gone);
            }
        }
        NBTTagCompound vic = new NBTTagCompound();
        if (island != null) {
            NBTTagCompound pos = new NBTTagCompound();
            island.writeToNBT(pos);
            vic.setTag("island", pos);
        }
        vic.setTag("left", left);
        persisted.setTag(TAG, vic);
        player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);
    }

    private static boolean contains(NBTTagList list, int x, int z) {
        for (int i = 0; i < list.tagCount(); i++)
            if (list.getCompoundTagAt(i).getInteger("posX") == x && list.getCompoundTagAt(i).getInteger("posY") == z)
                return true;
        return false;
    }

    private static int blocks(int grid) {
        return grid * IslandManager.initialIslandDistance;
    }
}
