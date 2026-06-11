<div align="center">

# RS-Auger

### Vertical Item Transport for Modern Minecraft Servers

![Minecraft](https://img.shields.io/badge/Minecraft-1.21%2B-green)
![Platform](https://img.shields.io/badge/Platform-Paper-orange)
![Storage](https://img.shields.io/badge/Storage-SQLite-blue)
![Status](https://img.shields.io/badge/Status-Stable-success)
![Development](https://img.shields.io/badge/Development-Active-brightgreen)
![RS Ecosystem](https://img.shields.io/badge/RS-Ecosystem-purple)

</div>

---

## Overview

**RS-Auger** is a lightweight vertical item transport plugin designed for Minecraft servers that need fast, reliable movement of items between containers without the complexity of pipes, networks, or large hopper systems.

Players place and configure Augers to automatically move item stacks between containers using Lightning Rods while maintaining full control through filters, machine states, diagnostics, and administrative tools.

Built from the ground up with performance, scalability, and long-term server operation in mind.

---

## Why RS-Auger?

Traditional item transport systems often create:

* Excessive hopper usage
* Server lag
* Complicated redstone contraptions
* Large storage bottlenecks
* Difficult maintenance and troubleshooting

RS-Auger replaces those systems with a clean, configurable, database-backed solution that is easy for players to use and easy for administrators to manage.

---

# Features

### Whole Stack Transfers

Moves entire item stacks instead of individual items.

### Vertical Item Transport

Built specifically for moving items upward between containers.

### Dynamic GUI Menus

Simple and intuitive inventory-based interfaces.

### Advanced Item Filtering

Control exactly which items are transferred.

### SQLite Storage

Reliable persistent machine storage.

### Machine States

BUILDING, RUNNING, PAUSED, and ERROR states.

### Error Diagnostics

Visual error indicators using Angry Villager particles.

### Pause & Resume

Pause machines safely and resume after validation.

### Automatic Validation

Continuously validates source and destination containers.

### Performance Focused

Designed to minimize server impact while maximizing functionality.

### Administrative Tools

Powerful management tools for staff and server operators.

---

# How It Works

The Lightning Rod orientation determines transfer direction.

### Small End

Source container

### Large End

Destination container

Example:

```text
Chest
 ↑
RS-Auger
 ↑
Barrel
````

Items move from the chest to the barrel.

---

# Supported Containers

* Chest
* Trapped Chest
* Barrel
* Hopper

---

# Configuration

### Maximum Machine Length

```yaml
max-machine-length: 16
```

Defines the maximum number of Lightning Rod segments allowed between source and destination containers.

Server owners may lower this value.

The hard maximum machine length is capped at **16 segments** regardless of configuration.

---

# RS Ecosystem

RS-Auger is the transport component of the growing **RS Ecosystem**.

```text
┌─────────────┐
│ RS-MobStand │
└──────┬──────┘
       │ Generate
       ▼
┌──────────────┐
│ RS-ItemMagnet│
└──────┬───────┘
       │ Collect
       ▼
┌───────────┐
│ RS-Auger  │
└─────┬─────┘
      │ Transport
      ▼
┌─────────────┐
│ RS-Warehouse│
└─────────────┘
       Store
```

Together these plugins create a complete automated resource collection and logistics system.

---

# Permissions

| Permission       | Description           |
| ---------------- | --------------------- |
| `rs.auger.use`   | Allows use of Augers  |
| `rs.auger.admin` | Administrative access |

---

# Installation

1. Download the latest release.
2. Place the jar into your server's `plugins` folder.
3. Restart the server.
4. Configure settings as desired.
5. Enjoy automated vertical item transport.

---

# Performance

RS-Auger was designed with long-term server operation in mind.

Features include:

* SQLite persistence
* Chunk-safe processing
* Whole-stack transfers
* Efficient validation
* Configurable machine lengths
* Optimized inventory handling
* Minimal server overhead

---


# Contributors

### Big Pappa

Project Creator
Founder of RSScripting

### Atlas
Architecture, Design & Development Support

---

# Support
If you encounter a bug or have a feature request, please open an issue in the GitHub repository.

---

# License

MIT License
See the LICENSE file for details.

---

<p align="center">
  Built by RSScripting
</p>

<p align="center">
  Generate • Collect • Transport • Store
</p>
