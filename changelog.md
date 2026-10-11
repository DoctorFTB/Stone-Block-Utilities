# Changelog

## 2.2.0

- Add handler to fix: crashes when an Ex Compressum auto sieve is fed food with a potion effect (rotten flesh, menril berries), by hand or by an Extra Utilities 2 Mechanical User. The sieve eats the food as Forge's shared fake player, which has no network connection, so the potion effect crashed. Fake players without a connection no longer receive potion effects; the sieve still gets its speed boost. Only active when Ex Compressum is installed. ([StoneBlock #897](https://github.com/Sunekaer/stoneBlock/issues/897), [#853](https://github.com/Sunekaer/stoneBlock/issues/853))
- Add handler to fix: Void Island Control: each player's island, and any island they left, is now also saved in their own player data. If VIC loses its `data/VICData.dat` (for example after a crash while saving), returning players are restored instead of being treated as new, so their base is no longer overwritten by the starting island and their inventory is no longer cleared. Islands players left stay reserved, so no new island is pasted over them. Only active when Void Island Control is installed. ([StoneBlock #898](https://github.com/Sunekaer/stoneBlock/issues/898), [#878](https://github.com/Sunekaer/stoneBlock/issues/878), [#793](https://github.com/Sunekaer/stoneBlock/issues/793))
- Fix crook random drops: every drop shared one item stack, so after the first sapling was picked up that sapling type dropped as an empty item.
- Requires Forge 14.23.5.2792 or newer.

## 2.1.5

- Fix dupe with the Crafting Table.

## 2.1.4

- Fix getting the wrong recipe.

## 2.1.3

- Fix the crook recipe.
- Fix crook durability (it was not damaged after breaking a block).

## 2.1.2

- Remove the Ex Nihilo dependency.
- Add drop config for the crook.
- **The config format changed.** `drops` became `handDrops` and `crookDrops`, and `drop_with`/`drop_without` are now item counts. A config from 2.1.1 or older crashes on startup: delete it to get the default, or convert it.
