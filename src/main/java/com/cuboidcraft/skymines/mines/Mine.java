package com.cuboidcraft.skymines.mines;

import com.cuboidcraft.skymines.util.Box;
import com.cuboidcraft.skymines.util.MaterialBag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Mine {
    //material to be used for the border
    private static final Material borderMaterial = Material.BEDROCK;
    //data used to construct the mine
    private MineData mineData;
    //container that random materials can be pulled from
    MaterialBag materialBag;

    //disable default constructor
    @SuppressWarnings("unused")
    private Mine() {}

    public Mine(@NotNull MineData data){
        mineData = data;
        materialBag = new MaterialBag(data.getMaterials());
        //reset so that the mine will spawn in the world as soon as it's created
        reset();
    }
    //teleport players to the top/center of the mine.
    public void teleportPlayersOut(){
        //loop through each player, and if they are inside the mine,
        //teleport them to be on top of the mine in the middle
        for(Player p : Bukkit.getOnlinePlayers())
            if(mineData.getBox().isInside(p.getLocation()))
                p.teleport(mineData.getBox().getTopMiddle());
    }

    public void reset(){
        //first teleport players out to make sure nobody is in there
        teleportPlayersOut();
        //get bounding box that we will use to make the mine
        Box b = mineData.getBox();
        //thickness of the walls
        int thickness = mineData.getBorderThickness();

        //loop through each block location in the 3D box for this mine
        for (Location curr : b) {
            //get the block at the current location
            Block block = b.world.getBlockAt(curr);

            //if the mine should have a bedrock border, then
            //generate it (with the proper thickness)
            if (b.isWithinNblocksOfEdge(curr, thickness, false)) {
                block.setType(borderMaterial);
                continue;
            }

            //set the block to be a random material from the materialBag
            if(block.getType() == Material.AIR)
                block.setType(materialBag.nextMaterial());
        }
    }

    public MineData getMineData() {
        return mineData;
    }

    public void setMineData(MineData data) {
        mineData = data;
    }
}
