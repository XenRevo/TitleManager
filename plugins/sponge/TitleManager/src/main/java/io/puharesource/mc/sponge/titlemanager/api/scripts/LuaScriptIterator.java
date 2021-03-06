package io.puharesource.mc.sponge.titlemanager.api.scripts;

import com.google.inject.Inject;
import io.puharesource.mc.sponge.titlemanager.utils.MiscellaneousUtils;
import io.puharesource.mc.sponge.titlemanager.TitleManager;
import io.puharesource.mc.sponge.titlemanager.api.animations.AnimationFrame;
import lombok.Data;
import lombok.val;
import org.luaj.vm2.LuaValue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Iterator;

@Data
public final class LuaScriptIterator implements Iterator<AnimationFrame> {
    @Inject private TitleManager plugin;

    private final LuaValue value;
    private final Text originalText;
    private final Player player;

    private int i;
    private boolean done;

    @Override
    public boolean hasNext() {
        return !done;
    }

    @Override
    public AnimationFrame next() {
        val args = value.get("tm_main").invoke(LuaValue.valueOf(plugin.replacePlaceholders(player, originalText).toPlain()), LuaValue.valueOf(i));
        done = args.arg(2).toboolean();
        i++;
        return new AnimationFrame(MiscellaneousUtils.format(args.arg(1).toString()), args.arg(3).toint(), args.arg(4).toint(), args.arg(5).toint());
    }

    public void reset() {
        done = false;
        i = 0;
    }
}
