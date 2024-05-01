package com.sirfootball.android.structure

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class SirAvatar private constructor() {

    companion object {

        @Composable
        fun getResourceIdForAvatar(avatarKey: String, teamNum: Int) : Int {
            val safeTeamNum = if (teamNum > 12) teamNum.toString().first().digitToInt() else teamNum
            var avatarPath : String
            if (avatarKey == "user_default") {
                avatarPath = "avatar_system_user_default_avatar_${safeTeamNum}"
            } else {
                if (avatarKey == "robo_default") {
                    avatarPath = "avatar_system_robo_default_avatar_${safeTeamNum}"
                } else {
                    val pathTranslate = avatarKey.replace(":", "_")
                    avatarPath = "avatar_${pathTranslate}"
                }
            }

            return LocalContext.current.resources.getIdentifier(avatarPath, "drawable",
                LocalContext.current.packageName)
        }

        fun getAvatars(avatarGroup: String): List<String> {
            val avatars =
                when (avatarGroup) {
                    AvatarGroup.adult_beverage.toString() -> listOf(
                        "beer_mug",
                        "beer_mugs",
                        "beer_tray",
                        "beer_tray2",
                        "beer_tub",
                        "bourbon_rocks",
                        "cranberry_vodka",
                        "dark_six",
                        "lime_margarita",
                        "martini",
                        "mug_dark_ale",
                        "shot_glasses",
                        "six_pack",
                        "strawberry_margarita",
                        "tequila",
                        "whiskey_set",
                        "white_wine",
                        "wine_set"
                    )

                    AvatarGroup.alphabet_jackets.toString() -> listOf(
                        "a",
                        "a2",
                        "b",
                        "c",
                        "c2",
                        "d",
                        "d2",
                        "e",
                        "f",
                        "f2",
                        "g",
                        "g2",
                        "h",
                        "h2",
                        "i",
                        "j",
                        "j2",
                        "k",
                        "k2",
                        "l",
                        "l2",
                        "m",
                        "m2",
                        "n",
                        "n2",
                        "o",
                        "o2",
                        "p",
                        "p2",
                        "q",
                        "r",
                        "r2",
                        "s",
                        "t",
                        "u",
                        "v",
                        "w",
                        "x",
                        "y",
                        "z"

                    )

                    AvatarGroup.animal_mascots.toString() -> listOf(
                        "aardvark",
                        "badger",
                        "bear",
                        "beaver",
                        "chipmunk",
                        "coyote",
                        "cub",
                        "deer",
                        "elephant",
                        "fire_wolf",
                        "fox",
                        "fox2",
                        "giraffe",
                        "groundhog",
                        "horse",
                        "lion",
                        "marching_wolf",
                        "meerkat",
                        "penguin",
                        "pirate_cat",
                        "platypus",
                        "pony1",
                        "pony2",
                        "rhino",
                        "rowdy_dog",
                        "salamander",
                        "seal",
                        "skunk",
                        "teal_owl",
                        "tiger",
                        "turtle",
                        "warthog"
                    )

                    AvatarGroup.anime.toString() -> listOf(
                        "beetle_boy","biker_cat","biker_eagle","blue_beaver","boxer","bully","business_badger",
                        "crawler","croc","cyber","cyborg_lizard","deer_girl","emerald_cat","fox_player","giraffe",
                        "golden_boy","golden_dolphin","hottie","ice_cat","iggy_ref","jeweled_frog","kitty_mascot",
                        "knight","lightning_maiden","lion","mechanical_villain","money_boy","pads","pony_kicker",
                        "pyro","rainbow_furball","rainbow_serpent","rhino","skeleton_moon","snake","spike","spike2",
                        "spikes","steel_boy","vampire_ref","wiry"
                    )

                    AvatarGroup.bobbleheads.toString() -> listOf(
                        "blue_uniform", "coach", "dark_magenta_uniform",
                        "green_uniform", "magenta_uniform", "orange_uniform",
                        "purple_uniform", "red_white_uniform", "yellow_uniform"
                    )

                    AvatarGroup.cats.toString() -> listOf(
                        "american_bobtail","birman", "california2", "korat2", "persian", "siamese2",
                        "american_longhair","bombay", "exotic_shorthair","laperm", "ragamuffin", "york",
                        "american_shorthair","burmese", "himalayan", "nebelung", "ragamuffin2",
                        "balinese", "california", "korat", "norwegian", "siamese"
                    )

                    AvatarGroup.cheerleaders.toString() -> listOf(
                        "aa1",
                        "aa2",
                        "aa3",
                        "aa4",
                        "army",
                        "asian",
                        "astronaut",
                        "belle1",
                        "belle2",
                        "blackhair",
                        "blonde1",
                        "blonde2",
                        "brunette1",
                        "brunette2",
                        "cowgirl",
                        "cowgirl2",
                        "goth",
                        "latina",
                        "patriotic",
                        "pink",
                        "police",
                        "rainbow",
                        "redhead1",
                        "redhead2",
                        "referee",
                        "roller_girl",
                        "roller_girl2",
                        "schoolgirl",
                        "stars",
                        "tiger",
                        "waves",
                        "winter",
                        "xmas"

                    )

                    AvatarGroup.chess.toString() -> listOf(
                        "bishop",
                        "board",
                        "brass_set",
                        "bronze_pieces",
                        "king",
                        "king_on_slab",
                        "knight",
                        "pawn",
                        "queen",
                        "rook",
                        "wood_set"
                    )

                    AvatarGroup.christmas_winter.toString() -> listOf(
                        "ball_mascot",
                        "candycane1",
                        "candycane_ref",
                        "cane_player",
                        "elf1",
                        "elf2",
                        "female_ref_elg",
                        "football_tree",
                        "frozen_mascot1",
                        "gingerbread1",
                        "gingerbread2",
                        "grinch_ref",
                        "grinch_ref_female",
                        "grinch_suit",
                        "iceman1",
                        "iceman2",
                        "iceman3",
                        "manger1",
                        "manger2",
                        "ornament1",
                        "ornament2",
                        "ornament3",
                        "ornament4",
                        "ornament5",
                        "ornament6",
                        "ornament7",
                        "ornament8",
                        "ornament9",
                        "presents1",
                        "presents2",
                        "rudolph",
                        "santa1",
                        "santa2",
                        "skis",
                        "sleigh1",
                        "sleigh2",
                        "sleigh_rider",
                        "snowman1",
                        "snowman2",
                        "snowman3",
                        "snowmobile1",
                        "tree_mascot",
                        "wreath_mascot"
                    )

                    AvatarGroup.dogs.toString() -> listOf(
                        "airedale", "border_terrier", "cocker_spaniel", "french_poodle2", "italian_greyhound", "rottweiler",
                        "akita", "boxer", "collie", "german_shepherd", "jack_russell", "scottish_terrier", "american_eskimo",
                        "bull_terrier", "corgi", "german_shepherd2", "lhasa_alpo", "shiba_inu", "american_samoyed",
                        "bull_terrier2", "corgi2", "golden_retriever", "maltese", "shiba_inu2", "bassett_hound2",
                        "bulldog", "dachshund", "golden_retriever2", "mastiff", "shih_tzu", "bassett_houng", "bulldog2",
                        "dachshund2", "great_dane", "papillon", "siberian_husky", "beagle", "cane_corso", "dalmation",
                        "greyhound", "pitbull", "st_bernard", "beagle2", "chihuahua", "doberman", "havanese",
                        "pomeranian", "whippet", "bichon", "chihuahua2", "doberman2", "havanese2", "pomeranian2",
                        "yorky", "black_lab", "chocolate_lab", "french_bulldog", "husky_white", "pug", "bloodhound",
                        "chocolate_lab2", "french_poodle", "husky_white2", "pug2"
                    )

                    AvatarGroup.emoji.toString() -> listOf(
                        "angel",
                        "astonished",
                        "cowboy",
                        "crying",
                        "happy",
                        "hear_no_evil",
                        "heart",
                        "lol",
                        "money",
                        "oriental",
                        "puke",
                        "rolling_eyes",
                        "see_no_evil",
                        "star",
                        "sunglasses",
                        "tongue",
                        "wizard"

                    )

                    AvatarGroup.fantasy_creatures.toString() -> listOf(
                        "crouching_imp",
                        "dragon1",
                        "dragon2",
                        "emerald_fairie",
                        "ent",
                        "fairie_football",
                        "gargoyle",
                        "gnome1",
                        "gnome2",
                        "goblin1",
                        "goblin2",
                        "golem",
                        "halfling",
                        "hobbit",
                        "horned_imp",
                        "minotaur",
                        "mummy",
                        "necromancer",
                        "ogre",
                        "skeleton",
                        "unicorn"
                    )

                    AvatarGroup.food.toString() -> listOf(
                        "apple",
                        "apple_pie",
                        "bananas",
                        "bbq_brisket",
                        "burger_fries",
                        "butterscotch_candy",
                        "caramel_corn",
                        "cheese_and_crackers",
                        "cheeseburger",
                        "cherry_cola",
                        "cherry_pie",
                        "chex_and_milk",
                        "chili",
                        "chips_salsa",
                        "coffee_pot",
                        "cookies",
                        "corn_flakes_cereal",
                        "fortune_cookie",
                        "fried_chicken",
                        "fried_rice",
                        "frosted_donut",
                        "fruit_loops",
                        "generals_chicken",
                        "glazed_donut",
                        "gumbo",
                        "ice_cream_sundae",
                        "iced_tea",
                        "lasagna",
                        "loaded_potato",
                        "meat_lovers_pizza",
                        "milkshake",
                        "mixed_nuts",
                        "nachos",
                        "pancakes",
                        "pizza",
                        "pizza_olives",
                        "po_boy",
                        "popcorn",
                        "pumpkin_pie",
                        "rainbow_lollipop",
                        "red_velvet_cake",
                        "ribs",
                        "salad",
                        "spaghetti_meatballs",
                        "sub_sandwich",
                        "taco",
                        "twist_cone",
                        "vanilla_wafers",
                        "wings"

                    )

                    AvatarGroup.football_fun.toString() -> listOf(
                        "bandaid",
                        "bathtub",
                        "big_w",
                        "bomb",
                        "calculator",
                        "campfire",
                        "cartoon_pylon",
                        "cartoon_whistle",
                        "church_guard",
                        "colored_pool",
                        "construction_worker",
                        "crib",
                        "cyborg_ball",
                        "devil_referee",
                        "dumpster",
                        "evil_referee",
                        "green_slime",
                        "lego",
                        "luckyplayer",
                        "lunch",
                        "money_bag_play",
                        "money_player",
                        "moneyball",
                        "mrs_potato_head",
                        "potato_head_football",
                        "preacher",
                        "radio",
                        "robot_ball",
                        "robot_trio",
                        "stoplight",
                        "toilet_paper",
                        "trashcan",
                        "tuxedo"
                    )

                    AvatarGroup.gnomes.toString() -> listOf(
                        "armor","clown2","guitar2","karate","old_man3","ref","armor2","cowboy","guitar3",
                        "magic_wand","pilot","robber","astronaut","cowboy2",
                        "guitar4","magician","pirate","samurai","axe1","crystal_ball","guitar5","magician2","pirate2","samurai2","axe2","driver",
                        "helmet","miner","player","sombrero","banker","engineer","helmet2","monk","plumber","trumpet","biker","farmer","hiker",
                        "monk2","policeman","tux","chef","firefighter","hippie","old_man","racer2","tux2","clown",
                        "guitar","hippie2","old_man2","rancher"

                    )

                    AvatarGroup.gods.toString() -> listOf(
                        "helmet_god", "hera1", "hera2", "hercules", "horus", "icarus", "isis",
                        "lightning_god", "medusa", "odysseus", "osiris", "poseidon", "zeus"
                    )

                    AvatarGroup.halloween.toString() -> listOf(
                        "bride_frankenstein", "dracula", "female_specter", "frankenstein",
                        "ghost1", "ghost2", "ghost3", "green_witch", "mummy1", "mummy2", "mummy3",
                        "scarecrow", "skeleton_ghoul", "skeleton_witch", "vampire_priestess",
                        "vampire_warrior", "werewolf", "werewolf2", "zombie_warrior"
                    )

                    AvatarGroup.kings_queens.toString() -> listOf(
                        "armor_head", "battle_king", "battle_ready", "book", "caster_king", "caster_queen", "cat_queen", "chain_king",
                        "chain_queen", "charms", "checkered_shield", "coins", "dice", "emoji_queen", "fair_warrior", "feline", "friend",
                        "frog", "game_on", "hammer_queen", "happy_battle", "heavy_armor", "horseback", "king_hammer", "king_pet_dragon",
                        "kiss", "kitty", "knight_n_queen", "magic_queen", "orb", "pup", "queen_dog", "rabbit", "regal", "rich_girlwebp",
                        "riches", "rider", "ruler_queen", "scorned", "shield_maiden", "throne", "throne_queen", "treasures",
                        "valkyrie_queen", "warrior", "wealth"
                    )

                    AvatarGroup.meme.toString() -> listOf(
                        "crying_jordan","doge","doge_football","doge_ref","doge_suit","doge_wizard",
                        "grumpy_cat_football","grumpy_cat_jersey","grumpy_cat_leather","most_interesting","nyan_cat",
                        "pepe1","picard","this_is_fine","trollface","trollface_tophat"
                    )

                    AvatarGroup.military.toString() -> listOf(
                        "af_seal",
                        "af_wing_logo",
                        "anchor_and_guns",
                        "army_female",
                        "army_logo",
                        "army_male",
                        "bazooka_soldier",
                        "carrier",
                        "chopper",
                        "f15",
                        "green_beret",
                        "grenade",
                        "knight_anchor",
                        "marines_seal",
                        "naval_officer",
                        "navy_anchor",
                        "navy_logo",
                        "pow_mia",
                        "tank",
                        "trooper"
                    )

                    AvatarGroup.music.toString() -> listOf(
                        "acoustic_guitar", "bass", "blues_brother", "cello",
                        "flashy_blue_drum_set", "flute", "marching_band",
                        "purple_guitar", "saxophone", "skeletal_warrior_guitarist",
                        "trumpet", "violin"
                    )

                    AvatarGroup.ogres_orcs.toString() -> listOf(
                        "beastmaster", "blue_devil", "bluestone", "bouncer", "bouncer2", "brawler", "chef",
                        "club_bouncer", "dreamer", "drummer", "enchanted", "fixer", "gazer", "genius", "greenstone",
                        "healer", "heartbreaker", "hiker", "horned", "horned2", "hulker", "lion_prince",
                        "longhair", "magician", "master", "menace", "miner", "party", "perfect", "player",
                        "pose", "punisher", "rainbow", "runner", "samurai", "saw", "saw_traveller", "searcher",
                        "seer", "shifter", "slither", "sloppy", "stealth", "stone_keeper", "straight_a", "summoned",
                        "sun_god", "victor", "waldo", "wanderer", "wayfarer", "wisest"
                    )

                    AvatarGroup.other_sports.toString() -> listOf(
                        "baseball","baseball_girl","baseball_man","basketball_girl","basketball_guy","bowling",
                        "bowling2","fencer","golf_girl","golf_girl2","golf_guy1","golf_guy2","hockey_girl",
                        "hockey_man","ice_skater_female","ice_skater_male","karate_girl","karate_guy","pool_8ball",
                        "racer_girl","racer_guy","soccer_ball","soccer_female","soccer_male","softball","softball_girl",
                        "softball_girl2","tennis_ball","tennis_girl","tennis_man"
                    )

                    AvatarGroup.painted_footballs.toString() -> listOf(
                        "black_gold_fancy",
                        "blue_kitty",
                        "blue_ram",
                        "blue_sword",
                        "blue_white_horse",
                        "bluestar",
                        "brown_pup",
                        "bullseye",
                        "burgundy_eye",
                        "canadian",
                        "checkered",
                        "cosmic",
                        "crown",
                        "crown_regal",
                        "cyborg",
                        "egyptian",
                        "eight_ball",
                        "fireworks",
                        "floral",
                        "freedom_musket",
                        "german_flag",
                        "green_box",
                        "green_rocket",
                        "laser_gun",
                        "lightning_god",
                        "love",
                        "lucky_ball",
                        "mint_green",
                        "neon_needle",
                        "oilball",
                        "old_western",
                        "orange_bear_paw",
                        "orange_brown_stripes",
                        "orange_horseshoe",
                        "orange_reef",
                        "pewter_shield",
                        "purple_bird_eye",
                        "purple_yellow_axe",
                        "radiated",
                        "rainbow",
                        "red_black_bird",
                        "red_blue_bull",
                        "red_cali",
                        "red_sword",
                        "red_yellow_stars",
                        "sci_fi",
                        "silver_and_black",
                        "silver_blue",
                        "silver_blue_star",
                        "skeleton",
                        "tattoo",
                        "teal_spots",
                        "tiger_stripes",
                        "union_jack",
                        "victorian",
                        "white_fist",
                        "white_red_bird",
                        "white_wings",
                        "yellow_black_stripes"

                    )

                    AvatarGroup.pet_dragons.toString() -> listOf(
                        "attacker", "baller", "battler", "beach", "bengal", "big_sword", "blue_streak", "cutie",
                        "dj", "fighter", "firework", "football", "friendly", "fury", "goldy", "greenie", "grump", "gus",
                        "heart", "jewels", "laughter", "magic", "mechanized", "prince", "purps", "red", "ref", "salty", "spectrum",
                        "toy", "traveler", "treasure"
                    )

                    AvatarGroup.rpg_warriors.toString() -> listOf(
                        "alchemist", "archer", "axe_dwarf", "bard", "blue_knight",
                        "cleric", "elven_archer", "female_mage", "flail_warrior", "hammer_warrior",
                        "huntress", "mace_warrior", "muscle_warrior1", "muscle_warrior2",
                        "red_knight", "sorceror", "spectral", "swordsman", "swordsman2",
                        "thief", "valkyrie", "warrior_princess"
                    )

                    AvatarGroup.scifi.toString() -> listOf(
                        "alien_aqua","alien_blue","alien_green","alien_green_squid","alien_orange","alien_red",
                        "alien_red2","alien_yellow","arena_thug","attacking_football_ufo","big_eyes","black_hole_alien",
                        "bling_helmet","bodybuilder","bouncer","brain","crazy_helmet","croc","croc2","crowned_alien",
                        "cyborg_head","cyborg_head2","cyborg_head3","cyborg_head4","emoji_thug","examiner","eye_man",
                        "fighter","fish_thug","football_ufo","helmet_weapon","insect","insect2","jewel_helmet","jeweled",
                        "jeweled_helmet2","laser_helmet","leather_agent","lizard_fighter","magenta_sacuer","octopus","pilot",
                        "raygun_helmet","righteous_helmet","saber_fighter","saucer_green","saucer_purple","scientist",
                        "shock_alien","squid_fighter","squid_menace","squid_thug","sun_rager","sword_football","thug",
                        "tree_alien","trenchcoat","trippy","trooper_alien","ufo_helmet","ufo_targeting_football",
                        "weapon_helmet2","whisper_alien","wired_helmet","zebra_gunner","zombie","zombie2"
                    )

                    AvatarGroup.shields.toString() -> listOf(
                        "ally", "campmaker", "firebrand", "hotshot", "perennial", "seized", "aquatic", "capitol",
                        "firebreather",    "jayfighter", "pirate", "shamrock", "armorer", "claw", "firehawk",
                        "levelup", "prideknight", "smurfhunter", "baron", "cottoncandy", "grapeman", "matador",
                        "provoker", "starcrosser", "behemoth", "crawler", "greenhip", "mischief", "reptile", "starforge",
                        "blueberry", "crossways", "hardcandy", "nefarious", "rival", "steelheart", "bluenote", "deadwing",
                        "hawkhunter", "ogrewind", "rubes", "sudden", "borer", "deathswing", "heartbreaker", "oink", "sagefinder",
                        "sunrise", "brawler", "dragonforce", "hellion", "overdrive", "scope", "valiant", "bridgebuilder",
                        "feathersword", "highspirit", "pathfinder", "seafarer"
                    )

                    AvatarGroup.superhero.toString() -> listOf(
                        "android", "angel", "blue_streak", "chrome", "cobra", "diamonds", "disco", "dragoness",
                        "firefly", "huntress", "inspector", "intrigue", "ivory", "kombat", "magenta",
                        "pearls", "pilot", "portal", "pounder", "priestess", "reaper", "shooter",
                        "spike", "stripes", "teller", "tinkerer", "viper", "vr_man", "wings"
                    )

                    AvatarGroup.wizards.toString() -> listOf(
                        "boy_teller", "burgundy", "caster", "caster2", "fire", "friendly", "gardener", "ghostie", "guide",
                        "icer", "knight_mage", "masked", "purple", "reader", "ruby", "shroud", "spectral", "staff_boy",
                        "survivor", "teller", "townkeep", "trinkets", "wanderer"
                    )

                    else -> listOf()
                }
            return avatars
        }

    }

}

enum class AvatarGroup {
    adult_beverage
    , alphabet_jackets
    , animal_mascots
    , anime
    , bobbleheads
    , cats
    , cheerleaders
    , chess
    , christmas_winter
    , dogs
    , emoji
    , fantasy_creatures
    , food
    , football_fun
    , gnomes
    , gods
    , halloween
    , kings_queens
    , meme
    , military
    , music
    , ogres_orcs
    , other_sports
    , painted_footballs
    , pet_dragons
    , rpg_warriors
    , scifi
    , shields
    , superhero
    , wizards
}