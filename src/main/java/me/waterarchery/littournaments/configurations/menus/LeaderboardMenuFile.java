package me.waterarchery.littournaments.configurations.menus;

import com.chickennw.utils.configurations.menu.triumph.MenuFiller;
import com.chickennw.utils.configurations.menu.triumph.MenuItemStack;
import com.chickennw.utils.configurations.menu.triumph.SymboledMenuItem;
import com.chickennw.utils.libs.config.configs.OkaeriConfig;
import com.chickennw.utils.libs.config.configs.annotation.NameModifier;
import com.chickennw.utils.libs.config.configs.annotation.NameStrategy;
import com.chickennw.utils.libs.config.configs.annotation.Names;
import com.chickennw.utils.models.config.menu.triumph.TriumphMenuConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class LeaderboardMenuFile extends OkaeriConfig implements TriumphMenuConfiguration {

    private String title = "ʟᴇᴀᴅᴇʀʙᴏᴀʀᴅ";

    private String openSound = "BLOCK_NOTE_BLOCK_PLING";

    private List<String> pattern = List.of(
        "---------",
        "--iiiii--",
        "--iiiii--",
        "--n---p--"
    );

    private MenuFiller filler = new MenuFiller(true,
        new MenuItemStack("<gray>", "GRAY_STAINED_GLASS_PANE", -1, List.of()));

    private SymboledMenuItem templateItem = new SymboledMenuItem(
        "%player-name%",
        "PLAYER",
        -1,
        List.of(
            "",
            "<#47D4FF><bold>ᴘʟᴀʏᴇʀ sᴛᴀᴛs",
            "<#47D4FF>| <#CCFFEE>ᴘʟᴀʏᴇʀ's ɴᴀᴍᴇ: <#47D4FF>%player-name%",
            "<#47D4FF>| <#CCFFEE>sᴄᴏʀᴇ: <#47D4FF>%player-score%",
            "<#47D4FF>| <#CCFFEE>ᴘᴏsɪᴛɪᴏɴ: <#47D4FF>%player-position%"
        ),
        "template",
        'i'
    );

    private Map<String, SymboledMenuItem> items = Map.ofEntries(
        Map.entry("previous-page", new SymboledMenuItem(
            "<red><#47D4FF>%tournament% ʟᴇᴀᴅᴇʀʙᴏᴀʀᴅ",
            "ARROW",
            -1,
            List.of(
                "",
                "<#CCFFEE>ᴄʟɪᴄᴋ ʜᴇʀᴇ ᴛᴏ ɴᴀᴠɪɢᴀᴛᴇ",
                "<#47D4FF>ᴘʀᴇᴠɪᴏᴜs ᴘᴀɢᴇ."
            ),
            "previous-page",
            'p'
        )),
        Map.entry("next-page", new SymboledMenuItem(
            "<red>ɴᴇxᴛ ᴘᴀɢᴇ",
            "ARROW",
            -1,
            List.of(
                "",
                "<#CCFFEE>ᴄʟɪᴄᴋ ʜᴇʀᴇ ᴛᴏ ɴᴀᴠɪɢᴀᴛᴇ",
                "<#47D4FF>ɴᴇxᴛ ᴘᴀɢᴇ."
            ),
            "next-page",
            'n'
        ))
    );
}
