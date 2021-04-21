# bitvavo-pi
Unofficial Bitvavo balance printer for Raspberry Pi with LCD1602 display.

_Note that this project makes use of the [java-bitvavo-api](https://github.com/bitvavo/java-bitvavo-api) wrapper. For more information about java-bitvavo-api, visit the [official repository](https://github.com/bitvavo/java-bitvavo-api)._

## Requirements

To be able to use bitvavo-pi, you need the following:
* Raspberry Pi running Raspbian
* LCD1602 Display with 4-bit addon
* Bitvavo API Key + Secret

You can generate an API Key + Secret from the Bitvavo site when logged in, do not forget to activate it via email.

## Setting up

Before you can use this program, a few things have to be set up:
* Connect the LCD1602 display to the GPIO pins of the Raspberry Pi as followed:

![image](https://user-images.githubusercontent.com/64023154/115477815-09746c00-a245-11eb-8243-2710e7ef7a32.png)
* [Enable I2C](https://www.raspberrypi-spy.co.uk/2014/11/enabling-the-i2c-interface-on-the-raspberry-pi/) with `sudo raspi-config`
* Install Java with `sudo apt update` followed by `sudo apt install default-jdk` 
* Download the latest `bitvavo-pi.jar` from [releases](https://github.com/hiddevanesch/bitvavo-pi/releases)

## Usage

You can use this program with or without arguments.

For trying out, simply run `java -jar /path/to/bitvavo-pi.jar`.
You can also run the program in one go with `java -jar /path/to/bitvavo-pi.jar <YOUR_API_KEY> <YOUR_API_SECRET>`.

## Launch on boot

To launch this program on boot of your Raspberry Pi, open a terminal and run `sudo nano /etc/rc.local` then enter `java -jar /path/to/bitvavo-pi.jar <YOUR_API_KEY> <YOUR_API_SECRET>` before the exit 0. Save the file and reboot.
