package ftblag.stoneblockutilities.gson;

import java.util.List;

public class SBUGson {

    public static class Cfg {
        public boolean active_render = true;
        public List<Drops> drops;
    }

    public static class Drops {
        public String block, drop_with, drop_without;
        public int chance;
        public double speed;

        public Drops(String block, String drop_with, String drop_without, int chance, double speed) {
            this.block = block;
            this.drop_with = drop_with;
            this.drop_without = drop_without;
            this.chance = chance;
            this.speed = speed;
        }
    }
}
