package com.vanilla.mixin;

import com.vanilla.util.MouseScrollEvent;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseScrollMixin {
    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        boolean consumed = MouseScrollEvent.EVENT.invoker().onScroll(horizontal, vertical);
        if (consumed) ci.cancel();   // 返回 true 可阻止 MC 原逻辑（切栏）
    }
}