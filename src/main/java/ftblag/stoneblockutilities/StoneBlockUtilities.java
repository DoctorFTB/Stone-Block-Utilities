package ftblag.stoneblockutilities;

import ftblag.stoneblockutilities.config.SBUConfig;
import ftblag.stoneblockutilities.gui.ContainerWB;
import ftblag.stoneblockutilities.gui.GuiHandler;
import ftblag.stoneblockutilities.registry.SBURegistry;
import ftblag.stoneblockutilities.render.RenderWB;
import ftblag.stoneblockutilities.tileentity.StoneWorkbenchTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = StoneBlockUtilities.MODID, name = StoneBlockUtilities.MODNAME, version = StoneBlockUtilities.VERSION, dependencies = StoneBlockUtilities.DEPENDENCIES)
public class StoneBlockUtilities {

    public static final String MODID = "stoneblockutilities", MODNAME = "StoneBlockUtilities", VERSION = "@VERSION@",
            DEPENDENCIES = "required-after:forge@[14.23.4.2707,);required-after:exnihilocreatio@[0.1.5,);";

    @Mod.Instance(StoneBlockUtilities.MODID)
    public static StoneBlockUtilities INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new SBURegistry());
        NetworkRegistry.INSTANCE.registerGuiHandler(StoneBlockUtilities.INSTANCE, new GuiHandler());
        SBUConfig.setupConfig(new Configuration(e.getSuggestedConfigurationFile()), e.getModLog());
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && SBUConfig.active_render)
            client();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        OreDictionary.registerOre("workbench", new ItemStack(SBURegistry.table));
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("ContainerClass", ContainerWB.class.getName());
        tagCompound.setString("AlignToGrid", "left");
        FMLInterModComms.sendMessage("craftingtweaks", "RegisterProvider", tagCompound);
    }

    @SideOnly(Side.CLIENT)
    private void client() {
        ClientRegistry.bindTileEntitySpecialRenderer(StoneWorkbenchTileEntity.class, new RenderWB());
    }
}