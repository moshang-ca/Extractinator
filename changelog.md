## 3.1.0-alpha.1 (Minecraft 1.21.1)
English/[中文](changelog.zh.md)

### Added
- Automatic item extraction from adjacent containers (chests, etc.) — the extractinator will pull valid items from a nearby container and process them.
- Output items are now pushed into the container above the extractinator if present; otherwise dropped as item entities.
- Platform-specific container integration: Fabric uses `ItemStorage`, NeoForge uses `Capabilities.ItemHandler`.
- Improved input insertion logic supports stacking and simulation.

### Changed
- `addItemToInput` now returns the remaining items instead of modifying the original stack directly.
- Container position is persisted and automatically reconnected.
- Replaced `level == null` checks with `assert` for cleaner code.
- JEI tooltips now display drop chance, minimum and maximum drop counts per output (if JEI is installed).

### Fixed
- Proper handling of item insertion when the input slot is full.
