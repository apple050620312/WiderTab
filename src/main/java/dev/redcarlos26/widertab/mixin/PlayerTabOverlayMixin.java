/*
 * Written by RedCarlos26
 *
 * This file is part of WiderTab.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package dev.redcarlos26.widertab.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.stream.Stream;

@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {
    @Redirect(
        method = "getPlayerInfos",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/stream/Stream;limit(J)Ljava/util/stream/Stream;"
        )
    )
    private <T> Stream<T> removePlayerLimit(Stream<T> players, long ignoredLimit) {
        return players;
    }

    @ModifyConstant(method = "extractRenderState", constant = @Constant(intValue = 20))
    private int rowsForScreenHeight(int vanillaRows) {
        int guiHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int rowsThatFit = (guiHeight - 120) / 9;
        return Math.max(vanillaRows, rowsThatFit);
    }
}
