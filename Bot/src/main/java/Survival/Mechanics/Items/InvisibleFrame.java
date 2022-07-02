package Survival.Mechanics.Items;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class InvisibleFrame implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void setFrameInvisibly(PlayerInteractAtEntityEvent e) {
		if(!e.getPlayer().isSneaking()) return;
		if(e.getRightClicked().getType() != EntityType.ITEM_FRAME) return;
		ItemFrame frame = (ItemFrame) e.getRightClicked();
		frame.setVisible(false);
	}
	
	
}
