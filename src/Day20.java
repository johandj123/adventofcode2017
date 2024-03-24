import lib.InputUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day20 {
    public static void main(String[] args) throws IOException {
        List<Particle> particles = InputUtil.readAsLines("input20.txt").stream()
                .map(Particle::new)
                .collect(Collectors.toList());
        int smallest = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < particles.size(); i++) {
            int value = particles.get(i).maxAccelMagnatude();
            if (value < smallest) {
                smallest = value;
                index = i;
            }
        }
        System.out.println(index);
    }

    static class Particle {
        int[] x = new int[3];
        int[] v = new int[3];
        int[] a = new int[3];

        // Direct formula for the position:
        // x=at^2+v0t+x0

        Particle(String s) {
            String[] sp = s.split("[^\\d-]+");
            for (int i = 0; i < 3; i++) {
                x[i] = Integer.parseInt(sp[i + 1]);
            }
            for (int i = 0; i < 3; i++) {
                v[i] = Integer.parseInt(sp[i + 4]);
            }
            for (int i = 0; i < 3; i++) {
                a[i] = Integer.parseInt(sp[i + 7]);
            }
        }

        int maxAccelMagnatude() {
            return Arrays.stream(a)
                    .map(Math::abs)
                    .max()
                    .orElseThrow();
        }
    }
}
