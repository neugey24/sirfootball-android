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

                    AvatarGroup.bobbleheads.toString() -> listOf(
                        "blue_uniform", "coach", "dark_magenta_uniform",
                        "green_uniform", "magenta_uniform", "orange_uniform",
                        "purple_uniform", "red_white_uniform", "yellow_uniform"
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

                    AvatarGroup.gods.toString() -> listOf(
                        "helmet_god", "hera1", "hera2", "hercules", "horus", "icarus", "isis",
                        "lightning_god", "medusa", "odysseus", "osiris", "poseidon", "zeus"
                    )

                    AvatarGroup.halloween.toString() -> listOf(
                        "bride_frankenstein", "dracula", "female_specter", "frankenstein",
                        "ghost1", "ghost2", "ghost3", "green_witch", "mummy1", "mummy2",
                        "scarecrow", "skeleton_ghoul", "skeleton_witch", "vampire_priestess",
                        "vampire_warrior", "werewolf", "werewolf2", "zombie_warrior"
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

                    AvatarGroup.rpg_warriors.toString() -> listOf(
                        "alchemist", "archer", "axe_dwarf", "bard", "blue_knight",
                        "cleric", "elven_archer", "female_mage", "flail_warrior", "hammer_warrior",
                        "huntress", "mace_warrior", "muscle_warrior1", "muscle_warrior2",
                        "red_knight", "sorceror", "spectral", "swordsman", "swordsman2",
                        "thief", "valkyrie", "warrior_princess"
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
    , bobbleheads
    , cheerleaders
    , chess
    , christmas_winter
    , emoji
    , fantasy_creatures
    , food
    , football_fun
    , gods
    , halloween
    , military
    , music
    , painted_footballs
    , rpg_warriors
}