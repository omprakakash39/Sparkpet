# SparkPets

Custom Pets Plugin for Minecraft **1.21.3** (Paper) by **sayan**

## Features
- Primary + Secondary pet system
- 4 Rarities: Regular, Gold, Rainbow, Shiny
- Pet Eggs (Allay Spawn Egg)
- Fusion system (GUI ready)
- Working abilities (Attack Boost, Damage Reduction, Totem save, etc.)
- PlaceholderAPI support ready
- Admin-only take from GUIs

## Commands
- `/pets` or `/pet` → Open Pets GUI

## Permissions
- `sparkpets.use` → Use /pets (default: true)
- `sparkpets.admin` → Take pets/eggs from GUIs (default: op)
- `sparkpets.give` → Give eggs (default: op)

## How to Build
1. Push to GitHub
2. GitHub Actions will automatically build the JAR
3. Download from Actions → Artifacts

Or locally:
```bash
mvn clean package
