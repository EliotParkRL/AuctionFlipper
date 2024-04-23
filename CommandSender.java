import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "my_mod", version = "1.0")
public class MyMod {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String message = event.getMessage().getUnformattedText();
        if (message.equals("!mycommand")) {
            executeCommand("/say Hello, world!");
        }
    }

    private void executeCommand(String command) {
        Minecraft.getMinecraft().player.sendChatMessage(command);
    }
}