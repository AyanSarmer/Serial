# Project options 
FILE				= strip
MCU					= atmega328p
# Folders
SRC_DIR       		= src
INC_DIR       		= inc
EXE_DIR       		= exe
# Objects
SOURCES       		= $(wildcard $(SRC_DIR)/*.c)
SOURCES       		+= $(wildcard $(SRC_DIR)/*.S)
# GCC compiler and options
PREAMBLE      		= avr-
COMPILER      		= $(PREAMBLE)gcc
	CC_OPTIONS  	= -mmcu=$(MCU)
	CC_OPTIONS  	+= -Wl,-u,vfprintf -lprintf_flt
	CC_OPTIONS  	+= -Os
	CC_OPTIONS		+= -I $(INC_DIR)
	CC_OPTIONS  	+= -o
# Uploader and options
UPLOADER      		= avrdude
	U_OPTIONS		= -p $(MCU)
	U_OPTIONS		+= -c usbasp
	U_OPTIONS		+= -P usb
	U_OPTIONS		+= -e
	U_OPTIONS		+= -U flash:w:$(EXE_DIR)/$(FILE).hex