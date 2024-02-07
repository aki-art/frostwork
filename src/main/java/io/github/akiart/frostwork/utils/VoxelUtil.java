package io.github.akiart.frostwork.utils;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;

import java.util.HashSet;
import java.util.Set;

public class VoxelUtil {

    public static int getNeighbourCount(HashSet<? extends Vec3i> positions, Vec3i pos) {
        int count = 0;
        for (Direction dir : Direction.values())
            if (positions.contains(pos.relative(dir, 1))) count++;

        return count;
    }

    public static Set<Vec3i> getFilledCircle(float radius) {
        float d = radius * radius;
        Set<Vec3i> result = new HashSet<>();
        for(int x = Mth.floor(-radius); x < Mth.ceil(radius); x++) {
            for(int y = Mth.floor(-radius); y < Mth.ceil(radius); y++) {
                if(x * x + y * y <= d) {
                    result.add(new Vec3i(x, 0, y));
                }
            }
        }

        return result;
    }

    public static Set<Vec3i> getCircle(int radius, Vec3i center) {
        int i = 0;
        float num3 = 1f - radius;
        Set<Vec3i> result = new HashSet<>();
        while (radius >= i)
        {
            result.add(new Vec3i(radius + center.getX(), center.getY(), i + center.getZ()));
            result.add(new Vec3i(i + center.getX(), center.getY(), radius + center.getZ()));
            result.add(new Vec3i(-radius + center.getX(), center.getY(), i + center.getZ()));
            result.add(new Vec3i(-i + center.getX(), center.getY(), radius + center.getZ()));
            result.add(new Vec3i(-radius + center.getX(), center.getY(), -i + center.getZ()));
            result.add(new Vec3i(-i + center.getX(), center.getY(), -radius + center.getZ()));
            result.add(new Vec3i(radius + center.getX(), center.getY(), -i + center.getZ()));
            result.add(new Vec3i(i + center.getX(), center.getY(), -radius + center.getZ()));

            ++i;

            if (num3 < 0)
            {
                num3 += 2 * i + 1;
            } else
            {
                --radius;
                num3 += 2 * (i - radius) + 1;
            }
        }
        return result;
    }


    public static HashSet<Vec3i> projectDown(HashSet<Vec3i> positions, Vec3i from, Vec3i to) {
        HashSet<Vec3i> result = new HashSet<>();

        for(int x = from.getX(); x < to.getX(); x++) {
            for(int z = from.getZ(); z < to.getZ(); z++) {
                projectColumn(positions, result, x, z, from.getY(), to.getY());
            }
        }

        return result;
    }

    private static void projectColumn(HashSet<Vec3i> positions, HashSet<Vec3i> result, int x, int z, int from, int to) {
        for(int y = from; y < to; y++) {
            Vec3i pos = new Vec3i(x, y, z);
            if(positions.contains(pos)) {
                result.add(new Vec3i(x, 0, z));
                return;
            }
        }
    }
}
