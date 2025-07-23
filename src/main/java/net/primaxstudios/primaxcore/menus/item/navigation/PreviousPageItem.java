package net.primaxstudios.primaxcore.menus.item.navigation;

import net.primaxstudios.primaxcore.menus.item.NavigateItem;
import net.primaxstudios.primaxcore.utils.NavigationType;

public class PreviousPageItem extends NavigateItem {

    public PreviousPageItem(String pageKey) {
        super(pageKey, NavigationType.PREVIOUS_PAGE);
    }

    @Override
    public String getId() {
        return "previous_page";
    }
}
