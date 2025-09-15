# Marker - the shape render library

## Introduction

Marker provides an easy and modern way to visually highlight areas in Minecraft by leveraging Display Entities.
Instead of relying on the common particle-based solutions, this approach creates solid, stable, and high-quality markers that remain perfectly visible from any distance without the typical flicker or random gaps you get with particles.

Particles can be great for quick effects, but they were never designed for precise or persistent area visualization. Their constant motion, uneven density, and limited color control often make boundaries look noisy and inconsistent—especially when players move or when the server lags.
Marker on the other hand uses Minecrafts [DisplayEntities](https://minecraft.wiki/w/Display) introduced in `Minecraft Java Version 1.19.4`. This means the markers stay crisp and perfectly aligned, even under heavy server load or when viewed from unusual angles.

With this library, you can easily mark build regions, protected plots, event arenas, or custom gameplay zones in a way that looks professional and feels integrated with modern Minecraft visuals.
It’s also designed to be lightweight, performant (since display entities render client side), and highly customizable, so you can adapt the style, color, and shape of your markers to suit everything from creative building tools to complex minigames or admin utilities.

Marker is at the time being developed and tested for `Paper 1.21.8`.

## Before you start
Marker provides two render types for markes:

| Type         | Advantages                                        |                                                             |
|--------------|---------------------------------------------------|-------------------------------------------------------------|
| TextDisplay  | Can render every rgb color, color can be animated | Uses 4 entities per line to be visible from every angle     |
| BlockDisplay | Only uses one entity per line -> faster           | Texture/Color is limited to a material, cant be transparent |

## Available shapes

### Line
A line is, like the name suggests, s simple line between two locations

<img width="auto" height="200" alt="image" src="https://github.com/user-attachments/assets/1657b8f6-c3ac-430c-96c0-5909017fd2aa" />

### Cuboid
A cuboid spans an area between two locations

<img width="auto" height="200" alt="image" src="https://github.com/user-attachments/assets/8b3bec07-0d58-4c69-ba20-4a137597d9f0" />

### Circle
A circle takes a center position and a radius

<img width="auto" height="200" alt="image" src="https://github.com/user-attachments/assets/70707525-402e-4528-966c-b848686100e4" />

### Sphere
A sphere takes a center position and a radius

<img width="auto" height="200" alt="image" src="https://github.com/user-attachments/assets/6645d49e-6442-48bf-99b7-9ca412807d0c" />
