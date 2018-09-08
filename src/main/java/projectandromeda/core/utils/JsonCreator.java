package projectandromeda.core.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import projectandromeda.ProjectAndromeda;

public class JsonCreator {

	private static String modid = ProjectAndromeda.MODID;
	public static String path = "K://MCP/codding/1.12.2/src/main/resources/assets/" + modid + "/";

	public static void addBlockJsonFiles(Block block, String addPath){
		try{
			File blockStates = new File(path + "/blockstates/", block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			File modelBlock = new File(path + "/models/block/", block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			File modelItemBlock = new File(path + "/models/item/", block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			
			if (!blockStates.exists())
				if (blockStates.createNewFile()) {
					blockstateJson(block, blockStates);
				} else if (blockStates.exists()) {
					blockstateJson(block, blockStates);
				}
			
			if (!modelBlock.exists())
				if (modelBlock.createNewFile()) {
					modelBlockJson(block, modelBlock, addPath);
				} else if (modelBlock.exists()) {
					modelBlockJson(block, modelBlock, addPath);
				}
			
			if (!modelItemBlock.exists())
				if (modelItemBlock.createNewFile()) {
					modelItemBlockJson(block, modelItemBlock, addPath);
				} else if (modelItemBlock.exists()) {
					modelItemBlockJson(block, modelItemBlock, addPath);
				}
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	
	private static void blockstateJson(Block block, File file){
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		  "{"
		+ "\n \"variants\": {"
		+     "\n \"normal\": { "
		+         "\"model\" : "
		+          "\"" + modid + ":" + block.getUnlocalizedName().toLowerCase().substring(5) + "\""
		+         "}"
		+     "}"
		+ "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
		
	}
	
	private static void modelBlockJson(Block block, File file, String addPath){
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		"{"
		+ " \"parent\": \"block/cube_all\", "
		+     " \"textures\": { "
		+         "\"all\" : "
		+          "\"" + modid + ":blocks/" + addPath + block.getUnlocalizedName().toLowerCase().substring(5) + "\""
		+       "}"
		+   "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
		
	}
	
	private static void modelItemBlockJson(Block block, File file, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{\n" 
			+ "	\"parent\": \"" + modid + ":block/" + block.getUnlocalizedName().toLowerCase().substring(5) + "\" \n"
			+ "}"
			);
			writer.close();
		}catch (IOException ex) {
			System.out.println(ex);
		}
	}
	
	/*
	private static void modelItemBlockJson(Block block, File file, String addPath){
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		"{"
		+ " \"parent\": \"block/cube_all\", "
		+     " \"textures\": { "
		+         "\"all\" : "
		+          "\"" + modid + ":blocks/" + addPath + block.getUnlocalizedName().toLowerCase().substring(5) + "\""
		+       "}"
		+   "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	*/
	// ***********ITEMS************
	public static void addItemJsonFiles(Item item) {
		addItemJsonFiles(item, "", item.getUnlocalizedName().toLowerCase().substring(5));
	}
	
	public static void addItemJsonFiles(Item item, String folder)
	{
		addItemJsonFiles(item, folder, item.getUnlocalizedName().toLowerCase().substring(5));
	}
	
	public static void addItemJsonFiles(Item item, String folder, String name) {
		try {
			File pathFolder = new File(path + "/models/item/" + folder);
			if(!pathFolder.exists()) pathFolder.mkdirs();
			
			File modelItemFile = new File(pathFolder, name + ".json");
		
			if(!modelItemFile.exists())
				if (modelItemFile.createNewFile()) {
					modelItemJson(item, modelItemFile, folder);
				} else if (modelItemFile.exists()) {
					modelItemJson(item, modelItemFile, folder);
				}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void modelItemJson(Item item, File file, String folder) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{"
			+ " \"parent\": \"item/generated\", "
			+     " \"textures\": { "
			+         "\"layer0\" : "
			+          "\"" + modid + ":items/" + folder + item.getUnlocalizedName().toLowerCase().substring(5) + "\""
			+       "}"
			+   "}"
			);					
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	// ***********BLOCKS META************
	public static void addBlockMetadataJsonFiles(Block block, String[] variants, String property, String addPath) {
		try {
			File blockstateItemBlockMeta = new File(path + "/blockstates/",
					block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			for (int i = 0; i < variants.length; i++) {
				File modelBlockMeta = new File(path + "/models/block/",
						variants[i] + ".json");
				File modelItemBlockMeta = new File(path + "/models/item/",
						variants[i] + ".json");

				if(!modelBlockMeta.exists())
					if (modelBlockMeta.createNewFile()) {
						modelBlockMetaJson(block, variants, modelBlockMeta, i, addPath);
					} else if (modelBlockMeta.exists()) {
						modelBlockMetaJson(block, variants, modelBlockMeta, i, addPath);
					}
				if(!modelItemBlockMeta.exists())
					if (modelItemBlockMeta.createNewFile()) {
						modelItemBlockMetaJson(block, variants, modelItemBlockMeta, i, addPath);
					} else if (modelItemBlockMeta.exists()) {
						modelItemBlockMetaJson(block, variants, modelItemBlockMeta, i, addPath);
					}
			}
			if(!blockstateItemBlockMeta.exists())
				if (blockstateItemBlockMeta.createNewFile()) {
					blockstateItemBlockMetaJson(block, blockstateItemBlockMeta, variants, property);
				} else if (blockstateItemBlockMeta.exists()) {
					blockstateItemBlockMetaJson(block, blockstateItemBlockMeta, variants, property);
				}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void blockstateItemBlockMetaJson(Block block, File file, String[] variants, String property) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("{ \n	\"variants\": { \n");
			
			for (int i = 0; i < variants.length; i++) {
				String string = "\"" + property + "="  + variants[i] + "\"" + ": { \"model\": " + "\"" + modid
						+ ":" + variants[i] + "\"}";
				if (variants[i] != variants[0]) {
					writer.write(", \n" + "		" + string);
				} else {
					writer.write("		" + string);
				}
			}
			writer.write("\n	} \n}");
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void modelBlockMetaJson(Block block, String[] variants, File file, int i, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{ \n"
			+ "	\"parent\": \"block/cube_all\", \n"
			+ " 	\"textures\": { "
			+         "\"all\" : "
			+          "\"" + modid + ":blocks/" + addPath + variants[i] + "\""
			+       "}"
			+   "\n}"
			);
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void modelItemBlockMetaJson(Block block, String[] variants, File file, int i, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{\n" 
			+ "	\"parent\": \"" + modid + ":block/" + variants[i] + "\" \n"
			+ "}"
			);
			writer.close();
		}catch (IOException ex) {
			System.out.println(ex);
		}
	}
	/*
	private static void modelItemBlockMetaJson(Block block, String[] variants, File file, int i, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{"
			+ " \"parent\": \"block/cube_all\", "
			+     " \"textures\": { "
			+         "\"all\" : "
			+          "\"" + modid + ":blocks/" + addPath + variants[i] + "\""
			+       "}"
			+   "}"
			);
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}*/

	// ***********ITEMS META************
	public static void addItemMetadataJsonFiles(Item item, String[] variants, String folder) {
		try {
			for (int i = 0; i < variants.length; i++) {
				File folders = new File(path + "/models/item/"+ folder);
				if(!folders.exists()) folders.mkdirs();
				
				File modelVariantsItemMeta = new File(path + "/models/item/"+ folder, variants[i] + ".json");
				
							
				if (!modelVariantsItemMeta.exists())
					if (modelVariantsItemMeta.createNewFile()) {
						modelVariantsItemMetaJson(item, variants, modelVariantsItemMeta, i, folder);
					} else if (modelVariantsItemMeta.exists()) {
						modelVariantsItemMetaJson(item, variants, modelVariantsItemMeta, i, folder);
					}
				
			}

		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void modelVariantsItemMetaJson(Item item, String[] variants, File file, int i, String folder) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{"
			+ " \"parent\": \"item/generated\", "
			+     " \"textures\": { "
			+         "\"layer0\" : "
			+          "\"" + modid + ":items/" + folder + variants[i] + "\""
			+       "}"
			+   "}"
			);
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
}
