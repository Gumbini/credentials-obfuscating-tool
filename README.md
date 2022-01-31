# Credentials Obfuscator
This program obfuscates (not encrypts!) access credentials (such as a WiFi password and SSID) for use on microcontrollers.
It creates data for a C++ header on standard output.

**This tool does NOT provide strong security for very important use cases! Use at your own risk.**

The following was intended as an area of application:

- You can show someone your source code and they won't see your WiFi password right away
- Your password is not stored in clear text in your project files
- If you exclude the created header in the *.gitignore* file, you can freely share your project publicly

## Build

- Import as a new Java Project in your IDE
- Compile the code

**Recommended if you were redirected to this repository from one of my other projects:**
use the prebuilt [JAR archive](https://github.com/Gumbini/credentials-obfuscator/releases)

## Usage

```
java -jar CredentialsObfuscator.jar <String> [String...]
```

## Example

```
$ java -jar CredentialsObfuscator.jar "EXAMPLE_SSID" "EXAMPLE_PASSWORD" > ObfuscatedData.hpp
```
will create a file ```ObfuscatedData.hpp```:
```cpp
#pragma once
#ifndef OBFUSCATED_DATA_HPP
#define OBFUSCATED_DATA_HPP

#include <climits> // for CHAR_BIT
#include <cstdint> // for uint8_t

static_assert(CHAR_BIT == 8, "Proper deobfuscation can only be guaranteed with 8-bit characters!");

namespace ObfuscatedCredentials {
	constexpr uint8_t data[] = {
		0x24, 0x60, 0x02, 0x81, 0x00, 0xC0, 0xE0, 0x41,
		0x40, 0x20, 0x00, 0x60, 0x80, 0x80, 0x21, 0xA0,
		0x02, 0x00, 0xC0, 0xE0, 0x41, 0x40, 0x20, 0x00,
		0x60, 0x40, 0xE0, 0x80, 0x80, 0x81, 0x61, 0x01,
		0xA0, 0xA8, 0x89, 0x0A, 0xEB, 0x6A, 0x88, 0x0B,
		0x28, 0x4A, 0x29, 0xA9, 0xE9, 0xEA
	};
}

#endif // OBFUSCATED_DATA_HPP


```
If you run the example yourself, you'll notice that the data in the array is different after each run. This coincidence is intentional.

## Deobfuscation

For deobfuscating on the microcontroller (e.g. an ESP32) use the
[Credentials Deobfuscation Library](https://github.com/Gumbini/credentials-deobfuscating-lib) which is written in C++.

## How does it work?

I will add this section later...
