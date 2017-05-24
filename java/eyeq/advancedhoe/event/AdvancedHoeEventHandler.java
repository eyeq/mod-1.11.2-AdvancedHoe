package eyeq.advancedhoe.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AdvancedHoeEventHandler {
    @SubscribeEvent
    public void onPlayerInteractBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        if(world.isRemote) {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        if(!player.isSneaking()) {
            return;
        }
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        if(!(item instanceof ItemHoe)) {
            return;
        }
        int level = (int)(getToolMaterial((ItemHoe) item).getEfficiencyOnProperMaterial() / 2);
        if(level <= 1) {
            return;
        }
        BlockPos pos = event.getPos();
        EnumHand hand = event.getHand();
        EnumFacing facing = event.getFace();
        Vec3d hitVec = event.getHitVec();

        EnumFacing front = EnumFacing.getFacingFromVector((float) (pos.getX() - player.posX), 0, (float) (pos.getZ() - player.posZ));
        EnumFacing right = front.rotateY();
        EnumFacing left = front.rotateYCCW();
        switch(level) {
        case 2:
            for(int i = 0; i < 3; i++) {
                if(i != 0) {
                    item.onItemUse(player, world, pos, hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                }
                pos = pos.offset(front);
            }
            break;
        case 3:
            for(int i = 0; i < 3; i++) {
                if(i != 0) {
                    item.onItemUse(player, world, pos, hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                }
                item.onItemUse(player, world, pos.offset(right), hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                item.onItemUse(player, world, pos.offset(left), hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                pos = pos.offset(front);
            }
            break;
        case 4:
            for(int i = 0; i < 6; i++) {
                if(i != 0) {
                    item.onItemUse(player, world, pos, hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                }
                item.onItemUse(player, world, pos.offset(right), hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                item.onItemUse(player, world, pos.offset(left), hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                pos = pos.offset(front);
            }
            break;
        default:
            if(level != 5) {
                player.setHeldItem(hand, new ItemStack(item));
            }
            for(int i = 0; i < 9; i++) {
                if(i != 0) {
                    item.onItemUse(player, world, pos, hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                }
                item.onItemUse(player, world, pos.offset(right), hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                item.onItemUse(player, world, pos.offset(left), hand, facing, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
                pos = pos.offset(front);
            }
            if(level != 5) {
                player.setHeldItem(hand, itemStack);
            }
        }
    }

    public static Item.ToolMaterial getToolMaterial(ItemHoe item) {
        return ObfuscationReflectionHelper.getPrivateValue(ItemHoe.class, item, "theToolMaterial", "field_77843_a");
    }
}
