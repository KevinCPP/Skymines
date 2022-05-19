package com.cuboidcraft.skymines;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {


    MineManager mgr = new MineManager(Utility.getInst().getPlugin());

    //test function
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(label.equalsIgnoreCase("boxtest")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                Box b = new Box(p.getLocation().add(10, 0, 10), p.getLocation().subtract(10, 20, 10));
                MineData data = new MineData(p.getUniqueId(), b, "stone,cobblestone");
                mgr.registerMine(data);
                mgr.saveMines(); //debug
            }
        }

        if(label.equalsIgnoreCase("resetmine")){
            if(sender instanceof Player) {
                Player p = (Player)sender;
                if(!mgr.resetMine(p.getUniqueId()))
                    System.out.println("There was an issue resetting mine for player: " + p.getName());
            }
        }

        return true;
    }

    private boolean boxTest(){
        return true;
    }
}
