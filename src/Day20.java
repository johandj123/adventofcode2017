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
        first(particles);
        second(particles);
    }

    private static void first(List<Particle> particles) {
        long smallest = Long.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < particles.size(); i++) {
            long value = particles.get(i).maxAccelMagnatude();
            if (value < smallest) {
                smallest = value;
                index = i;
            }
        }
        System.out.println(index);
    }

    private static void second(List<Particle> particles) {
        for (int count = 0; count < 10000; count++) {
            particles.forEach(Particle::step);

            boolean change;
            do {
                change = false;
                for (int i = 0; i < particles.size(); i++) {
                    Particle a = particles.get(i);
                    for (int j = particles.size() - 1; j > i; j--) {
                        Particle b = particles.get(j);
                        if (a.collision(b)) {
                            particles.remove(j);
                            change = true;
                        }
                    }
                    if (change) {
                        particles.remove(i);
                        break;
                    }
                }
            } while (change);
        }
        System.out.println(particles.size());
    }

    static class Particle {
        long[] x = new long[3];
        long[] v = new long[3];
        long[] a = new long[3];

        // Direct formula for the position:
        // x=at^2+v0t+x0

        Particle(String s) {
            String[] sp = s.split("[^\\d-]+");
            for (int i = 0; i < 3; i++) {
                x[i] = Long.parseLong(sp[i + 1]);
            }
            for (int i = 0; i < 3; i++) {
                v[i] = Long.parseLong(sp[i + 4]);
            }
            for (int i = 0; i < 3; i++) {
                a[i] = Long.parseLong(sp[i + 7]);
            }
        }

        long maxAccelMagnatude() {
            return Arrays.stream(a)
                    .map(Math::abs)
                    .max()
                    .orElseThrow();
        }

        void step() {
            for (int i = 0; i < 3; i++) {
                v[i] += a[i];
            }
            for (int i = 0; i < 3; i++) {
                x[i] += v[i];
            }
        }

        boolean collision(Particle o) {
            return Arrays.equals(x, o.x);
        }
    }
}
