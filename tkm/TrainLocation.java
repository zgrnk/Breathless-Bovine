    public class TrainLocation {
        Block block;
        double distance;
        boolean direction;
        boolean failed;

        public TrainLocation(Block b, boolean dir, double dist) {
            block = b;
            distance = dist;
            direction = dir;
            failed = false;
        }
        
        public int getMapX() {
            double dist_fact = distance / block.length;
            return (int)((block.mapX2 - block.mapX1)*dist_fact + block.mapX1);
        }

        public int getMapY() {
            double dist_fact = distance / block.length;
            return (int)((block.mapY2 - block.mapY1)*dist_fact + block.mapY1);
        }
    }
