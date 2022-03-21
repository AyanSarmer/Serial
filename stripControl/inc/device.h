#ifndef DEVICE_H_
#define DEVICE_H_

#include <avr/io.h>
#include <stdint.h>
#include <string.h>
#include "Serial.h"
#include "DS18B20.h"
#include "timer.h"

#define STRIP_LENGTH    10
#define LEDS_NUM		(STRIP_LENGTH * 3)

#define STRIP_DDR       DDRB
#define STRIP_PIN       PB0
#define STRIP_OFF       0
#define STRIP_ON        1

#define MAIN            1
#define WAIT            2

void deviceInit();
void deviceControl();
void stripOff();
void stripSetColor();
void setLedColor(uint8_t* p_buf, uint8_t led, uint8_t red, uint8_t green, uint8_t blue);

extern void stripRefresh(uint8_t * ptr, uint16_t count);

extern uint8_t buf[LEDS_NUM];

#endif /* DEVICE_H_ */