package beyondspace.items.render;

import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public enum RenderEnum {;
   static final int[] $SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType = new int[ItemRenderType.values().length];

   static {
      try {
         $SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[ItemRenderType.EQUIPPED_FIRST_PERSON.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         $SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[ItemRenderType.EQUIPPED.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[ItemRenderType.INVENTORY.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[ItemRenderType.ENTITY.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }

   }
}

