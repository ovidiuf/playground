# Java Bytecode Version

## Overview
A program that reads a class and prints the bytecode version. The project produces an executable JAR that can be used to check the version. The corresponding NOKB section: https://kb.novaordis.com/index.php/Java_Bytecode_Version

## Build

````shell
./gradlew clean build
````

## Usage

````shell
java -jar ./build/libs/bytecode-version.jar <class-file-path>
````

