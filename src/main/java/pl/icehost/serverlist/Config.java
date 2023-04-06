package pl.icehost.serverlist;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.UUID;

public class Config {

    private final ServerList plugin;

    private final File file;

    public Config(ServerList plugin) {
        this.plugin = plugin;
        plugin.saveResource("config.yml", false);
        file=new File("plugins/ServerListApi/config.yml");
    }

    public void load() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configurationSection;
        if (config.isConfigurationSection("nagroda")) {
            configurationSection = config.getConfigurationSection("nagroda");
        }else{
            System.out.println("błąd");
            return;
        }
        configurationSection.get()
        String itemName = config.getString("item.name");
        Material itemMaterial = Material.getMaterial(config.getString("item.material"));

        ItemStack itemStack = new ItemStack(itemMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();

        // Set the display name and lore of the item
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(config.getStringList("item.lore"));

        // Set the enchantments of the item
        for (String enchantmentString : config.getStringList("item.enchantments")) {
            String[] enchantmentParts = enchantmentString.split(":");
            Enchantment enchantment = Enchantment.getByName(enchantmentParts[0]);
            int level = Integer.parseInt(enchantmentParts[1]);
            itemMeta.addEnchant(enchantment, level, true);
        }

        // Set the flags of the item
        for (String flagString : config.getStringList("item.flags")) {
            ItemFlag flag = ItemFlag.valueOf(flagString);
            itemMeta.addItemFlags(flag);
        }

        // Set the attributes of the item
        for (String attributeString : config.getStringList("item.attributes")) {
            String[] attributeParts = attributeString.split(":");
            String attributeName = attributeParts[0];
            double attributeValue = Double.parseDouble(attributeParts[1]);
            AttributeModifier.Operation attributeOperation = AttributeModifier.Operation.valueOf(attributeParts[2]);
            EquipmentSlot attributeSlot = EquipmentSlot.valueOf(attributeParts[3]);
            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), attributeName, attributeValue, attributeOperation, attributeSlot);
            itemMeta.addAttributeModifier(Attribute.valueOf(attributeName), attributeModifier);
        }

        itemStack.setItemMeta(itemMeta);

    }

}
