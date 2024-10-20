package org.xianbei235.playServer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayServer extends JavaPlugin {

    @Override
    public void onEnable() {
        // 注册 Plugin Messaging Channel
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getLogger().info("PlayServer插件已启动 作者:xianbei235");
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayServer插件已卸载，感谢您的使用~");
    }

    // 处理 /play 指令
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("请不要在后台执行这个指令");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("/play <服务器名>");
            return true;
        }

        String serverName = args[0];
        connectToServer(player, serverName);
        return true;
    }

    private void connectToServer(Player player, String serverName) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(serverName);
        } catch (IOException e) {
            player.sendMessage("发送连接请求时出现错误!");
            e.printStackTrace();
        }

        player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
        player.sendMessage("§a§l正在前往服务器: §e§l" + serverName);
    }
}
