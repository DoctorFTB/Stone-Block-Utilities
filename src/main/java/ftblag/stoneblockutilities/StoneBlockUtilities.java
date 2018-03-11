package ftblag.stoneblockutilities;

import ftblag.stoneblockutilities.config.SBUConfig;
import ftblag.stoneblockutilities.gui.GuiHandler;
import ftblag.stoneblockutilities.render.RenderWB;
import ftblag.stoneblockutilities.tileentity.StoneWorkbenchTileEntity;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = StoneBlockUtilities.MODID, name = "StoneBlockUtilities", version = "@VERSION@", dependencies = StoneBlockUtilities.DEPENDENCIES)
public class StoneBlockUtilities {

    public static final String MODID = "stoneblockutilities",
            DEPENDENCIES = "required-after:forge@[14.23.2.2611,);required-after:exnihilocreatio@[0.1.5,);";

    @Mod.Instance(StoneBlockUtilities.MODID)
    public static StoneBlockUtilities INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(StoneBlockUtilities.INSTANCE, new GuiHandler());
        SBUConfig.setupConfig(new Configuration(e.getSuggestedConfigurationFile()), e.getModLog());
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && SBUConfig.active_render)
            client();
    }

    @SideOnly(Side.CLIENT)
    private void client() {
        ClientRegistry.bindTileEntitySpecialRenderer(StoneWorkbenchTileEntity.class, new RenderWB());
    }
}