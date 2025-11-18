# FakePlayer
Website: None

# Official Discord 

https://discord.gg/aT9z7q7hX8

## Building instructions

./gradlew build

## Default Config

```

# MySQL settings
mysql:
  # MySQL host
  host: 
  # MySQL port
  port: 
  # MySQL database
  database: mc157108
  # MySQL username
  username: 
  # MySQL password
  password: 
# Servers to sync with
servers:
  # Example server
  # This is an example and is not used
  example: true
  0050b07b-0f74-4428-94a1-8f16b4782d82: true
# Inject MOTD
# Injects the MOTD with the player count
inject-motd: true
```
 
## Description

A plugin that was originally created to display player count on velocity. This is also an api that allows other plugins can hook into it. 
>
> - Shows the alleged “player“ count on the server.
>   
> - Says what players are fake

## Features

> Velocity support
>
> SQL Support

## Hard Dependencies

>  - [FakePlayer](https://github.com/Folia-Inquisitors/FakePlayer) *Purpose: Generates fake players*
