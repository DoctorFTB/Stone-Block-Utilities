package ftblag.stoneblockutilities;

import ftblag.stoneblockutilities.config.SBUConfig;
import ftblag.stoneblockutilities.gui.GuiHandler;
import ftblag.stoneblockutilities.registry.SBURegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = StoneBlockUtilities.MODID, name = StoneBlockUtilities.MODNAME, version = StoneBlockUtilities.VERSION, dependencies = StoneBlockUtilities.DEPENDENCIES)
public class StoneBlockUtilities {

    public static final String MODID = "stoneblockutilities", MODNAME = "StoneBlockUtilities", VERSION = "@VERSION@",
            DEPENDENCIES = "required-after:forge@[14.23.1.2554,);required-after:exnihilocreatio@[0.1.5,);";

    @Mod.Instance(StoneBlockUtilities.MODID)
    public static StoneBlockUtilities INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new SBURegistry());
        NetworkRegistry.INSTANCE.registerGuiHandler(StoneBlockUtilities.INSTANCE, new GuiHandler());
        SBUConfig.setupConfig(new Configuration(e.getSuggestedConfigurationFile()), e.getModLog());
    }
}