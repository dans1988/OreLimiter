package pl.dans.plugins.ores;

import java.util.Objects;


public class OreLocation {
     private final int x;
     private final int y;
     private final int z;
     
     private final int hashCode;

    public OreLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        hashCode = Objects.hash(x,y,z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj != null && obj instanceof OreLocation) {
            OreLocation other = (OreLocation)obj;
            return Objects.equals(this.x, other.x)
                    && Objects.equals(this.y, other.y)
                    && Objects.equals(this.z, other.z);
                    
        } else {
            return false;
        }
    }
    
    
     
     
}
