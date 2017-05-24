package eyeq.advancedhoe;

import eyeq.advancedhoe.event.AdvancedHoeEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static eyeq.advancedhoe.AdvancedHoe.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
public class AdvancedHoe {
    public static final String MOD_ID = "eyeq_advancedhoe";

    @Mod.Instance(MOD_ID)
    public static AdvancedHoe instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new AdvancedHoeEventHandler());
    }
}
