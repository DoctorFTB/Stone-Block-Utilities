package ftblag.stoneblockutilities.gson;

import java.util.List;

public class SBUGson {

    public static class Cfg {
        public boolean active_render = true;
        public List<HandDrops> handDrops;
        public List<CrookDrops> crookDrops;
    }

    public static class HandDrops {
        public String block, drop;
        public int drop_with, drop_without, chance;
        public double speed;

        public HandDrops(String block, String drop, int drop_with, int drop_without, int chance, double speed) {
            this.block = block;
            this.drop = drop;
            this.drop_with = drop_with;
            this.drop_without = drop_without;
            this.chance = chance;
            this.speed = speed;
        }
    }

    public static class CrookDrops {
        public String block;
        public boolean randomDrop;
        public int randomDropAmount;
        public List<CrookDropGson> drops;

        public CrookDrops(String block, boolean randomDrop, int randomDropAmount, List<CrookDropGson> drops) {
            this.block = block;
            this.randomDrop = randomDrop;
            this.randomDropAmount = randomDropAmount;
            this.drops = drops;
        }
    }

    public static class CrookDropGson {
        public String drop;
        public int drop_size;

        public CrookDropGson(String drop, int drop_size) {
            this.drop = drop;
            this.drop_size = drop_size;
        }
    }
}
