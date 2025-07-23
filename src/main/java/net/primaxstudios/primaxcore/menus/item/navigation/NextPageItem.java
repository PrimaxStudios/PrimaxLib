package net.primaxstudios.primaxcore.menus.item.navigation;

import net.primaxstudios.primaxcore.menus.item.NavigateItem;
import net.primaxstudios.primaxcore.utils.NavigationType;

public class NextPageItem extends NavigateItem {

    public NextPageItem(String pageKey) {
        super(pageKey, NavigationType.NEXT_PAGE);
    }

    @Override
    public String getId() {
        return "next_page";
    }
}
