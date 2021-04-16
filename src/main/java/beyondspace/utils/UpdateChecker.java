package beyondspace.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import beyondspace.ModInfo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;

//Code is taken from com.brandon3055.draconicevolution.common.utills.UpdateChecker
public class UpdateChecker {
	private final UpdateCheckThread thread;
    private int delay = 300;
    private boolean playerNotified = false;

    public UpdateChecker() {
        thread = new UpdateCheckThread();
        thread.start();
    }


    @SubscribeEvent
    public void tickStart(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (delay > 0) {
            delay--;
            return;
        }

        if (!playerNotified && thread.isComplete()) {
            playerNotified = true;
            FMLCommonHandler.instance().bus().unregister(this);

            if (!thread.getVersion().equals(ModInfo.VERSION)) {
                event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BLUE + "[Beyond Space]" + EnumChatFormatting.DARK_BLUE + StatCollector.translateToLocal("info.newVersion.txt")));
                event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Beyond Space v" + thread.getVersion()));
                event.player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("info.note.txt")));
            }
        }

    }


    public class UpdateCheckThread extends Thread {
        private String version = null;
        private String note = null;
        private boolean complete = false;

        @Override
        public void run() {
            try {
                URL versionURL = new URL("https://raw.githubusercontent.com/BlesseNtumble/BeyondSpace/dev_1.7.10/UpdateChecker");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((versionURL).openStream()));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(":")) {
                        String value = line.substring(line.indexOf(":") + 1);
                        if (line.contains("Version")) version = value;
                        else if (line.contains("Note")) note = value;
                    }
                }

                if (version != null) complete = true;
                else {
                    note = "[Invalid Response]";
              }

            }
            catch (Exception e) {
                note = e.getClass().toString();
                e.printStackTrace();
            }
        }

        public String getVersion() {
            return version;
        }

        public boolean isComplete() {
            return complete;
        }

    }
}
