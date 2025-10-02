package com.vanilla.util;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@FunctionalInterface
public interface MouseScrollEvent {
    Event<MouseScrollEvent> EVENT = EventFactory.createArrayBacked(
            MouseScrollEvent.class,
            listeners -> (dx, dy) -> {
                for (MouseScrollEvent l : listeners) {
                    if (l.onScroll(dx, dy)) {
                        return true;          // 任一监听者消费掉就停止
                    }
                }
                return false;
            });

    boolean onScroll(double dx, double dy);
}