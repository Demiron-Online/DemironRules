package sdravstvuite.demironrules.LifeChoosing;


import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sdravstvuite.demironrules.ConfigManager;

public class LifeConverstation extends StringPrompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext conversationContext) {
        return "Как тебя зовут?\nОбрати внимание, отправленное имя в чат, ты сможешь изменить только после смерти персонажа.";
    }

    @Override
    public @Nullable Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String s) {
        TextComponent message = Component.text().content("Отлично, " + s + ", такое имя запомнят.").color(TextColor.fromHexString("#0384fc")).build();
        ((Player) conversationContext.getForWhom()).sendMessage(message);
        ConfigManager.changeLife((Player) conversationContext.getForWhom(), "name", s);
        ConfigManager.changeLife((Player) conversationContext.getForWhom(), "count_lives", 3);
        return null;
    }
}