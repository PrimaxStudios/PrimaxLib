package net.primaxstudios.primaxcore.menus.item;

import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.menus.item.slot.SingleSlotItem;
import net.primaxstudios.primaxcore.utils.NavigationType;

@Getter @Setter
public abstract class NavigateItem extends SingleSlotItem implements ClickableItem {

    private final String pageKey;
    private final NavigationType navigationType;

    public NavigateItem(String pageKey, NavigationType navigationType) {
        this.pageKey = pageKey;
        this.navigationType = navigationType;
    }

    @Override
    public void onClick(CustomMenuClickEvent e) {
        MenuHolder holder = e.getHolder();

        int page = holder.getMeta().getInt(pageKey);
        int newPage = switch (navigationType) {
            case PREVIOUS_PAGE -> page - 1;
            case NEXT_PAGE -> page + 1;
        };
        holder.getMeta().setInt(pageKey, newPage);
    }
}
