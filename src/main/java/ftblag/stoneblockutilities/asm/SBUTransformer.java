package ftblag.stoneblockutilities.asm;

import java.util.ArrayList;
import java.util.Map;

import ftblag.stoneblockutilities.config.SBUConfig;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.google.common.collect.ImmutableMap;

import net.minecraft.init.Blocks;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(1001)
public final class SBUTransformer implements IClassTransformer, IFMLLoadingPlugin, Opcodes {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        return transformedName.equals("net.minecraft.world.WorldEntitySpawner") ? patch(basicClass) : basicClass;
    }

    public static byte[] patch(byte[] basicClass) {
        ClassNode cNode = new ClassNode();
        new ClassReader(basicClass).accept(cNode, 0);

        String method = getMethod("net.minecraft.world.WorldEntitySpawner.getRandomChunkPosition");
        for (MethodNode mNode : cNode.methods)
            if (mNode.name.equals(method)
                    && mNode.desc.equals("(Lnet/minecraft/world/World;II)Lnet/minecraft/util/math/BlockPos;")) {
                LogManager.getLogger("SBU").info("+++");
                mNode.instructions = getLocaleInsnList();
                break;
            }

        ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cNode.accept(cWriter);
        return cWriter.toByteArray();
    }

    private static InsnList getLocaleInsnList() {
        InsnList insn = new InsnList();
        insn.add(new VarInsnNode(ALOAD, 0));
        insn.add(new VarInsnNode(ILOAD, 1));
        insn.add(new VarInsnNode(ILOAD, 2));
        insn.add(new MethodInsnNode(INVOKESTATIC, "ftblag/stoneblockutilities/asm/SBUTransformer",
                "getRandomChunkPosition", "(Lnet/minecraft/world/World;II)Lnet/minecraft/util/math/BlockPos;", false));
        insn.add(new InsnNode(ARETURN));
        return insn;
    }

    public static BlockPos getRandomChunkPosition(World worldIn, int x, int z) {
        if (worldIn.provider.getDimension() != 0)
            return getRandomChunkPositionVanilla(worldIn, x, z);
        int i = x * 16 + worldIn.rand.nextInt(16);
        int j = z * 16 + worldIn.rand.nextInt(16);
        ArrayList<Integer> list = new ArrayList<>();
        for (int m = worldIn.getHeight() - 1; m > 0; m--)
            if (worldIn.getBlockState(new BlockPos(i, m, j)).getBlock() == Blocks.AIR
                    && worldIn.getBlockState(new BlockPos(i, m - 1, j)).getBlock() != Blocks.AIR)
                if (worldIn.rand.nextInt(99) + 1 <= SBUConfig.add_to_list_chance)
					list.add(m);
		if (worldIn.rand.nextInt(99) + 1 <= SBUConfig.clear_list_chance)
            list.clear();
        return list.isEmpty() ? new BlockPos(i, 0, j) : new BlockPos(i, list.get(worldIn.rand.nextInt(list.size())), j);
    }

    private static BlockPos getRandomChunkPositionVanilla(World worldIn, int x, int z) {
        Chunk chunk = worldIn.getChunkFromChunkCoords(x, z);
        int i = x * 16 + worldIn.rand.nextInt(16);
        int j = z * 16 + worldIn.rand.nextInt(16);
        int k = MathHelper.roundUp(chunk.getHeight(new BlockPos(i, 0, j)) + 1, 16);
        int l = worldIn.rand.nextInt(k > 0 ? k : chunk.getTopFilledSegment() + 16 - 1);
        return new BlockPos(i, l, j);
    }

    private static final ImmutableMap<String, String> methods = ImmutableMap.<String, String>builder()
            .put("net.minecraft.world.WorldEntitySpawner.getRandomChunkPosition", "func_180621_a").build();

    public static String getMethod(String method) {
        return isObfuscated ? methods.get(method) : method.substring(method.lastIndexOf('.') + 1);
    }

    private static boolean isObfuscated = false;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { getClass().getName() };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        isObfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}