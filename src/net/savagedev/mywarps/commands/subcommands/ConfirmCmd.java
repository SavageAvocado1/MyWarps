package net.savagedev.mywarps.commands.subcommands;

import net.savagedev.mywarps.MyWarps;
import net.savagedev.mywarps.commands.state.CommandState;
import net.savagedev.mywarps.commands.state.ConfirmationManager;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class ConfirmCmd extends SubCommand {
    public ConfirmCmd(MyWarps plugin, Permission permission) {
        super(plugin, permission);
    }

    @Override
    public void execute(Player user, String... args) {
        if (!this.validatePermissions(user)) return;

        ConfirmationManager confirmationManager = this.getPlugin().getUserManager().getConfirmationManager(user);

        if (confirmationManager.getState() != CommandState.AWAITING_CONFIRMATION) {
            this.getPlugin().message(user, this.getPlugin().getConfig().getString("messages.not-awaiting-confirmation"));
            return;
        }

        if (!args[0].equalsIgnoreCase("confirm")) {
            confirmationManager.getCommand().execute(user, confirmationManager.getArguments());
            return;
        }

        confirmationManager.getCommand().onConfirm(user, confirmationManager.getArguments());
        confirmationManager.reset();
    }
}
