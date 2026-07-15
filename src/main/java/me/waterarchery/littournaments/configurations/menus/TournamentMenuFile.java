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
public class TournamentMenuFile extends OkaeriConfig implements TriumphMenuConfiguration {

    private String title = "<#47D4FF>ᴛᴏᴜʀɴᴀᴍᴇɴᴛs";

    private String openSound = "BLOCK_NOTE_BLOCK_PLING";

    private List<String> pattern = List.of(
        "---------",
        "--b-p-c--",
        "---------",
        "--m-i-k--",
        "---------"
    );

    private MenuFiller filler = new MenuFiller(true,
        new MenuItemStack("<dark_gray>", "GRAY_STAINED_GLASS_PANE", -1, List.of()));

    private Map<String, SymboledMenuItem> items = Map.ofEntries(
        Map.entry("block_break", tournamentItem("ʙʟᴏᴄᴋ ʙʀᴇᴀᴋ ᴛᴏᴜʀɴᴀᴍᴇɴᴛ", "COBBLESTONE", 'b', "ʙʀᴇᴀᴋ ʙʟᴏᴄᴋs", "ʙʀᴏᴋᴇɴ ʙʟᴏᴄᴋs")),
        Map.entry("block_place", tournamentItem("ʙʟᴏᴄᴋ ᴘʟᴀᴄᴇ ᴛᴏᴜʀɴᴀᴍᴇɴᴛ", "OAK_LOG", 'p', "ᴘʟᴀᴄᴇ ʙʟᴏᴄᴋs", "ᴘʟᴀᴄᴇᴅ ʙʟᴏᴄᴋs")),
        Map.entry("item_craft", tournamentItem("ᴄʀᴀғᴛ ɪᴛᴇᴍ ᴛᴏᴜʀɴᴀᴍᴇɴᴛ", "CRAFTING_TABLE", 'c', "ᴄʀᴀғᴛ ɪᴛᴇᴍs", "ᴄʀᴀғᴛᴇᴅ ɪᴛᴇᴍs")),
        Map.entry("mob_kill", tournamentItem("ᴍᴏʙ ᴋɪʟʟ ᴛᴏᴜʀɴᴀᴍᴇɴᴛ", "DIAMOND_SWORD", 'm', "ᴋɪʟʟ ᴍᴏʙs", "ᴋɪʟʟᴇᴅ ᴍᴏʙs")),
        Map.entry("item_break", tournamentItem("ɪᴛᴇᴍ ʙʀᴇᴀᴋ ᴛᴏᴜʀɴᴀᴍᴇɴᴛ", "ANVIL", 'i', "ʙʀᴇᴀᴋ ʏᴏᴜʀ ɪᴛᴇᴍs", "ʙʀᴏᴋᴇɴ ɪᴛᴇᴍs")),
        Map.entry("player_kill", tournamentItem("ᴘʟᴀʏᴇʀ ᴋɪʟʟ ᴛᴏᴜʀɴᴀᴍᴇɴᴛ", "SHIELD", 'k', "ᴋɪʟʟ ᴘʟᴀʏᴇʀs", "ᴋɪʟʟᴇᴅ ᴘʟᴀʏᴇʀs"))
    );

    private static SymboledMenuItem tournamentItem(String name, String material, char symbol, String desc, String statLabel) {
        return new SymboledMenuItem(
            "<#47D4FF>" + name,
            material,
            -1,
            List.of(
                "",
                "<#CCFFEE>ʏᴏᴜ ᴄᴀɴ <#47D4FF>" + desc + " <#CCFFEE>ᴛᴏ ʙᴇᴄᴏᴍᴇ",
                "<#CCFFEE>ᴛʜᴇ ᴄʜᴀᴍᴘɪᴏɴ ᴏғ <#47D4FF>ᴛʜɪs ᴛᴏᴜʀɴᴀᴍᴇɴᴛ!",
                "",
                "<#47D4FF><bold>ʏᴏᴜʀ sᴛᴀᴛs",
                "<#47D4FF>| <#CCFFEE>" + statLabel + ": <#47D4FF>%stat%",
                "<#47D4FF>| <#CCFFEE>ʏᴏᴜʀ ᴘᴏsɪᴛɪᴏɴ: <#47D4FF>%position%",
                "",
                "<#47D4FF><bold>ʟᴇᴀᴅᴇʀʙᴏᴀʀᴅ",
                "<#47D4FF>| <#ff001e>🔥 #1 <white>%leader_name_1% <dark_gray>(<#ff001e>%leader_score_formatted_1%<dark_gray>)",
                "<#47D4FF>| <red>🔥 #2 <white>%leader_name_2% <dark_gray>(<red>%leader_score_formatted_2%<dark_gray>)",
                "<#47D4FF>| <yellow>🔥 #3 <white>%leader_name_3% <dark_gray>(<yellow>%leader_score_formatted_3%<dark_gray>)",
                "<#47D4FF>| <#ffb700>⭐ #4 <white>%leader_name_4% <dark_gray>(<#ffb700>%leader_score_formatted_4%<dark_gray>)",
                "<#47D4FF>| <#47D4FF>⭐ #5 <white>%leader_name_5% <dark_gray>(<#47D4FF>%leader_score_formatted_5%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #6 <white>%leader_name_6% <dark_gray>(<dark_gray>%leader_score_formatted_6%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #7 <white>%leader_name_7% <dark_gray>(<dark_gray>%leader_score_formatted_7%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #8 <white>%leader_name_8% <dark_gray>(<dark_gray>%leader_score_formatted_8%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #9 <white>%leader_name_9% <dark_gray>(<dark_gray>%leader_score_formatted_9%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #10 <white>%leader_name_10% <dark_gray>(<dark_gray>%leader_score_formatted_10%<dark_gray>)",
                "<#47D4FF>|",
                "<#47D4FF>| <#CCFFEE>ʟᴇᴀᴅᴇʀʙᴏᴀʀᴅ ʀᴇғʀᴇsʜᴇs",
                "<#47D4FF>| <#CCFFEE>ɪᴛsᴇʟғ ᴇᴠᴇʀʏ 1 ᴍɪɴᴜᴛᴇ",
                "",
                "<#47D4FF><bold>ʀᴇᴍᴀɪɴɪɴɢ ᴛɪᴍᴇ",
                "<#47D4FF>| <#CCFFEE>ᴛʜᴇʀᴇ ɪs <red>%remaining_time%",
                "<#47D4FF>| <#CCFFEE>ᴛᴏ ᴛʜɪs ᴛᴏᴜʀɴᴀᴍᴇɴᴛ ғɪɴɪsʜ.",
                "<#47D4FF>",
                "<#47D4FF>| <green>ʟᴇғᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴊᴏɪɴ",
                "<#47D4FF>| <green>ʀɪɡʜᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴠɪᴇᴡ ʟᴇᴀᴅᴇʀʙᴏᴀʀᴅ"
            ),
            "none",
            symbol
        );
    }

    @Override
    public SymboledMenuItem getTemplateItem() {
        return null;
    }
}
